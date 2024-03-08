package com.esprit.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.esprit.models.Categorie_eq;
import com.esprit.services.Categorie_eqService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;



public class AjoutCategorie_eqController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private TableColumn<Categorie_eq, Void> action;
    @FXML
    private URL location;
    @FXML
    private TextField desc_ceq;

    @FXML
    private Button ajouterceq;
    @FXML
    private TableColumn<Categorie_eq, String> desc_cat;


    @FXML
    private TableColumn<Categorie_eq, Integer> id_cat;

    @FXML
    private TextField id_ceq;

    @FXML
    private Button modifierceq;

    @FXML
    private Button supp;

    @FXML
    private TableView<Categorie_eq> tableview;

    @FXML
    private TableColumn<Categorie_eq, String> type_cat;

    @FXML
    private TextField type_ceq;
    private int idcatsselected;

    @FXML
    void ajouter(ActionEvent event) throws IOException {

        Categorie_eqService es = new Categorie_eqService();
        if (controlSaisie(type_ceq) && controlSaisie(desc_ceq)) {
            es.ajouter(new Categorie_eq(type_ceq.getText(), desc_ceq.getText()));
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("categorie ajout");
            alerte.setContentText("categorie bien ajoutee");
            alerte.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCategorie_eq.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }

    }

    @FXML
    void modifier(ActionEvent event) {
        Categorie_eqService es = new Categorie_eqService();
        Categorie_eq catselected = es.recherchecat_eq(idcatsselected);
        Categorie_eq c = new Categorie_eq();
        int idCat = Integer.parseInt(id_ceq.getText());
        String cat = type_ceq.getText();
        String dcat = desc_ceq.getText();
        if (controlSaisie(type_ceq) && controlSaisie(desc_ceq)) {

            es.modifier(new Categorie_eq(idCat, cat, dcat));
        }

    }

    @FXML
    void supprimer(ActionEvent event) {
        Categorie_eqService c=new Categorie_eqService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Categorie_eq catASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(catASupprimer);

            tableview.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("categorie nest pas selectioner");
            alerte.show();
        }

    }

    @FXML
    void initialize() {
        Categorie_eqService c = new Categorie_eqService();
        List<Categorie_eq> cat = c.afficher();
        ObservableList<Categorie_eq> observableList = FXCollections.observableList(cat);
        tableview.setItems(observableList);
        id_cat.setCellValueFactory(new PropertyValueFactory<>("id_ceq"));
        type_cat.setCellValueFactory(new PropertyValueFactory<>("type_ceq"));
        desc_cat.setCellValueFactory(new PropertyValueFactory<>("desc_ceq"));
//
        tableview.setEditable(true);
        type_cat.setCellFactory(TextFieldTableCell.forTableColumn());
        desc_cat.setCellFactory(TextFieldTableCell.forTableColumn());
        Categorie_eqService ss = new Categorie_eqService();
        // Save changes on commit
        type_cat.setOnEditCommit(event -> {
            Categorie_eq s = event.getRowValue();
            s.setType_ceq(event.getNewValue());
            ss.modifier(s);
        });

        desc_cat.setOnEditCommit(event -> {
            Categorie_eq s = event.getRowValue();
            s.setDesc_ceq(event.getNewValue());
            ss.modifier(s);
        });
        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idcatsselected = newSelection.getId_ceq();
                //
                id_ceq.setText(String.valueOf(newSelection.getId_ceq()));
                type_ceq.setText(newSelection.getType_ceq());
                desc_ceq.setText(newSelection.getDesc_ceq());

            } else {
                idcatsselected = -1;
            }
        });
        boutonsupp();

    }

    @FXML
    void refresh(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Principale_eq.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    //TODO **************BOUTON SUPPRIMER cree tableview
    private void boutonsupp() {
        action.setCellFactory(col -> new TableCell<Categorie_eq, Void>() {
            private final Button participerButton = new Button("supprimer");

            {
                participerButton.setOnAction(event -> {
                    //   tableview.edit(-1, null);
                    Categorie_eq categorie_eq = getTableView().getItems().get(getIndex());
                    supprimerE(categorie_eq);
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
    private void supprimerE(Categorie_eq ev) {
        Categorie_eqService p = new Categorie_eqService();
        p.supprimer(ev);

        tableview.getItems().remove(ev);
    }
    public boolean controlSaisie (TextField field){
        if (field.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Form not full");
            alert.setContentText("you must fill all the Form");
            alert.show();
            return false;
        }
        return true;
    }

}
