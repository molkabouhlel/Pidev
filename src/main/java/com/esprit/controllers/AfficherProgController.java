package com.esprit.controllers;

import com.esprit.models.Programme;
import com.esprit.services.ProgrammeServices;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProgController implements Initializable {

    @FXML
    private Button Btajout_prog;

    @FXML
    private Button Btmodifier_prog;

    @FXML
    private Button Btsupp_prog;

    @FXML
    private TableColumn<Programme, Integer> Iduser_prog;

    @FXML
    private TableView<Programme> Prog_TableView;

    @FXML
    private TableColumn<Programme, Date> datedeb_prog;

    @FXML
    private TableColumn<Programme,Date> datefin_prog;

    @FXML
    private TableColumn<Programme,String> description_prog;

    @FXML
    private TableColumn<Programme,String> etatfin_prog;

    @FXML
    private TableColumn<Programme, String> etatinitial_prog;

    @FXML
    private TableColumn<Programme, Integer> id_prog;

    @FXML
    private TableColumn<Programme, Float> rate_prog;

    private int Id_prog_Selected;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProgrammeServices es = new ProgrammeServices();
        List<Programme> listProg = es.afficher();
        //System.out.println(es.afficher());

        ObservableList<Programme> ProgObservableList = FXCollections.observableArrayList(listProg);
        Prog_TableView.setItems(ProgObservableList);
        id_prog.setCellValueFactory(new PropertyValueFactory<>("ID_prog"));
        description_prog.setCellValueFactory(new PropertyValueFactory<>("desc_prog"));
        rate_prog.setCellValueFactory(new PropertyValueFactory<>("rate"));
        etatinitial_prog.setCellValueFactory(new PropertyValueFactory<>("etat_initial"));
        etatfin_prog.setCellValueFactory(new PropertyValueFactory<>("etat_final"));
        datedeb_prog.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        datefin_prog.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        Iduser_prog.setCellValueFactory(new PropertyValueFactory<>("ID_user"));


        Prog_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Id_prog_Selected = newSelection.getID_prog();
            } else {
                Id_prog_Selected = -1;
            }
        });
    }
    @FXML
    void RedirectToModifyForm_prog(ActionEvent event)  throws IOException {
        if (Id_prog_Selected != -1) {
            ProgrammeServices es = new ProgrammeServices();
            Programme Espace_Selected = es.rechercheProgramme(Id_prog_Selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ModifyProg.fxml"));
            Parent root = loader.load();
            ModifyProgController MEC = loader.getController();
            MEC.initData(Espace_Selected);
            Stage currentStage = (Stage) Prog_TableView.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }

    }

    @FXML
    void SupprimerProg(ActionEvent event) {
        if (Id_prog_Selected != -1) {
            ProgrammeServices es = new ProgrammeServices();
            Programme Espace_Selected = es.rechercheProgramme(Id_prog_Selected);
            es.supprimer(Espace_Selected);
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("suppresion Espace");
            alerte.setContentText("Espace  supprime");
            alerte.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AfficherProg.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) Prog_TableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void redirecttoAjoutForm_prog(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/addprog.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Prog_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }

}
