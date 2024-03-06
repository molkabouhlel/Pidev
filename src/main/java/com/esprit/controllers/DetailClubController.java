package com.esprit.controllers;

import com.esprit.models.Club;

import com.esprit.models.Cours;
import com.esprit.models.Espace;
import com.esprit.models.ListCours;
import com.esprit.services.ClubService;

import com.esprit.services.EspaceService;
import com.esprit.services.ListCoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DetailClubController {


    @FXML
    private ImageView imageClub;

    @FXML
    private Label adresse_club;


    @FXML
    private Label description_club;

    @FXML
    private Label espace_club;

    @FXML
    private Label heure_ouverture;

    @FXML
    private Label nom_club;


///////////////////////////////////////////////////////////////////////////
@FXML
private TextField user_mail;

    @FXML
    private TextField user_nom;

    @FXML
    private TextField user_prenom;

    private String captcha;

    @FXML
    private Canvas captchaCanvas;
    @FXML
    private TextField captchaTextfield;


    private int Id_Club_Selected;
    private ListCours ListCoursToModifier;

    private Club ClubToModifier;



    public void initClub(Club C) {


        ClubToModifier = C;
        nom_club.setStyle("-fx-font-family: 'Montserrat';");
        adresse_club.setStyle("-fx-font-family: 'Montserrat';");
        description_club.setStyle("-fx-font-family: 'Montserrat';");
        heure_ouverture.setStyle("-fx-font-family: 'Montserrat';");
        espace_club.setStyle("-fx-font-family: 'Montserrat';");


        nom_club.setText("Name:              "+C.getNom_club());
        adresse_club.setText("address:            "+C.getAdresse_club());
        description_club.setText("description:      "+C.getDescription_club());
        heure_ouverture.setText("Time-Open:     "+String.valueOf(C.getTemp_ouverture()));
        espace_club.setText("Space:               "+C.getEspace().getNom_espace());

        String imageUrl = C.getImage_club();
        System.out.println(imageUrl);

        Image image = new Image(new File(imageUrl).toURI().toString());
        imageClub.setImage(image);
        imageClub.setStyle("-fx-border-color: black; -fx-border-width: 5px;");

        Id_Club_Selected=C.getId_club();
    }


    public void initialize(Club C) {
        EspaceService es = new EspaceService();
        ///////////////////////////CONTROLE SAISIE//////////////////////////
        //controle saisie text
       /* nom_club.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\d.*")) {
                nom_club.setText(oldValue);
            }
        });*/
        generateCaptcha();
        drawCaptcha();
    }

    @FXML
    void refresh(ActionEvent event) {
        generateCaptcha();
        drawCaptcha();
    }



    private void generateCaptcha() {
        captcha = generateRandomString(6); // Change 6 to the desired length
    }

    private void drawCaptcha() {
        GraphicsContext gc = captchaCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, captchaCanvas.getWidth(), captchaCanvas.getHeight());
        gc.setFont(Font.font("Arial", 20));
        gc.setStroke(javafx.scene.paint.Color.BLACK);
        gc.setLineWidth(2.0);

        Random random = new Random();
        for (int i = 0; i < captcha.length(); i++) {
            gc.save();
            gc.translate(30 * i + 10, 30);
            gc.rotate(random.nextInt(20) - 10);
            gc.strokeText(String.valueOf(captcha.charAt(i)), 0, 0);
            gc.restore();
        }
        gc.beginPath();
        gc.moveTo(0, 25);
        for (int i = 0; i < captcha.length() * 30; i += 5)
            gc.lineTo(i, 25 + (random.nextInt(6) - 3));
        gc.stroke();
    }

    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }


    @FXML
    private void check() {
        String userInput = captchaTextfield.getText();
        if (userInput.equals(captcha)) {
            System.out.println("CAPTCHA matched!");

        } else {
            System.out.println("CAPTCHA did not match!");
            // You can add further actions here, like displaying an error message
        }
    }


    @FXML
    void RedicrectionListeCours(ActionEvent event) throws IOException {
        Id_Club_Selected=ClubToModifier.getId_club();
        System.out.println("Navigating to List Cour Club ID"+Id_Club_Selected);
        if (Id_Club_Selected != -1) {
            ListCoursService ls = new ListCoursService();
            ClubService cs =new ClubService();
            Club C=cs.rechercheClub(Id_Club_Selected);
            List<Cours> Lcours =ls.RecupererCours(Id_Club_Selected);
            System.out.println(Lcours);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCoursFront.fxml"));
            try {
                Parent root = loader.load();
                ListeCoursFrontController LCFC = loader.getController();
                //LCFC.initclub(clubSelected);
                LCFC.initialize(Lcours,C);
                Stage currentStage = (Stage) imageClub.getScene().getWindow();
                currentStage.setScene(new Scene(root));
                System.out.println("Navigating to ListeCours.fxml");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    @FXML
    void RedirectToAffichageClub(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichageClubFront.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) imageClub.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }



}


