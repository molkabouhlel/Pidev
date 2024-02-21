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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Updateevent {

    @FXML
    private TextField capeveup;

    @FXML
    private ComboBox<type_ev> comboidcatevup;

    @FXML
    private ComboBox<typec> comboidcatup;

    @FXML
    private ComboBox<Integer> comboidesup;

    @FXML
    private DatePicker datedebcombo;

    @FXML
    private DatePicker datefincombo;

    @FXML
    private TextArea deseveup;

    @FXML
    private TextField imeveup;

    @FXML
    private TextField idev;

    @FXML
    private TextField nomeveup;

    @FXML
    private TextField timedebup;

    @FXML
    private TextField timefinup;
    private Evenement tomodify;

    public void initData(Evenement c) {

        idev.setText(String.valueOf(c.getId_ev()));
        nomeveup.setText(c.getNom_ev());
        deseveup.setText(c.getDescription_ev());
        imeveup.setText(c.getImage_ev());
        comboidcatup.setValue(c.getId_typec());
        comboidcatevup.setValue(c.getId_type_ev());
        comboidesup.setValue(c.getId_espace());
        capeveup.setText(String.valueOf(c.getCapacite_max()));
    }
    @FXML
    void initialize() {
        TypecService ct = new TypecService();
        List<typec> l = ct.afficher();
        comboidcatup.setItems(FXCollections.observableArrayList(l));//combo box ll att mta3 type cours

        type_evService s = new type_evService();
        List<type_ev> ev = s.afficher();
        comboidcatevup.setItems(FXCollections.observableArrayList(ev));/// ll cat ev

        EvenementService evs = new EvenementService();
        List<Integer> lc = evs.rechercheIdEspace();
        comboidesup.setItems(FXCollections.observableArrayList(lc));

    }

    @FXML
    void modifier(ActionEvent event) {
            EvenementService es = new EvenementService();
        Evenement e =new Evenement();
        Date date = Date.valueOf(datedebcombo.getValue());
        String heure = timedebup.getText();
        String dateTimeString = date.toString() + " " + heure;
        Timestamp t = Timestamp.valueOf(dateTimeString);
          e.setDate_debut(t);

        Date datef = Date.valueOf(datefincombo.getValue());
        String heuref = timefinup.getText();
        String dateTimefinString = datef.toString() + " " + heuref;
        Timestamp tf = Timestamp.valueOf(dateTimefinString);
        e.setDate_fin(tf);
        ///int
        int capa = Integer.parseInt(capeveup.getText());
        int ice= Integer.parseInt(idev.getText());
        String nomev =nomeveup.getText();
        String des= deseveup.getText();
        String img=imeveup.getText();
        e.setImage_ev(img);
        e.setCapacite_max(capa);
        e.setNom_ev(nomev);
        e.setDescription_ev(des);
        e.setId_espace(comboidesup.getValue());
        e.setId_type_ev(comboidcatevup.getValue());
        e.setId_typec(comboidcatup.getValue());
        e.setId_ev(ice);
                  // Ajoutez le code pour récupérer la valeur de idcat si nécessaire

        es.modifier(e);

    }
    @FXML
    void retourner(ActionEvent event) {
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


}
