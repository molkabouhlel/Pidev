
package controllers;

import com.esprit.Models.Coach;

import com.esprit.Services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class AddCoach {

    @FXML
    private TextField FN;

    @FXML
    private TextField LN;

    @FXML
    private TextField TTF;

    @FXML
    private Button add;

    @FXML
    private TextField confirm;

    @FXML
    private TextField mail;

    @FXML
    private TextField mdp;

    @FXML
    private Button retourcoach;

    @FXML
    private TextField adresse;

    @FXML
    void retourcoach(ActionEvent event) {
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
        }
    }

    @FXML
    void showcoach(ActionEvent event) {
        // Code pour afficher les détails du coach (si nécessaire)
    }

    @FXML
    void addCoach(ActionEvent event) {

            UserServices us = new UserServices();
            us.add(new Coach(mail.getText(), mdp.getText(),FN.getText(),LN.getText(), Integer.parseInt(TTF.getText()), "Coach", adresse.getText()));


    }
}
