package com.esprit.controllers;

import com.esprit.models.Participer;
import com.esprit.services.participerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class Affichp {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Participer, Void> action;

    @FXML
    private TextField nom;

    @FXML
    private TableColumn<Participer, String> nomc;

    @FXML
    private TextField prenom;

    @FXML
    private TableView<Participer> tableview;

    @FXML
    void recherche(ActionEvent event) {
        participerService p = new participerService();
        List<Participer> participants = p.rechercheparticipant(nom.getText(), prenom.getText());
        ObservableList<Participer> observableList = FXCollections.observableList(participants);
        tableview.setItems(observableList);
        nomc.setCellValueFactory(new PropertyValueFactory<>("nomCours"));

    }




    @FXML
    void initialize() {
        setupActionColumn();
      //  affichage();
        //
        tableview.setEditable(true);


        nomc.setCellFactory(TextFieldTableCell.forTableColumn());

        participerService ss = new participerService();



        // Save changes on commit
        nomc.setOnEditCommit(event -> {
            Participer s = event.getRowValue();
            s.setNomCours(event.getNewValue());
            ss.modifier(s);
        });

    }

    private void setupActionColumn() {
        action.setCellFactory(col -> new TableCell<Participer, Void>() {
            private final Button participerButton = new Button("supprimer");

            {
                participerButton.setOnAction(event -> {
                    tableview.edit(-1, null);
                    Participer participant = getTableView().getItems().get(getIndex());
                    supprimer(participant);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(participerButton);
                }
            }
        });
    }

    private void supprimer(Participer participant) {
        participerService p = new participerService();
        p.supprimer(participant);

        tableview.getItems().remove(participant);
    }

    @FXML
    void goback(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    }




