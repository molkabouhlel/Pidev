package com.esprit.controllers;


import com.esprit.models.Club;

import com.esprit.services.ClubService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class AffichageClubController {
    @FXML
    private TextField recherche;

    @FXML
    private ComboBox<String> cbSort;

    @FXML
    private Button retour;

    @FXML
    private FlowPane flowPane;
    private int Id_Club_Selected;

    private List<Node> originalNodes = new ArrayList<>();

    public void initialize() {
        cbSort.setValue("nom");
        cbSort.getItems().addAll("nom", "temps", "");
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

            Button deleteButton = (Button) Vbox.getChildren().stream()
                    .filter(node -> node instanceof Button && "deleteButton".equals(((Button) node).getId()))
                    .findFirst()
                    .orElse(null);
            if (deleteButton != null) {
                deleteButton.setOnAction(event -> {
                    Id_Club_Selected = club.getId_club();
                    Delete();
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
        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        separator3.setPrefWidth(120);

        Label nom_club = new Label( C.getNom_club());
        nom_club.setAlignment(Pos.CENTER);
        //StackPane labelPane = new StackPane();
        //labelPane.getChildren().add(nom_club);


        Label label2 = new Label("Description: "+C.getDescription_club());

        // Crée le bouton "Détails" pour chaque image
        Button Details = new Button("Details");
        Details.setPrefWidth(150);
        Details.setStyle("-fx-content-display: LEFT;");


        Button Delete = new Button("Delete");
        Delete.setId("deleteButton");
        Delete.setPrefWidth(150);
        Delete.setStyle("-fx-content-display: LEFT;");
        //Delete.setOnAction(this::Delete);ew

        Label label3 = new Label("Heure ouverture: "+C.getTemp_ouverture());

        // Ajoute l'ImageView et le bouton "Détails" à la VBox
        Vbox.getChildren().addAll(nom_club,separator1,imageView,separator2,label2,label3,Details,separator3,Delete);

        return Vbox;
    }

    private void Delete() {
        System.out.println("Deleting  club ID: " + Id_Club_Selected);
        if (Id_Club_Selected != -1) {
            ClubService C = new ClubService();

            Club clubSelected = C.rechercheClub(Id_Club_Selected);
            C.supprimer(clubSelected);
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("suppresion Club");
            alerte.setContentText("Club  supprime");
            alerte.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) flowPane.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showDetails() {
        System.out.println("Showing details for club ID: " + Id_Club_Selected);
        if (Id_Club_Selected != -1) {
            ClubService cs = new ClubService();
            Club clubSelected = cs.rechercheClub(Id_Club_Selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCours.fxml"));
            try {
                Parent root = loader.load();
                ListeCoursController LCC = loader.getController();
                LCC.initClub(clubSelected);
                LCC.initialize(clubSelected);
                Stage currentStage = (Stage) flowPane.getScene().getWindow();
                currentStage.setScene(new Scene(root));
                System.out.println("Navigating to ListeCours.fxml");
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
    void Trier(ActionEvent event) {
        // Assuming your flowPane contains Club objects
        ObservableList<Node> children = flowPane.getChildren();

        // Assuming cbSort is a ComboBox<String> containing sorting options
        Comparator<Club> comparator = null;

        if ("nom".equals(cbSort.getValue())) {
            comparator = Comparator.comparing(Club::getNom_club);
        }

        if ("temps".equals(cbSort.getValue())) {
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
    void RedirectToAjoutClub(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutClub.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) flowPane.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }


    @FXML
    void RedirectToMenu()  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuModule.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) flowPane.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }


}
