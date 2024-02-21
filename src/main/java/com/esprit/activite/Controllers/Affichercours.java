package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.services.CoursService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class Affichercours {

    @FXML
    private TableColumn<Cours,String> desc;

    @FXML
    private TableColumn<Cours, Time> dureec;

    @FXML
    private TableColumn<Cours,Integer> id_typc;

    @FXML
    private TableColumn<Cours,Integer> idcc;

    @FXML
    private TableColumn<Cours,Integer> idclub;

    @FXML
    private TableColumn<Cours, String> imgc;

    @FXML
    private TableColumn<Cours, String> nomc;

    @FXML
    private TableView<Cours> tableview;
    private int idcoursselected;
    @FXML
    void returnlist(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/interfacecours.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }

    }

    @FXML
    void initialize() {
        CoursService c = new CoursService();
        List<Cours> cours = c.afficher();
        ObservableList<Cours> observableList = FXCollections.observableList(cours);
        tableview.setItems(observableList);
        nomc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        imgc.setCellValueFactory(new PropertyValueFactory<>("imagec"));
        dureec.setCellValueFactory(new PropertyValueFactory<>("duree"));
        idcc.setCellValueFactory(new PropertyValueFactory<>("idcoach"));
        idclub.setCellValueFactory(new PropertyValueFactory<>("idclub"));
        id_typc.setCellValueFactory(new PropertyValueFactory<>("id_typec"));
        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idcoursselected = newSelection.getId();
            } else {
                idcoursselected = -1;
            }
        });
    }
    @FXML
    void modifiercours(ActionEvent event) throws IOException {
        if (idcoursselected != -1) {
            CoursService es = new CoursService();
            Cours coursselected = es.rechercheCours(idcoursselected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierCours.fxml"));
            Parent root = loader.load();
            ModifierCours MEC = loader.getController();
            MEC.initData(coursselected);
            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }



    }


    @FXML
    void supprimercours(ActionEvent event) {
        CoursService c=new CoursService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Cours coursASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(coursASupprimer);

            // Mettez à jour la TableView
            tableview.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("cours nest pas selectioner");
            alerte.show();
        }
    }
}

