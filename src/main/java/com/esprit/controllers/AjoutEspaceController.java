package com.esprit.controllers;

import com.esprit.models.Espace;
import com.esprit.services.EspaceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;

public class AjoutEspaceController {

    @FXML
    private Button ajoutespace;

    @FXML
    private TextArea description;

    @FXML
    private AnchorPane formaddespace;

    @FXML
    private TextField heure_debut;

    @FXML
    private TextField heure_fin;


    @FXML
    private TextField nom_espace;

    public void initialize(){
        //controle saisie text
        nom_espace.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\d.*")) {
                nom_espace.setText(oldValue);
            }
        });
        nom_espace.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 15) {
                return change;
            }
            return null;
        }));

        description.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\d.*")) {
                description.setText(oldValue);
            }
        });

        //controle saisie Time
        heure_debut.setPromptText("hh:mm:ss");
        heure_debut.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}(:\\d{0,2}(:\\d{0,2})?)?")) {
                heure_debut.setText(oldValue);
            }
        });

        heure_fin.setPromptText("hh:mm:ss");
        heure_fin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}(:\\d{0,2}(:\\d{0,2})?)?")) {
                heure_fin.setText(oldValue);
            }
        });
    }

    @FXML
    void ajouterespace(ActionEvent event) throws IOException {


        EspaceService es = new EspaceService();
        es.ajouter(new Espace(nom_espace.getText(),description.getText() ,Time.valueOf(heure_debut.getText()),Time.valueOf(heure_fin.getText())));
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("espace ajout");
        alerte.setContentText("Espace bien ajoutee");
        alerte.show();
        nom_espace.setText("");
        description.setText("");
        heure_debut.setText("");
        heure_fin.setText("");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEspace.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) nom_espace.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void RedirectToEspaceAfficher(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEspace.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) nom_espace.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }

}
