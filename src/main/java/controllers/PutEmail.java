package controllers;

import com.esprit.Models.User;
import com.esprit.Services.EmailService;
import com.esprit.Services.UserServices;
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
        UserServices us=new UserServices();
        User u = new User();
        u=us.findemail(emailenvoi.getText());
        System.out.println(emailenvoi.getText());
        if(u.getNom()==null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("User not found");
            alert.setContentText("User not found");
            alert.show();
        }
        else
        {


            EmailService emails=new EmailService();
            Random random=new Random();
            int randomnumber = generateRandomNumber(8);
            emails.sendEmail(emailenvoi.getText(),"Code De confirmation","vous voulez changer votre mot de passe,\n Merci D'Ecrire Ce Code Pour Confirmer  : "+randomnumber+".");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PutCode.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PutCode updateController = loader.getController();
            Stage stage1=(Stage)envoiB.getScene().getWindow();
            stage1.close();
            updateController.initCode(randomnumber,emailenvoi.getText());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

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