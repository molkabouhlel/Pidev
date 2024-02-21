package com.esprit.controllers;

import com.esprit.models.Espace;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEspaceController implements Initializable {

    @FXML
    private Button AjoutEspace;

    @FXML
    private TableView<Espace> Espace_TableView;

    @FXML
    private TableColumn<Espace,String> description_espace;

    @FXML
    private TableColumn<Espace, Time>  heure_debut;

    @FXML
    private TableColumn<Espace, Time> heure_fin;

    @FXML
    private TableColumn<Espace, Integer> id_espace;

    @FXML
    private TableColumn<Espace, String> nom_espace;

    private int Id_espace_Selected;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        EspaceService es = new EspaceService();
        List<Espace> listEspace = es.afficher();
        //System.out.println(es.afficher());

        ObservableList<Espace> EspaceObservableList = FXCollections.observableArrayList(listEspace);
        Espace_TableView.setItems(EspaceObservableList);
        id_espace.setCellValueFactory(new PropertyValueFactory<>("id_espace"));
        nom_espace.setCellValueFactory(new PropertyValueFactory<>("nom_espace"));
        description_espace.setCellValueFactory(new PropertyValueFactory<>("description_espace"));
        heure_debut.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
        heure_fin.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));



        Espace_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Id_espace_Selected = newSelection.getId_espace();
            } else {
                Id_espace_Selected = -1;
            }
        });


    }

    @FXML
    void redirecttoAjoutForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutEspace.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Espace_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }




    @FXML
    void RedirectToModifyForm(ActionEvent event) throws IOException {
        if (Id_espace_Selected != -1) {
            EspaceService es = new EspaceService();
            Espace Espace_Selected = es.rechercheEspace(Id_espace_Selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEspace.fxml"));
            Parent root = loader.load();
            ModifierEspaceController MEC = loader.getController();
            MEC.initData(Espace_Selected);
            Stage currentStage = (Stage) Espace_TableView.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }
    }



    @FXML
    void SupprimerEspace(ActionEvent event) {
        if (Id_espace_Selected != -1) {
            EspaceService es = new EspaceService();
            Espace Espace_Selected = es.rechercheEspace(Id_espace_Selected);
            es.supprimer(Espace_Selected);
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("suppresion Espace");
            alerte.setContentText("Espace  supprime");
            alerte.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEspace.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) Espace_TableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}



