package com.esprit.controllers;

import com.esprit.models.Club;
import com.esprit.models.ListCours;
import com.esprit.services.ClubService;
import com.esprit.services.ListCoursService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListeCoursFrontController {
    @FXML
    private FlowPane flowPane;




    public void initialize(Club C) {

        initFlowpane(C);
    }

    public void initFlowpane(Club C){
       /* ListCoursService lcs=new ListCoursService();

        List<ListCours> ListCours = lcs.RecupererListeCours(C.getId_club());
        flowPane.setHgap(30);
        flowPane.setVgap(30);


        // Parcours la liste d'images et crée des VBox pour chaque image
        for (ListCours lc : ListCours) {
            System.out.println(lc.getId_cours());

            VBox Vbox = createVBox(lc);
            Vbox.setUserData(lc); // Set the Club object as user data for the VBox

            // Handle the Details button click
            javafx.scene.control.Button Details = (javafx.scene.control.Button) Vbox.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);
            if (Details != null) {
                Details.setOnAction(event -> {
                   Id_Club_Selected = club.getId_club();
                    showDetails();
                });
            }
            flowPane.getChildren().add(Vbox);
        }*/
    }


   /* private VBox createVBox(ListCours lc) {
        VBox Vbox = new VBox();
        Vbox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");



        // Crée l'ImageView pour l'image
        String imageUrl = lc.getImage_club();
        ImageView imageView = createImageView(imageUrl);
        imageView.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(120);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(120);

        Label nom_club = new Label( C.getNom_club());
        nom_club.setAlignment(Pos.CENTER);
        //StackPane labelPane = new StackPane();
        //labelPane.getChildren().add(nom_club);


        Label label2 = new Label("Description: "+C.getDescription_club());

        // Crée le bouton "Détails" pour chaque image
        Button Details = new Button("Details");
        Details.setPrefWidth(150);
        Details.setStyle("-fx-content-display: LEFT;");


        Label label3 = new Label(String.valueOf(C.getTemp_ouverture()));

        // Ajoute l'ImageView et le bouton "Détails" à la VBox
        Vbox.getChildren().addAll(nom_club,separator1,imageView,separator2,label2,label3,Details);

        return Vbox;
    }
*/
  /*  private ImageView createImageView(String imageUrl) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(130);

        try {
            File file = new File(imageUrl);
            javafx.scene.image.Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return imageView;
    }*/


    @FXML
    void RedirectToClubDetail(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailClub.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) flowPane.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }
}
