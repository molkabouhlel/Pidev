package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.*;
import com.esprit.activite.services.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.util.List;

public class InterfacecoursController {

    @FXML
    private TextArea description_c;

    @FXML
    private TextField duree;

    @FXML
    private ComboBox<String> id_club;

    @FXML
    private ComboBox<String> id_coach;

    /*@FXML
    private ComboBox<typec> id_typec;*/
    @FXML
    private ComboBox<String> id_typec;


    @FXML
    private TextField image_c;

    @FXML
    private TextField nom_c;

    private static final String ACCOUNT_SID = "ACa3f28d6690879f251648777668908995";
    private static final String AUTH_TOKEN = "8aa630d189241f55e7c7a4dcb3e6c290";
    private static final String TWILIO_PHONE_NUMBER = "+15134481071";
    private static final String VOTRE_NUMERO = "+21655020753";
    @FXML
    void addcours(ActionEvent event) {
        if (validerChamps()) {
            CoursService es = new CoursService();
            String catSelectionne = id_typec.getValue();
            String clubselect =id_club.getValue();
            String id_caoch =id_coach.getValue();
                typec categoSelectionne = es.rechercherCatParNom(catSelectionne);
                Club clubselectionid =es.rechercherClubparnom(clubselect);
            Coach coachselectid=es.rechercherCatParNomCoach(id_caoch);
                if (categoSelectionne != null) {
            es.ajouter(new Cours(nom_c.getText(), description_c.getText(), image_c.getText(), Time.valueOf(duree.getText()), coachselectid,clubselectionid,categoSelectionne));
            envoyerSMSConfirmation();
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("cours ajout");
            alerte.setContentText("cours bien ajoutee");
            alerte.show();
        }}
    }

    @FXML
    void afficher(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Affichercours.fxml"));
        try{
            description_c.getScene().setRoot(loader.load());

        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void retourner(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            //
        }
    }

////image
@FXML
void browse(ActionEvent event) {

    FileChooser fileChooser = new FileChooser();


    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));


  //  fileChooser.setInitialDirectory(new File("C:/xampp/htdocs/Image"));

    File file = fileChooser.showOpenDialog(null);
    String xamppHtdocsPath = "C:/xampp/htdocs/Image/";
    File destinationFile = new File(xamppHtdocsPath + file.getName());
    try {
        // Copy the selected file to the htdocs directory
        Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File copied successfully to: " + destinationFile.getAbsolutePath());
    }catch (IOException e){
        System.out.println("Error copying file: " + e.getMessage());
    }

        if (destinationFile != null) {
        String imageFile = file.toURI().toString();
        imageFile = imageFile.substring(8);
        image_c.setText(imageFile);
        System.out.println(imageFile);


}}
    @FXML
    void initialize() {
        CoursService c = new CoursService();
        List<String> l = c.listCoach();
        id_coach.setItems(FXCollections.observableArrayList(l));
       // CoursService cs =new CoursService();
      //List<Integer> ev = c.rechercheclub();
        //id_club.setItems(FXCollections.observableArrayList(ev));/// ll cat ev
        List<String> nomClubList = c.listclub();
        id_club.setItems(FXCollections.observableArrayList(nomClubList));
       /* TypecService tc =new TypecService();
        List<typec> lc =tc.afficher();
        id_typec.setItems(FXCollections.observableArrayList(lc));*/

        List<String> nomCatList = c.listcategorie();
        id_typec.setItems(FXCollections.observableArrayList(nomCatList));

    }
    @FXML
    public boolean validerChamps() {
        if (nom_c.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        if (!nom_c.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'Noneve' doit contenir uniquement des lettres.");
            return false;
        }
        if (!validerDureeFormat(duree.getText())) {
            afficherAlerte("Le format de la durée est incorrect. Utilisez hh:mm:ss");
            return false;
        }


        return true;
    }
    private boolean validerDureeFormat(String duree) {
        // Expression régulière pour le format "hh:mm:ss"
        String regex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        return duree.matches(regex);
    }
    private void afficherAlerte(String message) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur de saisie");
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
    }
    private void envoyerSMSConfirmation() {


        String message = "Bonjour  merci de verifier la liste des cours ";

        // Initialisez Twilio avec vos informations d'identification
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Envoyez le SMS de confirmation
        Message twilioMessage = Message.creator(
                        new PhoneNumber(VOTRE_NUMERO),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();

        // Affichez le SID du message Twilio dans la console
        //  System.out.println("SID du message Twilio : " + twilioMessage.getSid());
    }

}

