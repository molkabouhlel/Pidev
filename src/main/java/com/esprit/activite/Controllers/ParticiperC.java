package com.esprit.activite.Controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.Participer;
import com.esprit.activite.services.participerService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.FXCollections;
//import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ParticiperC {


    @FXML
    private ComboBox<String> comboides;

    @FXML
    private TextField emailp;

    @FXML
    private TextField nomp;

    @FXML
    private TextField prenomp;

    @FXML
    private TextField numT;

    @FXML
    private ImageView qrCodeImageView;

    private Participer p;
    private String qrCodeDescription;  // Ajout de la variable

    @FXML
    void PARTICIPER(ActionEvent event) {
        if (validerChamps()) {
            String nomCoursSelectionne = comboides.getValue();
            int num = Integer.parseInt(numT.getText());
            if (nomCoursSelectionne != null) {
                participerService es = new participerService();
                Cours coursSelectionne = es.rechercherCoursParNom(nomCoursSelectionne);
                if (coursSelectionne != null) {
                    if(es.utilisateurExiste(nomp.getText(),prenomp.getText())){
                    es.ajouter(new Participer(coursSelectionne, nomCoursSelectionne, nomp.getText(), prenomp.getText(), emailp.getText(), num,2));}
//                    envoyerSMSConfirmation();
                    qrCodeDescription = "le participant nom:" + nomp.getText() + " prenom:" + prenomp.getText() + " vient de participer au cours" + nomCoursSelectionne + " cliquer sur valider et vous recevez un sms de confirmation";
                    generateQRCode(nomp.getText(), prenomp.getText(), nomCoursSelectionne);
                    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                    alerte.setTitle("Participation ajoutée");
                    alerte.setContentText("Participation bien ajoutée");
                    alerte.show();
                }
            }}
        }




    @FXML
    void retourner(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/imageC.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        participerService evs = new participerService();
        List<String> nomCoursList = evs.listnomcours();
        comboides.setItems(FXCollections.observableArrayList(nomCoursList));
    }

    @FXML
    public boolean validerChamps() {
        if (nomp.getText().isEmpty() || prenomp.getText().isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return false;
        }

        if (!nomp.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'nom' doit contenir uniquement des lettres.");
            return false;
        }
        if (!prenomp.getText().matches("[a-zA-Z]+")) {
            afficherAlerte("Le champ 'prenom' doit contenir uniquement des lettres.");
            return false;
        }
        if (!validerFormatEmail(emailp.getText())) {
            afficherAlerte("Veuillez saisir une adresse e-mail valide.");
            return false;
        }

        return true;
    }

    private void afficherAlerte(String message) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur de saisie");
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
    }

    private boolean validerFormatEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    @FXML
    void liste(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/affichp.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generateQRCode(String nom, String prenom, String coursSelectionne) {
        try {
            String description = "le participant nom:" + nom + " prenom:" + prenom + " vient de participer au cours" + coursSelectionne + " cliquer sur valider et vous recevez un sms de confirmation";

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(description, BarcodeFormat.QR_CODE, 200, 200);

            Image qrCodeImage = matrixToImage(bitMatrix);

            showQRCodeImage(qrCodeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showQRCodeImage(Image qrCodeImage) {
        Stage qrCodeStage = new Stage();
        ImageView imageView = new ImageView(qrCodeImage);
        StackPane root = new StackPane(imageView);
        Button validerButton = new Button("Valider");
        validerButton.setOnAction(event -> envoyerSMSConfirmation());
        VBox vBox = new VBox(root, validerButton); // Utilisez un VBox pour organiser les éléments verticalement
        vBox.setSpacing(10); // Espace entre les éléments
        Scene scene = new Scene(vBox, 200, 235);
        qrCodeStage.setScene(scene);
        qrCodeStage.setTitle("QR Code");
        qrCodeStage.show();
    }

    private Image matrixToImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        WritableImage writableImage = new WritableImage(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writableImage.getPixelWriter().setArgb(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return writableImage;
    }
    private void envoyerSMSConfirmation() {
        String nom = nomp.getText();
        String prenom = prenomp.getText();
        int numeroTelephone = Integer.parseInt(numT.getText());

        String message = "Bonjour " + prenom + " " + nom + "! Merci de confirmer  votre participation.";

        // Initialisez Twilio avec vos informations d'identification
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Envoyez le SMS de confirmation
        Message twilioMessage = Message.creator(
                        new PhoneNumber("+216" + numeroTelephone),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();

        // Affichez le SID du message Twilio dans la console
      //  System.out.println("SID du message Twilio : " + twilioMessage.getSid());
    }
}
