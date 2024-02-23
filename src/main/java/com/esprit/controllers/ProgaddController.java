package com.esprit.controllers;

import com.esprit.models.Programme;
import com.esprit.services.ProgrammeServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;


public class ProgaddController {

    @FXML
    private TextField datedebText;

    @FXML
    private TextField datefinText;

    @FXML
    private TextArea descrprogText;

    @FXML
    private TextField etatfinText;

    @FXML
    private TextField etatintText;

    @FXML
    private TextField iduserText;

    @FXML
    private TextField rateText;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @FXML

    void ajouterProg(ActionEvent event) throws IOException, ParseException {
        ProgrammeServices es = new ProgrammeServices();
        float rate = Float.parseFloat(rateText.getText());
        String descrProg = descrprogText.getText();
        String etatInit = etatintText.getText();
        String etatFin = etatfinText.getText();
        java.util.Date datedeb = dateFormat.parse(datedebText.getText());
        java.util.Date datefin = dateFormat.parse(datefinText.getText());
        int idUser = Integer.parseInt(iduserText.getText());

        Programme programme = new Programme(descrProg, rate, etatInit, etatFin, new java.sql.Date(datedeb.getTime()), new java.sql.Date(datefin.getTime()), idUser);
        es.ajouter(programme);

        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Programme ajouté");
        alerte.setContentText("Le programme a été ajouté avec succès");
        alerte.show();

        // Effacer les champs après l'ajout
        descrprogText.clear();
        rateText.clear();
        etatintText.clear();
        etatfinText.clear();
        datedebText.clear();
        datefinText.clear();
        iduserText.clear();

        // Redirection vers la vue d'affichage des programmes
        try {
            // Redirection vers la vue d'affichage des programmes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AfficherProg.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) descrprogText.getScene().getWindow(); // Utilisation d'un élément de l'interface utilisateur pour obtenir la scène
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // Afficher un message d'erreur en cas d'échec de chargement du fichier FXML
            Alert alerte1 = new Alert(Alert.AlertType.ERROR);
            alerte1.setTitle("Erreur de chargement");
            alerte1.setContentText("Impossible de charger la vue d'affichage des programmes.");
            alerte1.show();
        }
    }
    @FXML
    void RedirectToProgAfficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AfficherProg.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) datedebText.getScene().getWindow();
        currentStage.setScene(new Scene(root));


    }

}

