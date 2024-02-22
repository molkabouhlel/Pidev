package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.Equipement;
import com.esprit.activite.services.EquipementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AfficherEquipementController {

    @FXML
    private ResourceBundle resources;
    private int idc;
    private String ref;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> description_eq;

    @FXML
    private TableColumn<?, ?> id_ceq;

    @FXML
    private TableColumn<?, ?> id_coach;

    @FXML
    private TableColumn<?, ?> id_espace;
    @FXML
    private Button supprimereq;


    @FXML
    private TableColumn<?, ?> id_m;

    @FXML
    private TableColumn<?, ?> image;

    @FXML
    private TableColumn<?, ?> nom_eq;

    @FXML
    private TableColumn<?, ?> quantite_dispo;

    @FXML
    private TableColumn<?, ?> ref_eq;

    @FXML
    private TableView<Equipement> tableview;
   private String idsel;


    @FXML
    void modifier(ActionEvent event) throws IOException {
        if (ref != null) {
            EquipementService es = new EquipementService();
            Equipement evselected = es.rechercheeq(ref);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEquipement.fxml"));
            Parent root = loader.load();
            ModifierEquipementController MEC = loader.getController();
            MEC.initData(evselected);
            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }

        }

    @FXML
    void initialize() {


        EquipementService c = new EquipementService();
        List<Equipement> catr = c.afficher();
        ObservableList<Equipement> observableList = FXCollections.observableList(catr);
        tableview.setItems(observableList);
        ref_eq.setCellValueFactory(new PropertyValueFactory<>("ref_eq"));
        nom_eq.setCellValueFactory(new PropertyValueFactory<>("nom_eq"));
        description_eq.setCellValueFactory(new PropertyValueFactory<>("description_eq"));
        quantite_dispo.setCellValueFactory(new PropertyValueFactory<>("quantite_dispo"));
        id_coach.setCellValueFactory(new PropertyValueFactory<>("id_coach"));
        id_espace.setCellValueFactory(new PropertyValueFactory<>("id_espace"));
        id_ceq.setCellValueFactory(new PropertyValueFactory<>("id_ceq"));
        id_m.setCellValueFactory(new PropertyValueFactory<>("id_m"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));







        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               // idc  = Integer.parseInt(newSelection.getRef_eq());

                ref = newSelection.getRef_eq();
                ref_eq.setText(String.valueOf(newSelection.getRef_eq()));
                //  date_rv.setText(Timestamp.valueOf(newSelection.getDate_rv()));
            } else {
                //idc = -1;
                ref =null;
            }
        });
    }
    @FXML
    void supprimer(ActionEvent event) {
        EquipementService c=new EquipementService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Equipement catASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(catASupprimer);

            // Mettez à jour la TableView
            tableview.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("eq nest pas selectioner");
            alerte.show();
        }

    }
}
