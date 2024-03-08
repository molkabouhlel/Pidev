package com.esprit.controllers;
import com.esprit.models.Categorie;
import com.esprit.models.Produit;
import com.esprit.models.score;
import com.esprit.services.CategorieService;
import com.esprit.services.ProduitService;
import com.esprit.services.ScoreService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AjoutScoreController implements Initializable {


    @FXML
    private ComboBox<String> id_produit;

    @FXML
    private TextField tfnote;

    @FXML
    void addscore(ActionEvent event) {
        ScoreService ss = new ScoreService();
        double tf_note = Double.parseDouble(this.tfnote.getText());
        String produit = id_produit.getValue();
        if (tfnote.toString().isEmpty() ) {

            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("note vide !");
            alertVide.setContentText("Veuillez saisir la note du produit.");
            alertVide.show();
            return;
        }
        if (produit==null) {

            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("produit vide!");
            alertVide.setContentText("Veuillez saisir le produit.");
            alertVide.show();
            return;
        }
        ProduitService ps=new ProduitService();
        Produit p=ps.rechercheCategorienom(produit);
        ss.ajouter(new score(p ,tf_note));
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("score ajout");
        alerte.setContentText("score bien ajoute");
        alerte.show();
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProduitService es = new ProduitService();
        List<String> l=es.Recuperernom();
        id_produit.setItems(FXCollections.observableArrayList(l));
    }

    @FXML
    void ret(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichagescore.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }


}

