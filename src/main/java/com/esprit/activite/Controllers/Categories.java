package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.typec;
import com.esprit.activite.services.TypecService;
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

public class Categories {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idcatec;

    @FXML
    private TableColumn<?, ?> idtypec;

    @FXML
    private TableView<typec> tableview;

    @FXML
    private TableColumn<?, ?> typecours;

    @FXML
    private TextField typectext;
    private int idcatsselected;

    public void initData(typec c) {
        idcatec.setText(String.valueOf(c.getIdtypec()));
        typectext.setText(c.getTypecours());

    }
    @FXML
    void ajoutercatcours(ActionEvent event) throws IOException {
        TypecService es = new TypecService();
        es.ajouter(new typec(typectext.getText()));
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("cours ajout");
        alerte.setContentText("cours bien ajoutee");
        alerte.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) tableview.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void modifiercatcours(ActionEvent event) {
        TypecService es = new TypecService();
        typec catselected = es.recherchecat(idcatsselected);
        typec c = new typec();
        int idCat = Integer.parseInt(idcatec.getText());
        String cat = typectext.getText();

        es.modifier(new typec(idCat,cat));
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/categories.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }

    }

    @FXML
    void retourner(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/principale.fxml"));
        try {
            typectext.getScene().setRoot(loader.load());

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void supprimercategoriecours(ActionEvent event) throws IOException {
        TypecService c=new TypecService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            typec catASupprimer =  tableview.getItems().get(selectedID);


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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) tableview.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void initialize() {
        TypecService c = new TypecService();
        List<typec> cat = c.afficher();
        ObservableList<typec> observableList = FXCollections.observableList(cat);
        tableview.setItems(observableList);
        idtypec.setCellValueFactory(new PropertyValueFactory<>("idtypec"));
        typecours.setCellValueFactory(new PropertyValueFactory<>("typecours"));

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idcatsselected = newSelection.getIdtypec();
                //
                idcatec.setText(String.valueOf(newSelection.getIdtypec()));
                typectext.setText(newSelection.getTypecours());
            } else {
                idcatsselected = -1;
            }
        });

    }

}