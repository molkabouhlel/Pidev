package com.esprit.controllers;
import com.esprit.models.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import com.esprit.models.Produit;
import com.esprit.services.ProduitService;
import com.esprit.services.CategorieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
public class AffichageCategorieController {

    @FXML
    private TableColumn<Categorie, String> DescriptionC;

    @FXML
    private TableColumn<Categorie, String> NomC;
    @FXML
    private TableView<Categorie> tableviewC;
    @FXML
    private Button modifierC;
    @FXML
    private TableColumn<Categorie, Void> action;
    @FXML
    private Button supprimerC;
    public AffichageCategorieController() {
    }
    @FXML
    void initialize() {
        CategorieService p = new CategorieService();
        List<Categorie> categories = p.afficher();
        ObservableList<Categorie> observableList = FXCollections.observableList(categories);
        this.tableviewC.setItems(observableList);
        this. NomC.setCellValueFactory(new PropertyValueFactory("nom_cat"));
        this. DescriptionC.setCellValueFactory(new PropertyValueFactory("descr"));
        tableviewC.setEditable(true);
        this.NomC.setCellFactory(TextFieldTableCell.forTableColumn());
        this.DescriptionC.setCellFactory(TextFieldTableCell.forTableColumn());
        //modification nom double click
        this.NomC.setOnEditCommit(event -> {

            String newSujet = event.getNewValue();
            if (newSujet.trim().isEmpty()) {
                // Display error message for empty text
                Alert alertVide = new Alert(AlertType.ERROR);
                alertVide.setTitle("Erreur de Saisie");
                alertVide.setHeaderText("Titre vide!");
                alertVide.setContentText("Veuillez saisir le titre du sujet.");
                alertVide.show();
                return;
            } else if (newSujet.length() > 30) {
                // Display error message for exceeding length
                Alert alertLength = new Alert(AlertType.ERROR);
                alertLength.setTitle("Erreur de Saisie");
                alertLength.setHeaderText("Titre de sujet est trop longue!");
                alertLength.setContentText("Le titre ne doit pas dépasser 30 caractères.");
                alertLength.show();
                return;
            }

            Categorie cat = event.getRowValue();
            cat.setNom_cat(event.getNewValue());
            p.modifier(cat);
        });
        //modification description double click
        this.DescriptionC.setOnEditCommit(event -> {

            String newSujet = event.getNewValue();
            if (newSujet.trim().isEmpty()) {
                // Display error message for empty text
                Alert alertVide = new Alert(AlertType.ERROR);
                alertVide.setTitle("Erreur de Saisie");
                alertVide.setHeaderText("Titre vide!");
                alertVide.setContentText("Veuillez saisir le titre du sujet.");
                alertVide.show();
                return;
            } else if (newSujet.length() > 30) {
                // Display error message for exceeding length
                Alert alertLength = new Alert(AlertType.ERROR);
                alertLength.setTitle("Erreur de Saisie");
                alertLength.setHeaderText("Titre de sujet est trop longue!");
                alertLength.setContentText("Le titre ne doit pas dépasser 30 caractères.");
                alertLength.show();
                return;
            }

            Categorie cat = event.getRowValue();
            cat.setDescr(event.getNewValue());
            p.modifier(cat);
        });
        boutonsupp();
    }


    @FXML
    void modifierC(ActionEvent event) throws IOException {
        Categorie selected = (Categorie) this.tableviewC.getSelectionModel().getSelectedItem();
        if (selected != null) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ModifierCategorie.fxml"));
            Parent root = (Parent)loader.load();
            ModifierCategorieController mrc = (ModifierCategorieController) loader.getController();
            mrc.setReponseToModify(selected);
            this.tableviewC.getScene().setRoot(root);
        } else {
            Alert alertAjout = new Alert(AlertType.ERROR);
            alertAjout.setTitle("Erreur de modification");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir une réponse pour la modifier!");
            alertAjout.show();
        }
    }
    @FXML
    void supprimerC(ActionEvent event) {
        CategorieService p = new CategorieService();
        int selectedID = this.tableviewC.getSelectionModel().getSelectedIndex();
        if (selectedID >= 0) {
            Categorie coursASupprimer = (Categorie) this.tableviewC.getItems().get(selectedID);
            p.supprimer(coursASupprimer);
            this.tableviewC.getItems().remove(selectedID);
        } else {
            Alert alerte = new Alert(AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("Categorie nest pas selectioner");
            alerte.show();
        }
    }
    @FXML
    void retu(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MenuC.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }
    //TODO **************BOUTON SUPPRIMER cree tableview
    private void boutonsupp() {
        action.setCellFactory(col -> new TableCell<Categorie, Void>() {
            private final Button participerButton = new Button("supprimer");

            {
                participerButton.setOnAction(event -> {
                    //   tableview.edit(-1, null);
                    Categorie categorie = getTableView().getItems().get(getIndex());
                    supprimer(categorie);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(participerButton);
                }
            }
        });
    }
    private void supprimer(Categorie ev) {
        CategorieService p = new CategorieService();
        p.supprimer(ev);
        // Actualisez la TableView pour refléter la suppression
        tableviewC.getItems().remove(ev);
    }
}

