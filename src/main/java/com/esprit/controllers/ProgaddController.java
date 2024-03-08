package com.esprit.controllers;

import com.esprit.models.Programme;
import com.esprit.services.ProgrammeServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.prefs.Preferences;

public class ProgaddController {
    Preferences prefs = Preferences.systemNodeForPackage(this.getClass());

    @FXML
    private DatePicker datedebtxt;

    @FXML
    private DatePicker datefintxt;

    @FXML
    private TextArea descrprogText;

    @FXML
    private TextField etatfinText;

    @FXML
    private TextField etatintText;

    @FXML
    private TextField iduserText;
    @FXML
    private TextField nomText;
    @FXML
    private TextField rateText;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    void initialize() {
        iduserText.setText(prefs.get("cin","not found"));
    }
    @FXML
    void ajouterProg(ActionEvent event) {
        /////////////////////////////////////////////////CONTROLE DE SAISIE //////////////////////////////////////////
        ////////////////////////////////////DATE//////////////////////////////////////
        if (datedebtxt.getValue() == null || datefintxt.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs de date vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une date pour le début et la fin du programme.");
            alert.showAndWait();
            return;
        }

        // Vérifier si la date de début est antérieure à la date actuelle
        LocalDate currentDate = LocalDate.now();
        if (datedebtxt.getValue().isBefore(currentDate)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Date de début invalide");
            alert.setHeaderText(null);
            alert.setContentText("La date de début ne peut pas être antérieure à la date actuelle.");
            alert.showAndWait();
            return;
        }
        // Vérifier si la date de fin est postérieure à la date de début
        LocalDate dateDebut = datedebtxt.getValue();
        LocalDate dateFin = datefintxt.getValue();
        if (dateFin.isBefore(dateDebut)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Date de fin invalide");
            alert.setHeaderText(null);
            alert.setContentText("La date de fin doit être postérieure à la date de début.");
            alert.showAndWait();
            return;
        }
        ///////////////////////////////////////// NOM PROG /////////////////////////////////////////////
        if (nomText.getText().isEmpty() || descrprogText.getText().isEmpty() || etatintText.getText().isEmpty() || iduserText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs obligatoires vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires : description , nom , etat initial et ID user .");
            alert.showAndWait();
            return;
        }

        ProgrammeServices es = new ProgrammeServices();
        try {
            String nom_prog = nomText.getText();
            String descrProg = descrprogText.getText();
           // float rate = Float.parseFloat(rateText.getText());
            String etatInit = etatintText.getText();
            //String etatFin = etatfinText.getText();
            Date datedeb = dateFormat.parse(datedebtxt.getValue().toString());
            Date datefin = dateFormat.parse(datefintxt.getValue().toString());
            int idUser = Integer.parseInt(iduserText.getText());

            Programme programme = new Programme(nom_prog,descrProg, etatInit, new java.sql.Date(datedeb.getTime()), new java.sql.Date(datefin.getTime()), idUser);
            es.ajouter(programme);

            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Programme ajouté");
            alerte.setContentText("Le programme a été ajouté avec succès");
            alerte.show();

            // Effacer les champs après l'ajout
            nomText.clear();
            descrprogText.clear();
            //rateText.clear();
            etatintText.clear();
           // etatfinText.clear();
            datedebtxt.getEditor().clear();
            datefintxt.getEditor().clear();
            iduserText.clear();

            // Redirection vers la vue d'affichage des programmes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AfficherProg.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) descrprogText.getScene().getWindow(); // Utilisation d'un élément de l'interface utilisateur pour obtenir la scène
            currentStage.setScene(new Scene(root));
        } catch (IOException | ParseException | NumberFormatException e) {
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
        Stage currentStage = (Stage) datedebtxt.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }

}
