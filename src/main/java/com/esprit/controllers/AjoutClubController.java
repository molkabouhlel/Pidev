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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EspaceService es=new EspaceService();
        List<Espace> l=es.afficher();                //HEDHI COMBOBOX BL LES ATTRIBUT ESPACE LKOL
        Espace.setItems(FXCollections.observableArrayList(l));

        /*List<Integer> l=es.Return_idEspace();                //HEDHI COMBOBOX BL ID
        Espace.setItems(FXCollections.observableArrayList(l));*/

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

