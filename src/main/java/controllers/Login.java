package controllers;

import com.esprit.Models.User;
import com.esprit.Services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Login {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    private UserServices userServices;

    public Login() {

        this.userServices = new UserServices();
    }


    @FXML
    void loginbutton(ActionEvent event) {
        String userEmail = email.getText();
        String userPassword = password.getText();

        try {
            if (userServices.login(userEmail, userPassword)) {
                User loggedInUser = userServices.getUserByEmail(userEmail);

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
                            break;
                        case "Coach":
                            showInfoAlert("Coach Login Successful!");
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

    // Method to load and switch to the backadmin.fxml scene
    private void loadBackAdminScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backadmin.fxml"));
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
}
