package com.esprit.controllers;


import com.esprit.models.Programme;
import com.esprit.services.ProgrammeServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AffichageProg1Controller {


    @FXML
    private FlowPane flowPane;
    private int Id_Prog_Selected;

    public void initialize() {

        ProgrammeServices cs = new ProgrammeServices();
        List<Programme> Proglist = cs.afficher();
        //System.out.println(Proglist);
        flowPane.setHgap(30);
        flowPane.setVgap(30);

        // Parcours la liste d'images et crée des VBox pour chaque image
        for (Programme prog : Proglist) {
            VBox Vbox = createImageWithButtonVBox(prog);
            Vbox.setUserData(prog); // Set the Club object as user data for the VBox

            // Handle the Details button click
            Button Details = (Button) Vbox.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);
            if (Details != null) {
                Details.setOnAction(event -> {
                    Id_Prog_Selected = prog.getID_prog();
                    showDetails();
                });
            }
            flowPane.getChildren().add(Vbox);
        }

    }


    private VBox createImageWithButtonVBox(Programme C) {
        VBox Vbox = new VBox();
        Vbox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");
        Label label2 = new Label("Description: "+C.getDesc_prog());

        // Crée le bouton "Détails" pour chaque image
        Button Details = new Button("Détails");
        Details.setPrefWidth(150);
        Details.setStyle("-fx-content-display: LEFT;");



        // Ajoute l'ImageView et le bouton "Détails" à la VBox
        Vbox.getChildren().addAll(label2,Details);

        return Vbox;
    }

    private void showDetails() {
        System.out.println("Showing details for club ID: " + Id_Prog_Selected);
        if (Id_Prog_Selected != -1) {
            ProgrammeServices cs = new ProgrammeServices();
            Programme ProgSelected = cs.rechercheProgramme(Id_Prog_Selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Objectif.fxml"));
            try {
                Parent root = loader.load();
                ObjectifController LCC = loader.getController();
                LCC.init(ProgSelected);
                LCC.initialize(ProgSelected);
                Stage currentStage = (Stage) flowPane.getScene().getWindow();
                currentStage.setScene(new Scene(root));
                System.out.println("Navigating to objectif.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
