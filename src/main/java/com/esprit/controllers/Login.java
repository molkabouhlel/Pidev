package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserServices;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class Login {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    private UserServices userServices;
    @FXML
    private ToggleButton togglePasswordBtn;
    @FXML
    private Label shownPassword;


    Preferences prefs = Preferences.systemNodeForPackage(this.getClass());
    public Login() {

        this.userServices = new UserServices();
    }

    @FXML
    private void initialize() {
        shownPassword.setVisible(false);

    }

    @FXML
    void togglePasswordVisibility(ActionEvent event) {
        if(togglePasswordBtn.isSelected()){
            shownPassword.setVisible(true);
            shownPassword.textProperty().bind(Bindings.concat(password.getText()));
            togglePasswordBtn.setText("hide");
        }
        else {
            shownPassword.setVisible(false);
            togglePasswordBtn.setText("show");
        }
    }
    @FXML
    void loginbutton(ActionEvent event) {
        String userEmail = email.getText();
        String userPassword = hashPassword(password.getText());
        try {
            if (userServices.login(userEmail, userPassword)) {
                User loggedInUser = userServices.getUserByEmail(userEmail);

                // Save the user into local storage using Preference
                prefs.put("cin", String.valueOf(loggedInUser.getCin()));
                prefs.put("nom", loggedInUser.getNom());
                prefs.put("prénom", loggedInUser.getPrénom());
                prefs.put("email", loggedInUser.getEmail());
                prefs.put("mdp", loggedInUser.getMdp());
                prefs.put("numT", String.valueOf(loggedInUser.getNumT()));
                prefs.put("role", loggedInUser.getRole());

                System.out.println(prefs.get("cin","not found"));
                if (loggedInUser != null) {
                    String role = loggedInUser.getRole();
                    switch (role) {
                        case "Admin":
                            showInfoAlert("Admin Login Successful!");
                            // Load the backadmin.fxml scene for the Admin
                            loadBackAdminScene();
                            break;
                        case "Membre":
                            showInfoAlert("Member Login Successful!");
                            loadMemberScene();
                            break;
                        case "Coach":
                            showInfoAlert("Coach Login Successful!");
                            loadCoachScene();
                            break;
                        default:
                            showInfoAlert("Login Successful!");
                            break;
                    }
                } else {
                    showErrorAlert("Invalid user details");
                }
            } else {
                // Failed login
                showErrorAlert("Invalid email or password");
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
            showErrorAlert("An error occurred during login");
        }
    }
    public String hashPassword(String password) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");


            md.update(password.getBytes());


            byte[] bytes = md.digest();


            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Method to load and switch to the backadmin.fxml scene
    private void loadBackAdminScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuPrincipal.fxml"));
            Parent root = loader.load();

            // Assuming you have a reference to the current stage (primaryStage)
            Stage stage = (Stage) email.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error alert)
        }
    }
    private void loadMemberScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceMenuMembre.fxml"));
            Parent root = loader.load();

            // Assuming you have a reference to the current stage (primaryStage)
            Stage stage = (Stage) email.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error alert)
        }
    }
    private void loadCoachScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceMenuMembre.fxml"));
            Parent root = loader.load();

            // Assuming you have a reference to the current stage (primaryStage)
            Stage stage = (Stage) email.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error alert)
        }
    }


    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void forget(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PutEmail.fxml"));
            Parent root = loader.load();

            // Assuming you have a reference to the current stage (primaryStage)
            Stage stage = (Stage) email.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error alert)
        }

    }
}
