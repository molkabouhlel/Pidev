package com.esprit.controllers;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Frontactiviteruser {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void COURSE(ActionEvent event) {
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
    void EVENT(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/imageeve.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void initialize() {

    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceMenuCoach.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
