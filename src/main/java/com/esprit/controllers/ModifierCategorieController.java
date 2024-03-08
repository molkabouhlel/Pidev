package com.esprit.controllers;
import com.esprit.models.Categorie;
import com.esprit.models.Produit;
import com.esprit.services.CategorieService;
import com.esprit.services.ProduitService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifierCategorieController {

    @FXML
    private TextField DescC;

    @FXML
    private TextField NomC;

    @FXML
    private Button modifierC;
    private Categorie reponseToModify;
    public ModifierCategorieController() {
    }
    public void setReponseToModify(Categorie Categorie1) {
        this.reponseToModify = Categorie1;
        this.populateTextFields();
    }
    private void populateTextFields() {
        if (this.reponseToModify != null) {
            this.NomC.setText(this.reponseToModify.getNom_cat());
            this.DescC.setText(this.reponseToModify.getDescr());
        } else {
            this.NomC.clear();
            this.DescC.clear();
        }
    }
    @FXML
    void modifierCategorie(ActionEvent event) {
        CategorieService es = new CategorieService();
        reponseToModify.setNom_cat(NomC.getText());
        reponseToModify.setDescr(DescC.getText());
        es.modifier(reponseToModify);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modifier Categorie");
        alert.setContentText("Modification avec succces");
        alert.show();
    }
    @FXML
    void retur(ActionEvent event) {
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

