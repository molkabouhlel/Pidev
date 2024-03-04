package com.esprit.activite.Controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
public class Sms {

    @FXML
    private TextField messageTextField;


    // Remplacez ces valeurs par les informations de votre compte Twilio
    private static final String ACCOUNT_SID = "ACa3f28d6690879f251648777668908995";
    private static final String AUTH_TOKEN = "9bceef077a57ab4902a4675e4b6d330d";
    private static final String TWILIO_PHONE_NUMBER = "+15134481071";
    private static final String VOTRE_NUMERO = "+21655020753";



    @FXML
    public void envoyerSMS() {
        // Obtenez le message à partir du champ de texte
        String message = messageTextField.getText();

        // Initialisez Twilio avec vos informations d'identification
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Envoyez le SMS
        Message twilioMessage = Message.creator(
                        new PhoneNumber(VOTRE_NUMERO), // Votre numéro de téléphone
                        new PhoneNumber(TWILIO_PHONE_NUMBER), // Votre numéro Twilio
                        message)
                .create();

        // Traitez la réponse de l'envoi du SMS si nécessaire
        System.out.println("SID du message Twilio : " + twilioMessage.getSid());

    // Affichez une confirmation à l'utilisateur
    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
    alerte.setTitle("SMS envoyé");
    alerte.setContentText("SMS envoyé avec succès votre participation est prise en consideration ");
    alerte.show();
}
}
