package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.Evenement;
import com.esprit.activite.modeles.type_ev;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EvenementService;
import com.esprit.activite.services.TypecService;
import com.esprit.activite.services.type_evService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class Addevent {

    @FXML
    private TextField capeve;

    @FXML
    private ComboBox<typec> comboidcat;

    @FXML
    private ComboBox<type_ev> comboidcatev;

    @FXML
    private ComboBox<Integer> comboides;

    @FXML
    private TextArea deseve;

    @FXML
    private TextField imeve;

    @FXML
    private TextField nomeve;

    @FXML
    private TextField timedeb;

    @FXML
    private TextField timefin;
    @FXML
    private DatePicker datedebcomb;

    @FXML
    private DatePicker datefincom;

    @FXML
    void initialize() {
        TypecService c = new TypecService();
        List<typec> l = c.afficher();
        comboidcat.setItems(FXCollections.observableArrayList(l));//combo box ll att mta3 type cours

        type_evService s = new type_evService();
        List<type_ev> ev = s.afficher();
        comboidcatev.setItems(FXCollections.observableArrayList(ev));/// ll cat ev

        EvenementService evs = new EvenementService();
        List<Integer> lc = evs.rechercheIdEspace();
        comboides.setItems(FXCollections.observableArrayList(lc));
    }

    @FXML
    void addev(ActionEvent event) {

        EvenementService es = new EvenementService();
        /// debut
        Date date = Date.valueOf(datedebcomb.getValue());
        String heure = timedeb.getText();
        String dateTimeString = date.toString() + " " + heure;
        Timestamp t = Timestamp.valueOf(dateTimeString);
        ////fin
        Date datef = Date.valueOf(datefincom.getValue());
        String heuref = timefin.getText();
        String dateTimefinString = datef.toString() + " " + heuref;
        Timestamp tf = Timestamp.valueOf(dateTimefinString);
        ///int
        int capa = Integer.parseInt(capeve.getText());

        es.ajouter(new Evenement(nomeve.getText(), deseve.getText(), imeve.getText(), t, tf, capa, comboides.getValue(), comboidcat.getValue(), comboidcatev.getValue()));

        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("cours ajout");
        alerte.setContentText("cours bien ajoutee");
        alerte.show();
//
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
    void afficher(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherevent.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }


    }
    @FXML
    void retourner(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }
}
