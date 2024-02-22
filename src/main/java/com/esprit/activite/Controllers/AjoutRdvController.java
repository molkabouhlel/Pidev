package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;

public class AjoutRdvController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ajouterrv;

    @FXML
    private TextField date_rv;

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

    @FXML
    private ChoiceBox<Equipement> ref_eq;

    @FXML
    private TableColumn<?, ?> ref_eqafficher;

    @FXML
    private Button supprimerrv;

    @FXML
    private TableView<Rendez_vous> tableview;
    private int idc;

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Rendez_vousService rs = new Rendez_vousService();

        EquipementService es = new EquipementService();


        rs.ajouter(new Rendez_vous(Timestamp.valueOf(date_rv.getText()),ref_eq.getValue(),id_coach.getValue() ) );
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
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
            // Extraction de l'ID (supposant que id_rv est un champ de texte)
            int idRv = Integer.parseInt(id_rv.getText());

            // Extraction de la date (supposant que date_rv est un champ de texte)
            String dateText = date_rv.getText();
            Timestamp timestamp = Timestamp.valueOf(dateText);

            // Supposons que ref_eq et id_coach sont des ComboBox
            Equipement refEqSelected = ref_eq.getValue();
            int idCoachSelected = id_coach.getValue();

            // Configuration des propriétés de l'objet Rendez_vous
            rendezVous.setId_rv(idRv);
            rendezVous.setDate_rv(timestamp);
            rendezVous.setRef_eq(refEqSelected);
            rendezVous.setId_coach(idCoachSelected);

            // Appel à la méthode ajouter du service
            rendezVousService.modifier(rendezVous);

            // Affichage d'une alerte de succès
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Rendez-vous ajouté");
            alerte.setContentText("Rendez-vous bien ajouté");
            alerte.show();

            // Rechargement de la scène actuelle (rafraîchissement de la vue)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutRendezVous.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (NumberFormatException e) {
            // Gestion d'une exception si la conversion de l'ID échoue
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Veuillez saisir un ID valide.");
            alerteErreur.show();
        } catch (Exception e) {
            // Gestion d'autres exceptions possibles
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
        ref_eqafficher.setCellValueFactory(new PropertyValueFactory<>("ref_eq"));
        date_rvafficher.setCellValueFactory(new PropertyValueFactory<>("date_rv"));
        id_coachafficher.setCellValueFactory(new PropertyValueFactory<>("id_coach"));

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               idc  = newSelection.getId_rv();
                //
                id_rv.setText(String.valueOf(newSelection.getId_rv()));
               date_rv.setText(String.valueOf(newSelection.getDate_rv()));
            } else {
                idc = -1;
            }
        });




        Rendez_vousService rvs=new Rendez_vousService();

        EquipementService eqs=new EquipementService();


        List<Integer> C=eqs.RecupereridCoach();
        id_coach.setItems(FXCollections.observableArrayList(C));

        List<Equipement> eq=eqs.RecupererEquipement();
        ref_eq.setItems(FXCollections.observableArrayList(eq));
    }


    @FXML
    void supprimer(ActionEvent event) {
        Rendez_vousService c=new Rendez_vousService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Rendez_vous catASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(catASupprimer);

            // Mettez à jour la TableView
            tableview.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("rdv nest pas selectioner");
            alerte.show();
        }

    }
    }


