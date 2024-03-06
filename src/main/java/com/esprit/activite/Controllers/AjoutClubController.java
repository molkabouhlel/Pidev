package com.esprit.activite.Controllers;


import com.esprit.activite.modeles.*;

import com.esprit.activite.services.ClubService;
import com.esprit.activite.services.EspaceService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

public class AjoutClubController  implements Initializable {

    @FXML
    private Button Return;

    @FXML
    private TextField adresse_club;

    @FXML
    private Button ajoutclub;

    @FXML
    private TextArea description_club;

    @FXML
    private AnchorPane formaddespace;

    @FXML
    private ComboBox<Espace> Espace;

    @FXML
    private TextField image_club;


    @FXML
    private TextField nom_club;

    @FXML
    private TextField temp_ouverture;

    private Espace espace;


    @FXML
    private Label timeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //HEDHI COMBOBOX BL LES ATTRIBUT ESPACE LKOL
        EspaceService es=new EspaceService();
        List<Espace> l=es.afficher();
        Espace.setItems(FXCollections.observableArrayList(l));
        Espace.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                temp_ouverture.setText(String.valueOf(newValue.getHeure_debut()));
                temp_ouverture.setEditable(false);
            }
        });
        /*timePicker = new JFXTimePicker();
        timePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Selected time: " + newVal);
        });*/




////////////////////////////////////////CONTROLE SAISIE///////////////////////////////////////////
        //controle saisie text
        nom_club.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\d.*")) {
                nom_club.setText(oldValue);
            }
        });
        nom_club.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 15) {
                return change;
            }
            return null;
        }));

        adresse_club.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\d.*")) {
                adresse_club.setText(oldValue);
            }
        });
        adresse_club.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 15) {
                return change;
            }
            return null;
        }));

        description_club.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\d.*")) {
                adresse_club.setText(oldValue);
            }
        });


        /*List<Integer> l=es.Return_idEspace();                //HEDHI COMBOBOX BL ID
        Espace.setItems(FXCollections.observableArrayList(l));*/

    }


    @FXML
    void browseimage(ActionEvent event) {
        // select a file from the dialog box
        FileChooser fileChooser = new FileChooser();
        // image file extensions
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        String xamppHtdocsPath = "C:/xampp/htdocs/Image/";

        File destinationFile = new File(xamppHtdocsPath + selectedFile.getName());
        try {
            // Copy the selected file to the htdocs directory
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully to: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
        }

        if (destinationFile != null) {
            String imageFile = destinationFile.toURI().toString();
            imageFile = imageFile.substring(8);
            image_club.setText(imageFile);
        }

    }
    

    @FXML
    void RedirectToClubAfficher(ActionEvent event)  throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void ajouterclub(ActionEvent event) throws IOException {
            ClubService cs = new ClubService();
            EspaceService es = new EspaceService();

            //Espace e=es.rechercheEspace(Espace.getValue());

            cs.ajouter(new Club(nom_club.getText(),adresse_club.getText() ,description_club.getText(),image_club.getText(),Time.valueOf(temp_ouverture.getText()),Espace.getValue()) );
            //cs.ajouter(new Club(nom_club.getText(),adresse_club.getText() ,description_club.getText(),image_club.getText(),Time.valueOf(temp_ouverture.getText()),e) );
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("espace ajout");
        alerte.setContentText("Espace bien ajoutee");
        alerte.show();
        nom_club.setText("");
        adresse_club.setText("");
        description_club.setText("");
        image_club.setText("");
        temp_ouverture.setText("");
        //id_Espace.setValue(null);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) nom_club.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }



}

