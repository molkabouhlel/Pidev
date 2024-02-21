package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.Evenement;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EvenementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Afficherevent {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> capacite;

    @FXML
    private TableColumn<?, ?> dated;

    @FXML
    private TableColumn<?, ?> datefin;

    @FXML
    private TableColumn<?, ?> deseven;

    @FXML
    private TableColumn<?, ?> evnom;

    @FXML
    private TableColumn<?, ?> id_c;

    @FXML
    private TableColumn<?, ?> id_catev;

    @FXML
    private TableColumn<?, ?> id_es;

    @FXML
    private TableColumn<?, ?> imageurl;

    @FXML
    private TableView<Evenement> viewev;
    private int idevselected;


    @FXML
    void modifaffich(ActionEvent event) throws IOException {
        if (idevselected != -1) {
            EvenementService es = new EvenementService();
            Evenement evselected = es.rechercheEV(idevselected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateevent.fxml"));
            Parent root = loader.load();
            Updateevent MEC = loader.getController();
            MEC.initData(evselected);
            Stage currentStage = (Stage) viewev.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }

    }

    @FXML
    void retourner(ActionEvent event) {


Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/addevent.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

    @FXML
    void supp(ActionEvent event) {
        EvenementService c=new EvenementService();


        int selectedID = viewev.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Evenement EvASupprimer = viewev.getItems().get(selectedID);


            c.supprimer(EvASupprimer);

            // Mettez à jour la TableView
            viewev.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("cours nest pas selectioner");
            alerte.show();
        }

    }

    @FXML
    void initialize() {
        EvenementService c = new EvenementService();
        List<Evenement> EV = c.afficher();
        ObservableList<Evenement> observableList = FXCollections.observableList(EV);
        viewev.setItems(observableList);
        evnom.setCellValueFactory(new PropertyValueFactory<>("nom_ev"));
        deseven.setCellValueFactory(new PropertyValueFactory<>("description_ev"));
        imageurl.setCellValueFactory(new PropertyValueFactory<>("image_ev"));
        dated.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        datefin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        capacite.setCellValueFactory(new PropertyValueFactory<>("capacite_max"));
        id_es.setCellValueFactory(new PropertyValueFactory<>("id_espace"));
        id_c.setCellValueFactory(new PropertyValueFactory<>("id_typec"));
        id_catev.setCellValueFactory(new PropertyValueFactory<>("id_type_ev"));
        viewev.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idevselected = newSelection.getId_ev();
            } else {
                idevselected = -1;
            }
        });

    }
    }


