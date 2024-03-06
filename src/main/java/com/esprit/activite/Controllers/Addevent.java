package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.*;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EvenementService;
import com.esprit.activite.services.TypecService;
import com.esprit.activite.services.type_evService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class Addevent {

    @FXML
    private TextField capeve;


    @FXML
    private ComboBox<String> listcatcours;

    @FXML
    private ComboBox<String> listcateve;

    @FXML
    private ComboBox<String> listeespace;


    @FXML
    private TextArea deseve;

    @FXML
    private TextField imeve;

    @FXML
    private TextField nomeve;

    @FXML
    private DatePicker datedebcomb;

    @FXML
    private DatePicker datefincom;
    @FXML
    private Spinner<Integer> mindeb;

    @FXML
    private Spinner<Integer> timedeb;

    @FXML
    private Spinner<Integer> timefin;

    @FXML
    private Spinner<Integer> timefinmin;


    @FXML
    void initialize() {
        TypecService c = new TypecService();
        EvenementService ev =new EvenementService();
       CoursService cours =new CoursService();

            EvenementService evv =new EvenementService();
        timedeb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        mindeb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        timefin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        timefinmin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

        List<String> nomCatList = cours.listcategorie();
        listcatcours.setItems(FXCollections.observableArrayList(nomCatList));

        List<String> espaceList = ev.listespace();
        listeespace.setItems(FXCollections.observableArrayList(espaceList));

        List<String> catevList = ev.listcategorieevent();
        listcateve.setItems(FXCollections.observableArrayList(catevList));

        //


    }

    @FXML
    void addev(ActionEvent event) {
        CoursService c =new CoursService();
        EvenementService es = new EvenementService();
        if (validerChamps()) {
            //1  /// debut
            int selectedHour = timedeb.getValue();
            int selectedMinute = mindeb.getValue();
            LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);

            LocalDate date = datedebcomb.getValue();

            LocalDateTime localDateTime = LocalDateTime.of(date, selectedTime);

            Timestamp t = Timestamp.valueOf(localDateTime);
            ////fin
            LocalDate datef = datefincom.getValue();
            //2
            int selectedHour2 = timefin.getValue();
            int selectedMinute2 = timefinmin.getValue();
            LocalTime selectedTime2 = LocalTime.of(selectedHour2, selectedMinute2);
            LocalDateTime localDateTime2 = LocalDateTime.of(datef, selectedTime2);
            Timestamp tf = Timestamp.valueOf(localDateTime2);
            ///int
            String catCSelectionne = listcatcours.getValue();
            String espaceselect =listeespace.getValue();
            String CatEveselect =listcateve.getValue();

            typec categoSelectionne = c.rechercherCatParNom(catCSelectionne);
            espace espaceselectionid =es.rechercherespaceparnom(espaceselect);
            type_ev evecatidselected =es.rechercherCateveParNom(CatEveselect);
            ///

            int capa = Integer.parseInt(capeve.getText());
            es.ajouter(new Evenement(nomeve.getText(), deseve.getText(), imeve.getText(), t, tf, capa, espaceselectionid, categoSelectionne, evecatidselected));
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("confirmation");
            alerte.setContentText("evenement ajoutee");
            alerte.show();
//
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();


            try {
                Parent root = FXMLLoader.load(getClass().getResource("/addevent.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }else {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("erreur");
            alerte.setContentText("evenement non ajoutee");
            alerte.show();
        }

    }

    @FXML
    void afficher(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherevent.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

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

        }
    }




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
            imeve.setText(imageFile);
            System.out.println(imageFile);
        }}

    @FXML
    public boolean validerChamps() {
        if (nomeve.getText().isEmpty() || datedebcomb.getValue() == null || datefincom.getValue() == null) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        LocalDate dateActuelle = LocalDate.now();
        LocalDate dateDebut = datedebcomb.getValue();
        LocalDate dateFin = datefincom.getValue();

        if (dateDebut.isBefore(dateActuelle) || dateFin.isBefore(dateActuelle) || dateFin.isBefore(dateDebut)) {
            afficherAlerte("Les dates doivent être postérieures à la date actuelle.");
            return false;
        }

        if (!capeve.getText().matches("\\d*")) {
            afficherAlerte("Le champ 'Capeve' doit contenir uniquement des chiffres.");
            return false;
        }
        if (!nomeve.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'Noneve' doit contenir uniquement des lettres.");
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
}




