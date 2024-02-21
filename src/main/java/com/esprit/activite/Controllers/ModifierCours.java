package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.services.CoursService;
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
import java.sql.Time;

public class ModifierCours {

    @FXML
    private TextArea desc;

    @FXML
    private TextField drc;

    @FXML
    private TextField idc;
    @FXML
    private TextField idcou;

    @FXML
    private TextField idcat;

    @FXML
    private TextField idcl;

    @FXML
    private TextField imc;

    @FXML
    private TextField nomc;
    ////
    private Cours CoursToModify;

    public void initData(Cours c) {
        idcou.setText(String.valueOf(c.getId()));
        nomc.setText(c.getNom());
        desc.setText(c.getDescription());
        imc.setText(c.getImagec());
        drc.setText(String.valueOf(c.getDuree()));
        idc.setText(String.valueOf(c.getIdcoach()));
        idcl.setText(String.valueOf(c.getIdclub()));
    }


    @FXML
    void reafficherc(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichercours.fxml"));
        try {
            desc.getScene().setRoot(loader.load());

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void updatec(ActionEvent event) throws IOException {
        CoursService es = new CoursService();
        Cours c = new Cours();
        typec tc = new typec();
        int idCours = Integer.parseInt(idcou.getText());
        String nom = nomc.getText();
        String description = desc.getText();
        String image = imc.getText();
        Time duree = Time.valueOf(drc.getText());
        int idCoach = Integer.parseInt(idc.getText());
        int idClub = Integer.parseInt(idcl.getText());
        // Assurez-vous d'ajuster en fonction de votre interface graphique
        int idTypec = Integer.parseInt(idcat.getText());
         tc.setIdtypec(idTypec);

        // Ajoutez le code pour récupérer la valeur de idcat si nécessaire

        es.modifier(new Cours(idCours, nom, description, image, duree, idCoach, idClub, tc));


    }

}

