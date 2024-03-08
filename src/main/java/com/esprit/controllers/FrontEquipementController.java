package com.esprit.controllers;

import com.esprit.models.Equipement;
import com.esprit.services.EquipementService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.File;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FrontEquipementController {
    @FXML
    private TextField search;

    @FXML
    private ChoiceBox<String> choice;

    @FXML
    private FlowPane flowPane;
    private List<Node> originalNodes = new ArrayList<>();


    public void initialize() {
        EquipementService equipementService = new EquipementService();
        List<Equipement> equipementliste = equipementService.afficher();
        flowPane.setHgap(30);
        flowPane.setVgap(30);

        //  crée des VBox pour chaque image
        for (Equipement equipement : equipementliste) {
            VBox vBox = createImageWithButtonVBox(equipement);
            vBox.setUserData(equipement);

            flowPane.getChildren().add(vBox);
        }
        originalNodes.addAll(flowPane.getChildren());
        ////recherche ///
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercher();
        });
        search.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && search.getText().isEmpty()) {
                flowPane.getChildren().setAll(originalNodes);
            }
        });
        //filtre
        List<String> types = equipementliste.stream()
                .map(equipement -> equipement.getId_ceq().getType_ceq())
                .distinct()
                .collect(Collectors.toList());
        choice.getItems().addAll(types);

        // Ajouter un écouteur pour la ChoiceBox
        choice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterByType(newValue);
            }
        });
    }

    private VBox createImageWithButtonVBox(Equipement equipement) {
        VBox vBox = new VBox();
        //vbox style
        vBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        // Crée l'ImageView pour l'image
        String imageUrl = equipement.getImage();//rcupere limage
        ImageView imageView = createImageView(imageUrl);//cree image view
        //image style
        imageView.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(120);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(120);
        //
        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(120);
        Separator separator4 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(120);
        Label nomeq = new Label(equipement.getNom_eq());
        // Bouton "Plus de détails"
        Button detailsButton = new Button("Plus de détails");

        //style
        nomeq.setAlignment(Pos.CENTER);
        //stackpane
        StackPane labelPane = new StackPane();
        labelPane.getChildren().add(nomeq);
        //label description
        Label label2 = new Label("Description: " + equipement.getDescription_eq());


        // Ajoute l'ImageView et le bouton
        vBox.getChildren().addAll(labelPane, separator1, imageView, separator2, label2,separator3,detailsButton);

        return vBox;

    }


    private ImageView createImageView(String imageUrl) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(130);

        try {
            File file = new File(imageUrl);
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageView;
    }




    private void rechercher() {
        String searchText = search.getText().toLowerCase();

        List<Node> filteredNodes = flowPane.getChildren().stream()
                .filter(node -> {
                    if (node instanceof VBox) {
                        VBox vBox = (VBox) node;

                        // Check if the first child is a StackPane
                        if (vBox.getChildren().get(0) instanceof StackPane) {
                            StackPane stackPane = (StackPane) vBox.getChildren().get(0);

                            // Check if the StackPane contains a Label
                            if (stackPane.getChildren().get(0) instanceof Label) {
                                Label courNameLabel = (Label) stackPane.getChildren().get(0);
                                String courName = courNameLabel.getText().toLowerCase();
                                return courName.startsWith(searchText);
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());

        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(filteredNodes);
    }
 /*  @FXML
    void trier(ActionEvent event) {

        ObservableList<Node> children = flowPane.getChildren();

        //trie
        Comparator<Equipement> comparator = null;

        if ("nom_eq".equals(trie.getValue())) {
            comparator = Comparator.comparing(Equipement::getNom_eq);
        }

        if ("quantite_dispo".equals(trie.getValue())) {
            comparator = Comparator.comparing(Equipement::getQuantite_dispo);
        }

        List<Node> sortedChildren = new ArrayList<>(children);
        Comparator<Equipement> finalComparator = comparator;
        sortedChildren.sort(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                Equipement c1 = (Equipement) n1.getUserData();
                Equipement c2 = (Equipement) n2.getUserData();
                return finalComparator.compare(c1, c2);
            }
        });

        flowPane.getChildren().setAll(sortedChildren);
    }*/
    private void filterByType(String selectedType) {
        List<Node> filteredNodes = originalNodes.stream()
                .filter(node -> {
                    if (node instanceof VBox) {
                        Equipement evenement = (Equipement) ((VBox) node).getUserData();
                        return evenement.getId_ceq().getType_ceq().equals(selectedType);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        flowPane.getChildren().setAll(filteredNodes);
    }


    @FXML
    void retour()  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceMenuMembre.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) flowPane.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }

}

