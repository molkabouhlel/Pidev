package com.esprit.controllers;


import com.esprit.models.Club;

import com.esprit.services.ClubService;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import java.awt.TrayIcon.MessageType;

public class AffichageClubFrontController {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private TextField recherche;

    @FXML
    private ComboBox<String> Trie;


    @FXML
    private FlowPane flowPane;
    private int Id_Club_Selected;

    private Timeline timeline;

    private List<Node> originalNodes = new ArrayList<>();

    public void initialize() {
        ///////////////////////////TRIE/////////////////////////////////
        Trie.setValue("nom");
        Trie.getItems().addAll("nom", "temps", "");


        initFlowpane();
        /////////////////////////////////RECHERCHE/////////////////////////////////
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filter();
        });
        recherche.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && recherche.getText().isEmpty()) {
                flowPane.getChildren().setAll(originalNodes);
            }
        });


        //////////////////////////////////Notification////////////////////////////////////
        for (int i = 0; i < flowPane.getChildren().size(); i++) {

            Label Temps_ouverture = (Label) ((VBox) flowPane.getChildren().get(i)).getChildren().get(5);
            Label Nom_club = (Label) ((VBox) flowPane.getChildren().get(i)).getChildren().get(0);
            String NomText = Nom_club.getText();

            // Define a DateTimeFormatter for parsing the time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(10), event -> {
                        // Get the current time
                        LocalTime currentTime = LocalTime.now();

                        // Get the time from the label and parse it
                        String timeText = Temps_ouverture.getText();
                        LocalTime labelTime = LocalTime.parse(timeText, formatter);

                        // Compare the times
                        if (labelTime.isAfter(currentTime) && labelTime.isBefore(currentTime.plusMinutes(10))) {
                            System.out.println("Before.");
                            try {
                                notif(NomText);
                            } catch (AWTException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }


    public void initFlowpane(){

        ClubService cs = new ClubService();
        List<Club> ClubList = cs.afficher();
        flowPane.setHgap(30);
        flowPane.setVgap(30);


        // Parcours la liste d'images et crée des VBox pour chaque image
        for (Club club : ClubList) {
            VBox Vbox = createVBox(club);
            Vbox.setUserData(club); // Set the Club object as user data for the VBox

            // Handle the Details button click
            Button Details = (Button) Vbox.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);
            if (Details != null) {
                Details.setOnAction(event -> {
                    Id_Club_Selected = club.getId_club();
                    showDetails();
                });
            }

            flowPane.getChildren().add(Vbox);
        }
        originalNodes.addAll(flowPane.getChildren());
    }


    private void filter() {
        String searchText = recherche.getText().toLowerCase();

        List<Node> filteredNodes = flowPane.getChildren().stream()
                .filter(node -> {
                    if (node instanceof VBox) {
                        VBox vBox = (VBox) node;
                        Label clubNameLabel = (Label) vBox.getChildren().get(0);
                        String clubName = clubNameLabel.getText().toLowerCase();
                        return clubName.startsWith(searchText);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(filteredNodes);
    }




    private VBox createVBox(Club C) {
        VBox Vbox = new VBox();
        Vbox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");



        // Crée l'ImageView pour l'image
        String imageUrl = C.getImage_club();
        ImageView imageView = createImageView(imageUrl);
        imageView.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(120);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(120);

        Label nom_club = new Label( C.getNom_club());
        nom_club.setAlignment(Pos.CENTER);
        //StackPane labelPane = new StackPane();
        //labelPane.getChildren().add(nom_club);


        Label label2 = new Label("Description: "+C.getDescription_club());

        // Crée le bouton "Détails" pour chaque image
        Button Details = new Button("Details");
        Details.setPrefWidth(150);
        Details.setStyle("-fx-content-display: LEFT;");


        Label label3 = new Label(String.valueOf(C.getTemp_ouverture()));

        // Ajoute l'ImageView et le bouton "Détails" à la VBox
        Vbox.getChildren().addAll(nom_club,separator1,imageView,separator2,label2,label3,Details);

        return Vbox;
    }


    private void showDetails() {
        timeline.stop();
        System.out.println("Showing details for club ID: " + Id_Club_Selected);

        if (Id_Club_Selected != -1) {
            ClubService cs = new ClubService();
            Club clubSelected = cs.rechercheClub(Id_Club_Selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailClub.fxml"));
            try {
                Parent root = loader.load();
                DetailClubController DCC = loader.getController();
                DCC.initClub(clubSelected);
                DCC.initialize(clubSelected);
                Stage currentStage = (Stage) flowPane.getScene().getWindow();
                currentStage.setScene(new Scene(root));
                System.out.println("Navigating to Detail.fxml");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ImageView createImageView(String imageUrl) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(130);

        try {
            File file = new File(imageUrl);
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return imageView;
    }

    @FXML
    void Trier() {
        // Assuming your flowPane contains Club objects
        ObservableList<Node> children = flowPane.getChildren();

        // Assuming cbSort is a ComboBox<String> containing sorting options
        Comparator<Club> comparator = null;

        if ("nom".equals(Trie.getValue())) {
            comparator = Comparator.comparing(Club::getNom_club);
        }

        if ("temps".equals(Trie.getValue())) {
            comparator = Comparator.comparing(Club::getTemp_ouverture);
        }

        List<Node> sortedChildren = new ArrayList<>(children);
        Comparator<Club> finalComparator = comparator;
        sortedChildren.sort(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                Club club1 = (Club) n1.getUserData();
                Club club2 = (Club) n2.getUserData();
                return finalComparator.compare(club1, club2);
            }
        });

        flowPane.getChildren().setAll(sortedChildren);
    }






    @FXML
    void RedirectToMenu()  throws IOException {
        timeline.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceMenuCoach.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) flowPane.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }



    void notif(String S) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();

        //Alternative (if the icon is on the classpath):
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Image/gym.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage("Reminder", "Club "+S+" is about to open!", MessageType.INFO);



        /////////////////////////////////////NOTIF 2////////////////////////
        /*Platform.runLater(() -> {
                    Stage owner = new Stage(StageStyle.TRANSPARENT);
                    StackPane root = new StackPane();
                    root.setStyle("-fx-background-color: TRANSPARENT");
                    Scene scene = new Scene(root, 1, 1);
                    scene.setFill(Color.TRANSPARENT);
                    owner.setScene(scene);
                    owner.setWidth(1);
                    owner.setHeight(1);
                    owner.toBack();
                    owner.show();
                    Notifications.create().title("helo").text("test").showWarning();
                    ;
                }
        );*/
    }


}
