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

public class InterfacecoursController {

    @FXML
    private TextArea description_c;

    @FXML
    private TextField duree;

    @FXML
    private TextField id_club;

    @FXML
    private TextField id_coach;

    @FXML
    private TextField id_typec;

    @FXML
    private TextField image_c;

    @FXML
    private TextField nom_c;

    @FXML
    void addcours(ActionEvent event) {
        CoursService es = new CoursService();
        typec tc=new typec();
        tc.setIdtypec(Integer.parseInt(id_typec.getText()));
        int idCoach = Integer.parseInt(id_coach.getText());
        int idClub =Integer.parseInt(id_club.getText());
        es.ajouter(new Cours(nom_c.getText(),description_c.getText() ,image_c.getText(), Time.valueOf(duree.getText()),idCoach,idClub,tc));
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("cours ajout");
        alerte.setContentText("cours bien ajoutee");
        alerte.show();

    }

    @FXML
    void afficher(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Affichercours.fxml"));
        try{
            description_c.getScene().setRoot(loader.load());

        }catch (IOException e){
            System.err.println(e.getMessage());
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
