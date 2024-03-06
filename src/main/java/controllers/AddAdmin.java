package controllers;
import com.esprit.Models.Admin;
import com.esprit.Services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class AddAdmin implements Initializable {

    @FXML
    private Pane panecaptcha;
    @FXML
    private TextField FN;

    @FXML
    private TextField LN;

    @FXML
    private Canvas capchac;

    @FXML
    private TextField captchaText;
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
    private String captcha;



    @FXML
    void addAdmin(ActionEvent event) {
        // Get input values from the form
        //String captcha = generateCaptcha();
        String captchatext = captchaText.getText();
      /*  TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("CAPTCHA Verification");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter the CAPTCHA code:\n");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String userInput = result.get();*/

            if (!captchatext.equals(captcha)) {
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
                /*if (!password.equals(confirmPassword)) {
                    showAlert(AlertType.ERROR, "Password Mismatch", "Password and confirm password do not match.");
                    return; // Stop processing if passwords do not match
                }*/
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
    //}

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
    void refreshbutton(ActionEvent event) {
        generateCaptcha();
        drawCaptcha();
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
    private void generateCaptcha() {
        captcha = generateRandomString(6); // Change 6 to the desired length
    }

    private void drawCaptcha() {
        GraphicsContext gc = capchac.getGraphicsContext2D();
        gc.clearRect(0, 0, capchac.getWidth(), capchac.getHeight());
        gc.setFont(Font.font("Arial", 20));
        gc.setStroke(javafx.scene.paint.Color.BLACK);
        gc.setLineWidth(2.0);

        Random random = new Random();
        for (int i = 0; i < captcha.length(); i++) {
            gc.save();
            gc.translate(30 * i + 10, 30);
            gc.rotate(random.nextInt(20) - 10);
            gc.strokeText(String.valueOf(captcha.charAt(i)), 0, 0);
            gc.restore();
        }
        gc.beginPath();
        gc.moveTo(0, 25);
        for (int i = 0; i < captcha.length() * 30; i += 5)
            gc.lineTo(i, 25 + (random.nextInt(6) - 3));
        gc.stroke();
    }

    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateCaptcha();
        drawCaptcha();
    }
}

