package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.etat_m;
import com.esprit.activite.modeles.Maintenance_eq;
import com.esprit.activite.services.MaintenanceService;

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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AjoutMaintenanceController implements Initializable {

    @FXML
    private Button ajouterm;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Maintenance_eq> tableview;

    @FXML
    private TableColumn<Maintenance_eq, Void> action;

    @FXML
    private HBox date_m;

    @FXML
    private TableColumn<Maintenance_eq, String> date_ma;

    @FXML
    private ChoiceBox<etat_m> choicebox;

    @FXML
    private Spinner<Integer> heureSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;

    @FXML
    private TableColumn<?, ?> etat_ma;

    @FXML
    private TextField id_m;

    @FXML
    private TableColumn<?, ?> id_ma;

    @FXML
    private Button modifierm;

    @FXML
    private Button supprimerm;

    public int id_masselected;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<etat_m> choices = FXCollections.observableArrayList(etat_m.values());
        choicebox.setItems(choices);

        MaintenanceService c = new MaintenanceService();
        List<Maintenance_eq> cat = c.afficher();
        ObservableList<Maintenance_eq> observableList = FXCollections.observableList(cat);
        tableview.setItems(observableList);

        id_ma.setCellValueFactory(new PropertyValueFactory<>("id_m"));
        date_ma.setCellValueFactory(new PropertyValueFactory<>("date_m"));
        etat_ma.setCellValueFactory(new PropertyValueFactory<>("etat_m"));

        DatePicker datePicker = (DatePicker) date_m.getChildren().get(0);
        Spinner<Integer> heureSpinner = (Spinner<Integer>) date_m.getChildren().get(1);
        Spinner<Integer> minuteSpinner = (Spinner<Integer>) date_m.getChildren().get(2);

        SpinnerValueFactory<Integer> heureFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        heureSpinner.setValueFactory(heureFactory);

        SpinnerValueFactory<Integer> minuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        minuteSpinner.setValueFactory(minuteFactory);

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                id_masselected = newSelection.getId_m();
                id_m.setText(String.valueOf(newSelection.getId_m()));

                LocalDateTime localDateTime = newSelection.getDate_m().toLocalDateTime();
                datePicker.setValue(localDateTime.toLocalDate());
                heureSpinner.getValueFactory().setValue(localDateTime.getHour());
                minuteSpinner.getValueFactory().setValue(localDateTime.getMinute());
            } else {
                id_masselected = -1;
            }
        });

        boutonsupp();
    }

    private void boutonsupp() {
        action.setCellFactory(col -> new TableCell<Maintenance_eq, Void>() {
            private final Button participerButton = new Button("supprimer");

            {
                participerButton.setOnAction(event -> {
                    Maintenance_eq maintenance_eq = getTableView().getItems().get(getIndex());
                    supprimerE(maintenance_eq);
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

    private void supprimerE(Maintenance_eq ev) {
        MaintenanceService p = new MaintenanceService();
        p.supprimer(ev);

        tableview.getItems().remove(ev);
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        MaintenanceService es = new MaintenanceService();
        etat_m selectedEtat = choicebox.getValue();

        if (selectedEtat != null) {
            DatePicker datePicker = (DatePicker) date_m.getChildren().get(0);
            Spinner<Integer> heureSpinner = (Spinner<Integer>) date_m.getChildren().get(1);
            Spinner<Integer> minuteSpinner = (Spinner<Integer>) date_m.getChildren().get(2);

            LocalDateTime dateTime = LocalDateTime.of(datePicker.getValue(), LocalTime.of(heureSpinner.getValue(), minuteSpinner.getValue()));
            Timestamp timestamp = Timestamp.valueOf(dateTime);

            //if (!es.idMaintenanceExiste(Integer.parseInt(id_m.getText()))) {
                es.ajouter(new Maintenance_eq(timestamp, selectedEtat));
                // Affichage de l'alerte pour la première valeur ajoutée
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Maintenance ajoutée");
                alerte.setContentText("Maintenance bien ajoutée");
                alerte.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutMaintenance.fxml"));
                Parent root = loader.load();

                Stage currentStage = (Stage) tableview.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } else {
                Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
                alerteErreur.setTitle("Erreur");
                alerteErreur.setContentText("L'ID de maintenance existe déjà.");
                alerteErreur.show();
            }}
       /* } else {
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Veuillez sélectionner une valeur dans la ChoiceBox.");
            alerteErreur.show();
        } */


    @FXML
    void modifier(ActionEvent event) {
        MaintenanceService es = new MaintenanceService();

        DatePicker datePicker = (DatePicker) date_m.getChildren().get(0);
        Spinner<Integer> heureSpinner = (Spinner<Integer>) date_m.getChildren().get(1);
        Spinner<Integer> minuteSpinner = (Spinner<Integer>) date_m.getChildren().get(2);

        LocalDateTime dateTime = LocalDateTime.of(datePicker.getValue(), LocalTime.of(heureSpinner.getValue(), minuteSpinner.getValue()));
        Timestamp timestamp = Timestamp.valueOf(dateTime);

        es.modifier(new Maintenance_eq(id_masselected, timestamp, choicebox.getValue()));
    }

    @FXML
    void supprimer(ActionEvent event) {
        MaintenanceService c = new MaintenanceService();

        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Maintenance_eq catASupprimer = tableview.getItems().get(selectedID);

            c.supprimer(catASupprimer);

            tableview.getItems().remove(selectedID);
        } else {
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("maintenance nest pas selectionnee");
            alerte.show();
        }
    }

    @FXML
    void refresh(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
