package controllers;
import com.esprit.Models.Admin;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

public class AddAdmin {

    @FXML
    private Pane panecaptcha;
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
    void addAdmin(ActionEvent event) {
        // Get input values from the form
        String captcha = generateCaptcha();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("CAPTCHA Verification");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter the CAPTCHA code:\n");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String userInput = result.get();

            if (!userInput.equals(captcha)) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("CAPTCHA Verification Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("The CAPTCHA code you entered is incorrect. Please try again.");
                alert1.showAndWait();
            } else {

                String email = mail.getText();
                String password = hashPassword(mdp.getText());
                String confirmPassword = confirm.getText();  // New line to get confirm password
                String firstName = FN.getText();
                String lastName = LN.getText();
                String phoneText = TTF.getText();

                // Validate  all fields
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneText.isEmpty()) {
                    showAlert(AlertType.ERROR, "Input Error", "Please fill in all fields.");
                    return;
                }

                // Validate email format
                if (!isValidEmail(email)) {
                    showAlert(AlertType.ERROR, "Input Error", "Invalid email format. Please enter a valid email address.");
                    return;
                }

                // Validate password
                if (!isValidPassword(password)) {
                    showAlert(AlertType.ERROR, "Invalid Password", "Password must be at least 5 characters long and contain at least one special character.");
                    return; // Stop processing if the password is invalid
                }

                // Validate password confirmation
                if (!password.equals(confirmPassword)) {
                    showAlert(AlertType.ERROR, "Password Mismatch", "Password and confirm password do not match.");
                    return; // Stop processing if passwords do not match
                }
                // Validate first name contains only alphabetic characters
                if (!isValidName(firstName)) {
                    showAlert(AlertType.ERROR, "Input Error", "Invalid first name. Please enter only alphabetic characters.");
                    return;
                }

                // Validate last name contains only alphabetic characters
                if (!isValidName(lastName)) {
                    showAlert(AlertType.ERROR, "Input Error", "Invalid last name. Please enter only alphabetic characters.");
                    return;
                }

                // Validate phone number is a valid integer and has a length of 8
                if (!isValidPhoneNumber(phoneText)) {
                    showAlert(AlertType.ERROR, "Input Error", "Invalid phone number. Please enter a valid 8-digit number.");
                    return;
                }

                // Now you can proceed to add the admin
                UserServices us = new UserServices();
                us.add(new Admin(email,password, firstName, lastName, Integer.parseInt(phoneText), "Admin"));
                System.out.println("Adding Admin: ");
            }

        }
    }

    private boolean isValidPassword(String password) {
        // Add your password validation logic here
        // For example, check if it's at least 5 characters long and contains at least one special character
        return password.length() >= 5 && password.matches(".*[!@#$%^&*()-_=+{}|<>?].*");
    }
    private boolean isValidName(String name) {
        // Validate name contains only alphabetic characters
        return name.matches("^[a-zA-Z]+$");
    }
    private boolean isValidEmail(String email) {
        // Simple email format validation using regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneText) {
        // Validate phone number contains only digits and has a length of 8
        return phoneText.matches("\\d{8}");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


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
    void showAdmin(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backadmin.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String generateCaptcha() {
        String captcha = generateRandomString(4);
        panecaptcha.getChildren().clear();
        Random random = new Random();
        for (int i = 0; i < captcha.length(); i++) {
            char character = captcha.charAt(i);
            Shape shape;
            if (Character.isDigit(character)) {
                shape = new Rectangle(30, 30);
            } else {
                shape = null;
            }

            if (shape != null) {



                shape.setLayoutX(i * 50 + 20);
                shape.setLayoutY(30);

                shape.setRotate(random.nextInt(40) - 20);

                panecaptcha.getChildren().add(shape);
            }
            Text text = new Text(String.valueOf(character));
            text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            text.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

            text.setLayoutX(i * 50 + 30);
            text.setLayoutY(50);

            text.setRotate(random.nextInt(40) - 15);

            panecaptcha.getChildren().add(text);
        }

        return captcha;
    }


    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
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

}

