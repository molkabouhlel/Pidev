package com.esprit.controllers;
import com.esprit.models.Categorie;
import com.esprit.models.Produit;
import com.esprit.services.CategorieService;
import com.esprit.services.ProduitService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class AjoutCategorieController implements Initializable {
    @FXML
    private Button addC;
    @FXML
    private TextField tfDescriptionC;
    @FXML
    private TextField tfNomC;

    public AjoutCategorieController() {
    }

    @FXML
    void ajouterCategorie(ActionEvent event) throws IOException {
        CategorieService ps = new CategorieService();
        String description = tfDescriptionC.getText().trim();
        String nomC = tfNomC.getText().trim();
        if (nomC.isEmpty()) {
            // Display error message (e.g., using an Alert)
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Nom vide!");
            alertVide.setContentText("Veuillez saisir le nom de la categorie.");
            alertVide.show();
            return; // Prevent further execution if content is empty
        }
        if (description.isEmpty()) {
            // Display error message (e.g., using an Alert)
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Description vide!");
            alertVide.setContentText("Veuillez saisir la description de la categorie.");
            alertVide.show();
            return; // Prevent further execution if content is empty
        }
        ps.ajouter(new Categorie(tfNomC.getText(), tfDescriptionC.getText()));
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Categorie ajout");
        alerte.setContentText("Categorie bien ajoute");
        alerte.show();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    void ret(ActionEvent event) {
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

}



