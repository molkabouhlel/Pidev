package com.esprit.controllers;

import com.esprit.models.Club;

import com.esprit.models.Espace;
import com.esprit.models.ListCours;
import com.esprit.services.ClubService;

import com.esprit.services.EspaceService;
import com.esprit.services.ListCoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class DetailClubController {



    @FXML
    private ComboBox<String> Espace;
    @FXML
    private TextField adresse_club;

    @FXML
    private TextArea description_club;

    @FXML
    private TextField id_club;

    @FXML
    private TextField image_club;

    @FXML
    private TextField nom_club;

    @FXML
    private TextField temp_ouverture;

    @FXML
    private ImageView imageClub;


///////////////////////////////////////////////////////////////////////////

    @FXML
    private Button Ajout_ListeCours;

    @FXML
    private TextField Club;

    @FXML
    private Button DeleteListeCours;

    @FXML
    private TableView<ListCours> ListeCourd_TableView;

    @FXML
    private Button ModifyListeCours;

    @FXML
    private TableColumn<ListCours, Integer> cours;

    @FXML
    private TableColumn<ListCours, Integer> id_liste;
    @FXML
    private TableColumn<ListCours, String> Clubcolonne;

    @FXML
    private TextField id_ListeCours;

    @FXML
    private ComboBox<Integer> id_cours;

    private int  List_Selected;
    private int Club_selected;
    private ListCours ListCoursToModifier;

    private Club ClubToModifier;



    public void initClub(Club C) {


        ClubToModifier = C;
        //id_club.setText(String.valueOf(C.getId_club()));
        nom_club.setText(C.getNom_club());
        adresse_club.setText(C.getAdresse_club());
        description_club.setText(C.getDescription_club());
        image_club.setText(C.getImage_club());
        temp_ouverture.setText(String.valueOf(C.getTemp_ouverture()));
        Espace.setValue(C.getEspace().getNom_espace());

        String imageUrl = image_club.getText();
        System.out.println(imageUrl);

        Image image = new Image(new File(imageUrl).toURI().toString());
        imageClub.setImage(image);
        imageClub.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        Club_selected=C.getId_club();
    }


    public void initialize(Club C) {
        EspaceService es = new EspaceService();
        ///////////////////////////CONTROLE SAISIE//////////////////////////
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

        //controle saisie Time
        temp_ouverture.setPromptText("hh:mm:ss");
        temp_ouverture.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}(:\\d{0,2}(:\\d{0,2})?)?")) {
                temp_ouverture.setText(oldValue);
            }
        });

        Espace.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String e = Espace.getValue();
                Espace E = es.rechercheEspacenom(e);
                temp_ouverture.setText(String.valueOf(E.getHeure_debut()));
                temp_ouverture.setEditable(false);
            }
        });


        //////////////////////////////////////////////////////////////////////////////
        ClubToModifier = C;
        ClubService cs = new ClubService();
    }






    @FXML
    void Browse(ActionEvent event) {
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
        String imageUrl = image_club.getText();
        System.out.println(imageUrl);

        Image image = new Image(new File(imageUrl).toURI().toString());
        imageClub.setImage(image);
        imageClub.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

    }



    @FXML
    void RedirectToAffichageClub(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichageClubFront.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) imageClub.getScene().getWindow();
        currentStage.setScene(new Scene(root));


    }
}


