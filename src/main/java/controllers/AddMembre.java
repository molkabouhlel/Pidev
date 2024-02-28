package controllers;
import com.esprit.Models.Admin;
import com.esprit.Models.Membre;
import com.esprit.Models.User;
import com.esprit.Services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;


public class AddMembre {

    @FXML
    private TextField FN;

    @FXML
    private TextField LN;

    @FXML
    private TextField TTF;

    @FXML
    private Button add;


    //@FXML
   // private TextField abonnement;

    @FXML
    private TextField confirm;

    @FXML
    private TextField mail;

    @FXML
    private TextField mdp;

    @FXML
    private Button retourajout;
    @FXML
    void retourajout(ActionEvent event) {
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

    public void showuser(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gestionMembre.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    @FXML
    void addMembre(ActionEvent event) {
UserServices us ;
        String email = mail.getText();
        String password = mdp.getText();
        String confirmPassword = confirm.getText();  // New line to get confirm password
        String firstName = FN.getText();
        String lastName = LN.getText();
        String phoneText = TTF.getText();
        us = new UserServices();
        us.add(new Membre(email, password, firstName, lastName, Integer.parseInt(phoneText), "Membre",""));
        System.out.println("Adding Member: ");

    }



}

