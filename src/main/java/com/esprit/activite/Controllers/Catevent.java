package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.type_ev;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.services.TypecService;
import com.esprit.activite.services.type_evService;
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
import javafx.stage.Stage;

public class Catevent {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField catev;

    @FXML
    private TableColumn<?, ?> catevent;

    @FXML
    private TableColumn<?, ?> id_event;

    @FXML
    private TextField iev;

    @FXML
    private TableView<type_ev> viewevent;
    private int idcatsselected;

    @FXML
    void retourner(ActionEvent event) {


        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        type_evService es = new type_evService();

        es.ajouter(new type_ev(catev.getText()));

        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("event ajout");
        alerte.setContentText("event bien ajoutee");
        alerte.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/catevent.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) viewevent.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void delete(ActionEvent event) {
        type_evService c=new type_evService();


        int selectedID = viewevent.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            type_ev catASupprimer =  viewevent.getItems().get(selectedID);


            c.supprimer(catASupprimer);

            // Mettez à jour la TableView
            viewevent.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("categorie nest pas selectioner");
            alerte.show();
        }

    }

    @FXML
    void update(ActionEvent event) {
        type_evService es = new type_evService();
        type_ev catselected = es.recherchecatev(idcatsselected);
        typec c = new typec();
        int idCat = Integer.parseInt(iev.getText());
        String cat = catev.getText();

        es.modifier(new type_ev(idCat,cat));
        //
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/catevent.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }

    }

    @FXML
    void initialize() {
        type_evService c = new type_evService();
        List<type_ev> cat = c.afficher();
        ObservableList<type_ev> observableList = FXCollections.observableList(cat);
        viewevent.setItems(observableList);
        id_event.setCellValueFactory(new PropertyValueFactory<>("id_typeev"));
        catevent.setCellValueFactory(new PropertyValueFactory<>("type_ev"));
        //System.out.println(c.afficher());
        viewevent.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idcatsselected = newSelection.getId_typeev();
                //
                iev.setText(String.valueOf(newSelection.getId_typeev()));
                catev.setText(newSelection.getType_ev());
            } else {
                idcatsselected = -1;
            }
        });
    }

}
