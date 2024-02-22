package com.esprit.activite.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.Categorie_eq;
import com.esprit.activite.services.CategorieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class AjoutCategorieController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ajouterceq;

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

        CategorieService es = new CategorieService();
        es.ajouter(new Categorie_eq(type_ceq.getText()));
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("categorie ajout");
        alerte.setContentText("categorie bien ajoutee");
        alerte.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCategorie.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) tableview.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void modifier(ActionEvent event) {
        CategorieService es = new CategorieService();
        Categorie_eq catselected = es.recherchecat_eq(idcatsselected);
        Categorie_eq c = new Categorie_eq();
        int idCat = Integer.parseInt(id_ceq.getText());
        String cat = type_ceq.getText();

        es.modifier(new Categorie_eq(idCat,cat));

    }

    @FXML
    void supprimer(ActionEvent event) {
        CategorieService c=new CategorieService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Categorie_eq catASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(catASupprimer);

            // Mettez à jour la TableView
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
        CategorieService c = new CategorieService();
        List<Categorie_eq> cat = c.afficher();
        ObservableList<Categorie_eq> observableList = FXCollections.observableList(cat);
        tableview.setItems(observableList);
        id_cat.setCellValueFactory(new PropertyValueFactory<>("id_ceq"));
        type_cat.setCellValueFactory(new PropertyValueFactory<>("type_ceq"));

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idcatsselected = newSelection.getId_ceq();
                //
                id_ceq.setText(String.valueOf(newSelection.getId_ceq()));
                type_ceq.setText(newSelection.getType_ceq());
            } else {
                idcatsselected = -1;
            }
        });
    }

}
