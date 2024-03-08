package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.EmailService;
import com.esprit.services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class PutEmail {
    @FXML
    private Button envoiB;


    @FXML
    private TextField emailenvoi;

    @FXML
    void envoi(ActionEvent event) {
        try {
            System.out.println(emailenvoi.getText());
            UserServices us = new UserServices();
            User u = new User();
            u = us.findemail(emailenvoi.getText());
            System.out.println(us.findemail(emailenvoi.getText()));
            if (u.getNom() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("User not found");
                alert.setContentText("User not found");
                alert.show();
            } else {
                EmailService emails = new EmailService();
                Random random = new Random();
                int randomnumber = generateRandomNumber(8);
                emails.sendEmail(emailenvoi.getText(), "Code De confirmation", "vous voulez changer votre mot de passe,\n Merci D'Ecrire Ce Code Pour Confirmer  : " + randomnumber + ".");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PutCode.fxml"));
                Parent root = loader.load();
                PutCode updateController = loader.getController();
                Stage stage1 = (Stage) envoiB.getScene().getWindow();
                stage1.close();
                updateController.initCode(randomnumber, emailenvoi.getText());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
            // You may choose to display an error message to the user if the FXML file cannot be loaded
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
            // You may choose to display a generic error message to the user
        }
    }

    public static int generateRandomNumber(int maxDigits) {
        Random random = new Random();
        // Calculate the maximum value based on the number of digits
        int max = (int) Math.pow(10, maxDigits) - 1;
        // Generate a random number within the specified range
        return random.nextInt(max);
    }

}