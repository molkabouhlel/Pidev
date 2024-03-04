package controllers;

import com.esprit.Models.Membre;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMembre {

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

    @FXML
    void showuser(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage


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
        // Validation checks
        if (validateFields() && validateFormat()) {
            UserServices us = new UserServices();
            us.add(new Membre(mail.getText(), mdp.getText(), FN.getText(), LN.getText(), Integer.parseInt(TTF.getText()), "Membre", ""));
            System.out.println("Adding Member: ");
        }
    }

    private boolean validateFields() {
        // Validation for Empty Fields
        if (FN.getText().isEmpty() || LN.getText().isEmpty() || TTF.getText().isEmpty() || mail.getText().isEmpty() || mdp.getText().isEmpty() || confirm.getText().isEmpty()) {
            showAlert("Empty Fields", "Please fill in all fields.");
            return false;
        }

        return true;
    }

    private boolean validateFormat() {
        // Validation for First Name and Last Name (only letters allowed)
        if (!FN.getText().matches("[a-zA-Z]+") || !LN.getText().matches("[a-zA-Z]+")) {
            showAlert("Invalid Input", "First Name and Last Name should only contain letters.");
            return false;
        }

        // Validation for Telephone (only digits allowed)
        if (!TTF.getText().matches("\\d+")) {
            showAlert("Invalid Telephone", "Telephone should contain only digits.");
            return false;
        }


        if (!isValidEmail(mail.getText())) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }


        if (mdp.getText().length() < 6) {
            showAlert("Invalid Password", "Password should be at least 6 characters.");
            return false;
        }


        if (!mdp.getText().equals(confirm.getText())) {
            showAlert("Password Mismatch", "Passwords do not match.");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        // Regular expression for a valid email address
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
