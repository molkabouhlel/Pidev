package com.esprit.controllers;

import com.esprit.models.Programme;
import com.esprit.services.ProgrammeServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;


public class ModifyProgController {

    @FXML
    private TextField ID_prog;

    @FXML
    private TextField datedeb;

    @FXML
    private TextField datefin;

    @FXML
    private TextArea descrprog;

    @FXML
    private TextField etatfin;

    @FXML
    private TextField etatint;

    @FXML
    private TextField iduser;

    @FXML
    private TextField rate;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Programme ProgrammeToModifier;

    public void initData(Programme P) {
        ProgrammeToModifier = P;
        ID_prog.setText(String.valueOf(P.getID_prog()));
        descrprog.setText(P.getDesc_prog());
        rate.setText(String.valueOf(P.getRate()));
        etatint.setText(P.getEtat_initial());
        etatfin.setText(P.getEtat_final());
        datedeb.setText(dateFormat.format(P.getDate_debut()));
        datefin.setText(dateFormat.format(P.getDate_fin()));
        iduser.setText(String.valueOf(P.getID_user()));
    }


    @FXML
    void ModifierProg(ActionEvent event) throws IOException {
        ProgrammeServices es = new ProgrammeServices();

        ProgrammeToModifier.setID_prog(Integer.parseInt(ID_prog.getText()));
        ProgrammeToModifier.setDesc_prog(descrprog.getText());
        ProgrammeToModifier.setRate(Float.parseFloat(rate.getText()));
        ProgrammeToModifier.setEtat_initial(etatint.getText());
        ProgrammeToModifier.setEtat_final(etatfin.getText());

            Date dateDebut = Date.valueOf(datedeb.getText());
            ProgrammeToModifier.setDate_debut(dateDebut);

            Date dateFin = Date.valueOf(datefin.getText());
            ProgrammeToModifier.setDate_fin(dateFin);


        ProgrammeToModifier.setID_user(Integer.parseInt(iduser.getText()));

        es.modifier(ProgrammeToModifier);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modifier Programme");
        alert.setContentText("Modification avec succès");
        alert.showAndWait();

        Stage currentStage = (Stage) ID_prog.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AfficherProg.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void RedirectToProgrammeAfficher(ActionEvent event) throws IOException {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AfficherProg.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
    }
}
