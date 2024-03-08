package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Detaille {
    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventDateLabel;

    @FXML
    private Label eventDescriptionLabel;

    public void setEventDetails(String eventName, String eventDate, String eventDescription) {
        eventNameLabel.setText("Nom de l'événement : " + eventName);
        eventDateLabel.setText("Date de l'événement : " + eventDate);
        eventDescriptionLabel.setText("Description de l'événement : " + eventDescription);
    }
    @FXML
    void retourner(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/imageeve.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
