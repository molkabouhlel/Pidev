package com.esprit.activite.modeles;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class ParticipationConfirmationApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Confirmation de participation");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label nameLabel = new Label("Nom:");
        GridPane.setConstraints(nameLabel, 0, 0);
        Label nameValueLabel = new Label("John Doe"); // Remplacez par le nom de votre client
        GridPane.setConstraints(nameValueLabel, 1, 0);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 1);
        Label emailValueLabel = new Label("yasmine1jamoussi@gmail.com"); // Remplacez par l'e-mail de votre client
        GridPane.setConstraints(emailValueLabel, 1, 1);

        Button sendButton = new Button("Envoyer la confirmation");
        GridPane.setConstraints(sendButton, 1, 2);
        sendButton.setOnAction(event -> {
            String to = emailValueLabel.getText();
            String subject = "Confirmation de réservation";
            String body = "Bonjour " + nameValueLabel.getText() + ",\n\nVotre réservation a été confirmée. Merci de votre confiance.\n\nCordialement,\nVotre service de réservation";

            sendEmail(to, subject, body);
        });

        grid.getChildren().addAll(nameLabel, nameValueLabel, emailLabel, emailValueLabel, sendButton);

        Scene scene = new Scene(grid, 400, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void sendEmail(String to, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("yasmine1jamoussi@gmail.com", "zdqiwourgrlfvoum");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("yasmine1jamoussi@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
