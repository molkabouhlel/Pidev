package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.*;
import com.esprit.activite.services.CategorieService;
import com.esprit.activite.services.EquipementService;
import com.esprit.activite.services.MaintenanceService;
import com.esprit.activite.services.Rendez_vousService;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.*;

public class AjoutRdvController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private TableColumn<Rendez_vous, Void> action;

    @FXML
    private URL location;

    @FXML
    private Button ajouterrv;


   // private TextField date_rv;
   @FXML
   private DatePicker datePicker;

    @FXML
    private HBox date_rv;
    @FXML
    private Spinner<Integer> heureSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;



    @FXML
    private TableColumn<?, ?> date_rvafficher;

    @FXML
    private ChoiceBox<Integer> id_coach;

    @FXML
    private TableColumn<?, ?> id_coachafficher;

    @FXML
    private TextField id_rv;

    @FXML
    private TableColumn<?, ?> id_rvafficher;

    @FXML
    private Button modifierrv;
////
    @FXML
    private ChoiceBox<String> id_eq;


    @FXML
    private TableColumn<?, ?> id_eqafficher;

    @FXML
    private Button supprimerrv;

    @FXML
    private TableView<Rendez_vous> tableview;
    private int idc;

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Rendez_vousService rs = new Rendez_vousService();
        EquipementService es = new EquipementService();

        LocalDate selectedDate = datePicker.getValue();

        int selectedHour = heureSpinner.getValue();
        int selectedMinute = minuteSpinner.getValue();

        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);

        LocalDateTime localDateTime = LocalDateTime.of(selectedDate, selectedTime);

        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        ///
        String cat=id_eq.getValue();
        Equipement eq=rs.rechercherEqParNom(cat);
///
        rs.ajouter(new Rendez_vous(timestamp,eq, id_coach.getValue()));
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Rv ajout");
        alerte.setContentText("Rv bien ajoutee");
        alerte.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutRdv.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) date_rv.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }
    @FXML
    void modifier(ActionEvent event) {
        Rendez_vousService rendezVousService = new Rendez_vousService();
        Rendez_vous rendezVous = new Rendez_vous();

        try {
            // Extraction de l'ID
            int idRv = Integer.parseInt(id_rv.getText());

            LocalDate selectedDate = datePicker.getValue();

            int selectedHour = heureSpinner.getValue();
            int selectedMinute = minuteSpinner.getValue();

            LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);

            LocalDateTime localDateTime = LocalDateTime.of(selectedDate, selectedTime);

            Timestamp timestamp = Timestamp.valueOf(localDateTime);
///
            String cat=id_eq.getValue();
            Equipement eq=rendezVousService.rechercherEqParNom(cat);

            rendezVous.setId_rv(idRv);
            rendezVous.setDate_rv(timestamp);
            ///
            rendezVous.setId_eq(eq);
            rendezVous.setId_coach(id_coach.getValue());

            rendezVousService.modifier(rendezVous);

            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Rendez-vous modifié");
            alerte.setContentText("Rendez-vous bien modifié");
            alerte.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutRdv.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (NumberFormatException e) {
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Veuillez saisir un ID valide.");
            alerteErreur.show();
        } catch (Exception e) {
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Une erreur s'est produite lors de la modification du rendez-vous.");
            alerteErreur.show();
        }
    }


    @FXML
    void initialize() {
        Rendez_vousService c = new Rendez_vousService();
        List<Rendez_vous> catr = c.afficher();
        ObservableList<Rendez_vous> observableList = FXCollections.observableList(catr);
        tableview.setItems(observableList);
        id_rvafficher.setCellValueFactory(new PropertyValueFactory<>("id_rv"));
        id_eqafficher.setCellValueFactory(new PropertyValueFactory<>("id_eq"));
        date_rvafficher.setCellValueFactory(new PropertyValueFactory<>("date_rv"));
        id_coachafficher.setCellValueFactory(new PropertyValueFactory<>("id_coach"));
        heureSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idc = newSelection.getId_rv();
                //
                id_rv.setText(String.valueOf(newSelection.getId_rv()));

                Timestamp timestamp = newSelection.getDate_rv();
                LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
                datePicker.setValue(localDate);
                heureSpinner.getValueFactory().setValue(timestamp.toLocalDateTime().toLocalTime().getHour());
                minuteSpinner.getValueFactory().setValue(timestamp.toLocalDateTime().toLocalTime().getMinute());
            } else {
                idc = -1;
            }
        });

///

       List<String> nomeqList = c.listeq();
        id_eq.setItems(FXCollections.observableArrayList(nomeqList));



        Rendez_vousService rvs = new Rendez_vousService();
        EquipementService eqs = new EquipementService();

        List<Integer> C = eqs.RecupereridCoach();
        id_coach.setItems(FXCollections.observableArrayList(C));

       /* List<Equipement> eq = eqs.RecupererEquipement();
        id_eq.setItems(FXCollections.observableArrayList(eq));*/
        boutonsupp();
    }



    @FXML
    void supprimer(ActionEvent event) {
        Rendez_vousService c=new Rendez_vousService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Rendez_vous catASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(catASupprimer);

            tableview.getItems().remove(selectedID);
        } else {
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("rdv nest pas selectioner");
            alerte.show();
        }

    }
    @FXML
    void refresh(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO **************BOUTON SUPPRIMER cree tableview
    private void boutonsupp() {
        action.setCellFactory(col -> new TableCell<Rendez_vous, Void>() {
            private final Button participerButton = new Button("supprimer");

            {
                participerButton.setOnAction(event -> {
                    //   tableview.edit(-1, null);
                    Rendez_vous rendezVous = getTableView().getItems().get(getIndex());
                    supprimerE(rendezVous);
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
    private void supprimerE(Rendez_vous ev) {
        Rendez_vousService p = new Rendez_vousService();
        p.supprimer(ev);
        tableview.getItems().remove(ev);
    }

}



