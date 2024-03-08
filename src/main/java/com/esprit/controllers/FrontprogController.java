package com.esprit.controllers;

import javafx.scene.control.Control;

import com.esprit.models.Programme;
import com.esprit.services.ProgrammeServices;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.esprit.services.ProgrammeServices;
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
import javafx.fxml.FXML;
import org.controlsfx.control.Rating;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FrontprogController {
    @FXML
    private TextField recherche;
    @FXML
    private Rating rating;

    @FXML
    private ComboBox<String> cbSort;

    @FXML
    private Button retour;

    @FXML
    private FlowPane flowPane;
    private int Id_Prog_Selected;

    private List<Node> originalNodes = new ArrayList<>();

    public void initialize() {
        cbSort.setValue("nom");
        cbSort.getItems().addAll("nom", "date", "");
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
        // Programme Programme = new Programme();
        //  rating.ratingProperty().bindBidirectional(Programme.rateProperty();


    }


    public void initFlowpane(){

        ProgrammeServices cs = new ProgrammeServices();
        List<Programme> ClubList = cs.afficher();
        flowPane.setHgap(30);
        flowPane.setVgap(30);


        // Parcours la liste d'images et crée des VBox pour chaque image
        for (Programme programme : ClubList) {
            VBox Vbox = createVBox(programme);
            Vbox.setUserData(programme); // Set the Club object as user data for the VBox

            // Handle the Details button click
            Button Details = (Button) Vbox.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);
            if (Details != null) {
                Details.setOnAction(event -> {
                    Id_Prog_Selected = programme.getID_prog();
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
                        Label clubNameLabel = (Label) vBox.getChildren().get(1);
                        String clubName = clubNameLabel.getText().toLowerCase();
                        return clubName.startsWith(searchText);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(filteredNodes);
    }




    private VBox createVBox(Programme P) {
        VBox Vbox = new VBox();
        Vbox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(120);
        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        separator3.setPrefWidth(120);

        Label nom_club = new Label(P.getNom_prog());
        nom_club.setAlignment(Pos.CENTER);

        Label label2 = new Label("Description: " + P.getDesc_prog());

        // Utilisez votre attribut Rating pour afficher la valeur du taux sans la possibilité de la modifier
        rating = new Rating((int) P.getRate());

        Button Details = new Button("Details");
        Details.setPrefWidth(150);
        Details.setStyle("-fx-content-display: LEFT;");

        Button Delete = new Button("Delete");
        Delete.setId("deleteButton");
        Delete.setPrefWidth(150);
        Delete.setStyle("-fx-content-display: LEFT;");

        Label label3 = new Label("Date debut: " + P.getDate_debut());

        Vbox.getChildren().addAll(nom_club, separator1, label2, rating, label3, Details, separator3);

        return Vbox;
    }



    private void showDetails() {
        System.out.println("Showing details for club ID: " + Id_Prog_Selected);
        if (Id_Prog_Selected != -1) {
            ProgrammeServices cs = new ProgrammeServices();
            Programme ProgSelected = cs.rechercheProgramme(Id_Prog_Selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/frontobjectif.fxml"));
            try {
                Parent root = loader.load();
                FrontobjController LCC = loader.getController();
                LCC.init(ProgSelected);
                LCC.initialize(ProgSelected);
                Stage currentStage = (Stage) flowPane.getScene().getWindow();
                currentStage.setScene(new Scene(root));
                System.out.println("Navigating to frontobjectif.fxml");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    @FXML
    void trier() {
        // Assuming your flowPane contains Programme objects
        ObservableList<Node> children = flowPane.getChildren();

// Assuming cbSort is a ComboBox<String> containing sorting options
        Comparator<Programme> comparator = null;

        if ("nom".equals(cbSort.getValue())) {
            comparator = Comparator.comparing(Programme::getNom_prog);
        }

        if ("date".equals(cbSort.getValue())) {
            comparator = Comparator.comparing(Programme::getDate_debut, Comparator.nullsLast(Comparator.naturalOrder()));
        }

        if (comparator != null) {
            List<Node> sortedChildren = new ArrayList<>(children);
            Comparator<Programme> finalComparator = comparator;
            sortedChildren.sort(new Comparator<Node>() {
                @Override
                public int compare(Node n1, Node n2) {
                    Programme programme1 = (Programme) n1.getUserData();
                    Programme programme2 = (Programme) n2.getUserData();
                    // Check for null references
                    if (programme1 == null && programme2 == null) {
                        return 0;
                    }
                    if (programme1 == null) {
                        return 1;
                    }
                    if (programme2 == null) {
                        return -1;
                    }
                    // Use the finalComparator to compare Programme objects
                    return finalComparator.compare(programme1, programme2);
                }
            });

            flowPane.getChildren().setAll(sortedChildren);
        }
    }
}
