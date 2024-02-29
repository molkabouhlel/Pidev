package com.esprit.controllers;
import javafx.event.ActionEvent;
import com.esprit.models.Categorie;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import com.esprit.services.CategorieService;
import com.esprit.services.ProduitService;
import com.esprit.models.Produit;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MenuController {


        @FXML
        void add(ActionEvent event) throws IOException {
                Node source = (Node) event.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.close(); // Close the current stage
                // Load and show the new interface
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AjoutProduit.fxml"));
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle exception, if any
                }
            }
        @FXML
        void show(ActionEvent event) throws IOException {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage
            // Load and show the new interface
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AffichageProduit.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, if any

            }
        }

        @FXML
        void upd(ActionEvent event) throws IOException {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage
            // Load and show the new interface
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/ModifierProduit.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, if any
            }
        }
    @FXML
    void retret(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MenuGen.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }

    }
    }


