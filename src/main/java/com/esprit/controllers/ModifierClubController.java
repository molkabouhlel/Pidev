package com.esprit.controllers;

import com.esprit.models.Club;
import com.esprit.models.Espace;
import com.esprit.services.ClubService;
import com.esprit.services.EspaceService;
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
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

public class ModifierClubController implements Initializable  {

    @FXML
    private ComboBox<Espace> Espace;

    @FXML
    private Button ModifierClub;

    @FXML
    private Button Return;

    @FXML
    private TextField adresse_club;

    @FXML
    private TextArea description_club;

    @FXML
    private AnchorPane formaddespace;

    @FXML
    private TextField id_club;

    @FXML
    private TextField image_club;

    @FXML
    private TextField nom_club;

    @FXML
    private TextField temp_ouverture;

    private Club ClubToModifier;


    public void initData(Club C) {
        ClubToModifier = C;
        id_club.setText(String.valueOf(C.getId_club()));
        nom_club.setText(C.getNom_club());
        adresse_club.setText(C.getAdresse_club());
        description_club.setText(C.getDescription_club());
        image_club.setText(C.getImage_club());
        temp_ouverture.setText(String.valueOf(C.getTemp_ouverture()));
        Espace.setValue(C.getEspace());

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        EspaceService es = new EspaceService();
        List<Espace> l = es.afficher();                //HEDHI COMBOBOX BL LES ATTRIBUT ESPACE LKOL
        Espace.setItems(FXCollections.observableArrayList(l));
    }
    //sans combobox

    @FXML
    void ModifierClub(ActionEvent event) throws IOException {
        ClubService cs = new ClubService();
        Espace espace = Espace.getValue();
        //int numberOfQuestions = Integer.parseInt(nbr_questionstf.getText());
        ClubToModifier.setId_club(Integer.parseInt(id_club.getText()));
        ClubToModifier.setNom_club(nom_club.getText());
        ClubToModifier.setAdresse_club(adresse_club.getText());
        ClubToModifier.setDescription_club(description_club.getText());
        ClubToModifier.setImage_club(image_club.getText());
        ClubToModifier.setTemp_ouverture(Time.valueOf(temp_ouverture.getText()));

        ClubToModifier.setEspace(espace);

        cs.modifier(ClubToModifier);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modifier Club");
        alert.setContentText("Modification avec succces");
        alert.showAndWait();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void RedirectToClubAfficher(ActionEvent event)throws IOException {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    @FXML
    void Browse(ActionEvent event) {
        // select a file from the dialog box
        FileChooser fileChooser = new FileChooser();
        // image file extensions
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg", "*.gif"));
        fileChooser.setInitialDirectory(new File("C:/Users/thebe/OneDrive/Bureau/workshopjdbc/src/main/resources/Image"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String imageFile = file.toURI().toString();
            imageFile = imageFile.substring(8);
            image_club.setText(imageFile);
        }

    }


    /*@FXML
    void RedirectToMenu(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
*/
}

