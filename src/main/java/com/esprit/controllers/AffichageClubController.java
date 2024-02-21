package com.esprit.controllers;

import com.esprit.models.Club;
import com.esprit.models.Espace;
import com.esprit.services.ClubService;
import com.esprit.services.EspaceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

public class AffichageClubController implements Initializable {

    @FXML
    private Button AjoutClub;

    @FXML
    private TableView<Club> Club_TableView;

    @FXML
    private Button Modifier_Club;

    @FXML
    private Button SupprimerClub;

    @FXML
    private TableColumn<Club,Time> Temp_ouverture;

    @FXML
    private TableColumn<Club,String> adresse_club;

    @FXML
    private TableColumn<Club, String> description_club;

    @FXML
    private TableColumn<Club,Integer> id_club;

    @FXML
    private TableColumn<Club,Integer> espace;

    @FXML
    private TableColumn<Club, String> image_club;

    @FXML
    private TableColumn<Club, String> nom_club;


    private int Id_Club_Selected;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClubService cs = new ClubService();
        List<Club> listClub = cs.afficher();
        //System.out.println(cs.afficher());

        ObservableList<Club> EspaceObservableList = FXCollections.observableArrayList(listClub);
        Club_TableView.setItems(EspaceObservableList);
        id_club.setCellValueFactory(new PropertyValueFactory<>("id_club"));
        nom_club.setCellValueFactory(new PropertyValueFactory<>("nom_club"));
        adresse_club.setCellValueFactory(new PropertyValueFactory<>("adresse_club"));
        description_club.setCellValueFactory(new PropertyValueFactory<>("description_club"));
        image_club.setCellValueFactory(new PropertyValueFactory<>("image_club"));
        Temp_ouverture.setCellValueFactory(new PropertyValueFactory<>("temp_ouverture"));


        espace.setCellValueFactory(new PropertyValueFactory<>("espace"));


        Club_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Id_Club_Selected = newSelection.getId_club();
            } else {
                Id_Club_Selected = -1;
            }
        });


    }

    @FXML
    void RedirectToModifyForm(ActionEvent event) throws IOException {
        if (Id_Club_Selected != -1) {
            ClubService cs = new ClubService();
            Club Club_selected = cs.rechercheClub(Id_Club_Selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierClub.fxml"));
            Parent root = loader.load();
            ModifierClubController MCC = loader.getController();
            MCC.initData(Club_selected);
            Stage currentStage = (Stage) Club_TableView.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }
    }

    @FXML
    void SupprimerClub(ActionEvent event) {
        if (Id_Club_Selected != -1) {
            ClubService cs = new ClubService();
            Club Club_Selected = cs.rechercheClub(Id_Club_Selected);
            cs.supprimer(Club_Selected);
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("suppresion Club");
            alerte.setContentText("Club  supprime");
            alerte.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageClub.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) Club_TableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void redirecttoAjoutForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutClub.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Club_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));


    }

}

