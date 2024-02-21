package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.esprit.Models.Admin;
import com.esprit.Models.Coach;
import com.esprit.Models.Membre;
import com.esprit.Models.User;
import com.esprit.Services.UserServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddUsercontroller {

    @FXML
    private TextField mdp;
    @FXML
    private TextField addresse;
    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField adr;
    @FXML
    private TextField abonnement;

    @FXML
    private URL location;

    @FXML
    private TextField TTF;

    @FXML
    private ComboBox<String> UserTF;


    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;
    @FXML
    private Button add;

    @FXML
    void add(ActionEvent event) {

        UserServices us = new UserServices();
        String test = UserTF.getValue();
        if (test == "Admin") {
            us.add(new Admin(addresse.getText(), mdp.getText(), nomTF.getText(), prenomTF.getText(), Integer.parseInt(TTF.getText()), UserTF.getValue()));
            System.out.println("Adding Admin: ");
        }
        if (test == "Membre") {
            us.add(new Membre(addresse.getText(),mdp.getText(),nomTF.getText(),prenomTF.getText(),Integer.parseInt(TTF.getText()),UserTF.getValue(),abonnement.getText()));
            System.out.println("Adding Member: ");
        }
        if (test == "Coach") {
            us.add(new Coach(addresse.getText(),mdp.getText(),nomTF.getText(),prenomTF.getText(),Integer.parseInt(TTF.getText()),UserTF.getValue(),adr.getText()));
            System.out.println("Adding  Coach: ");
        }

            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("user ajout");
            alerte.setContentText("user  bien ajoutee");

        }


    @FXML
    void showuser(ActionEvent event) {

    }


    @FXML
    void initialize() {
        ObservableList<String> choices = FXCollections.observableArrayList("Admin", "Membre","Coach");
        UserTF.setItems(choices);


    }}

