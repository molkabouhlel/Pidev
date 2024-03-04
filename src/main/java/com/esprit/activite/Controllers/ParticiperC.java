package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.*;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.participerService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.scene.image.Image;

public class ParticiperC {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboides;

    @FXML
    private TextField emailp;

    @FXML
    private TextField nomp;

    @FXML
    private TextField prenomp;

    private Participer p;
    @FXML
    void PARTICIPER(ActionEvent event) {

        if (validerChamps()) {
            String nomCoursSelectionne = comboides.getValue();

            if (nomCoursSelectionne != null) {
                participerService es = new participerService();
                Cours coursSelectionne = es.rechercherCoursParNom(nomCoursSelectionne);
                // System.out.println(coursSelectionne);
                if (coursSelectionne != null) {
                    es.ajouter(new Participer(coursSelectionne, nomCoursSelectionne, nomp.getText(), prenomp.getText(), emailp.getText()));
                    System.out.println(coursSelectionne.getId());
                    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                    alerte.setTitle("Participation ajoutée");
                    alerte.setContentText("Participation bien ajoutée");
                    alerte.show();
                   /* //mail
                    // Envoi de l'e-mail de confirmation
                    String to = "molkabouhlel19@gmail.com"; // Adresse e-mail du destinataire
                    String subject = "Confirmation de réservation";
                    String body = "Votre participation a été confirmée.";

                    ParticipationConfirmationApp.sendEmail(to, subject, body);

                    // Afficher une confirmation à l'utilisateur
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setTitle("Confirmation");
                    alert1.setContentText("Reservation ajoutée avec succès et email de confirmation envoyé.");
                    alert1.showAndWait();*/
                }
            }
            }
        }


    @FXML
    void retourner(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/imageC.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void initialize() {
     //  Cours c =new Cours();
        participerService evs = new participerService();
        List<String> nomCoursList = evs.listnomcours();
        comboides.setItems(FXCollections.observableArrayList(nomCoursList));


    }
    @FXML
    public boolean validerChamps() {
        if (nomp.getText().isEmpty() || prenomp.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }


        if (!nomp.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'nom' doit contenir uniquement des lettres.");
            return false;
        }
        if (!prenomp.getText().matches("[a-zA-Z]+")){
            afficherAlerte("Le champ 'prenom' doit contenir uniquement des lettres.");
            return false;

        }
        if (!validerFormatEmail(emailp.getText())) {
            afficherAlerte("Veuillez saisir une adresse e-mail valide.");
            return false;
        }

        return true;
    }



    private void afficherAlerte(String message) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur de saisie");
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
    }
    private boolean validerFormatEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    @FXML
    void liste(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/affichp.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }
}
