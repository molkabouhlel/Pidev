package com.esprit.controllers;

import com.esprit.models.Espace;
import com.esprit.services.EspaceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;

public class ModifierEspaceController  {

    @FXML
    private Button ModifierEspace;

    @FXML
    private TextArea description;

    @FXML
    private AnchorPane formaddespace;

    @FXML
    private TextField heure_debut;

    @FXML
    private TextField heure_fin;

    @FXML
    private TextField id_espace;

    @FXML
    private TextField nom_espace;

    private Espace EspaceToModifier;


    public void initData(Espace E) {
        EspaceToModifier = E;
        id_espace.setText(String.valueOf(E.getId_espace()));
        nom_espace.setText(E.getNom_espace());
        description.setText(E.getDescription_espace());
        heure_debut.setText(String.valueOf(E.getHeure_debut()));
        heure_fin.setText(String.valueOf(E.getHeure_debut()));
    }

    @FXML
    void ModifierEspace(ActionEvent event) throws IOException {
        EspaceService es = new EspaceService();
        //Espace espace = difficulteComboBox.getValue();
        //int numberOfQuestions = Integer.parseInt(nbr_questionstf.getText());
        EspaceToModifier.setId_espace(Integer.parseInt(id_espace.getText()));
        EspaceToModifier.setNom_espace(nom_espace.getText());
        EspaceToModifier.setDescription_espace(description.getText());
            EspaceToModifier.setHeure_debut(Time.valueOf(heure_debut.getText()));
            EspaceToModifier.setHeure_fin(Time.valueOf(heure_fin.getText()));
            es.modifier(EspaceToModifier);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modifier Espace");
            alert.setContentText("Modification avec succces");
            alert.showAndWait();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEspace.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    @FXML
    void RedirectToEspaceAfficher(ActionEvent event) throws IOException{

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEspace.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}






