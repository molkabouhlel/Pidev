package com.esprit.activite.Controllers;

import com.esprit.activite.modeles.Categorie_eq;
import com.esprit.activite.modeles.Maintenance_eq;
import com.esprit.activite.modeles.etat_m;
import com.esprit.activite.services.CategorieService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class AjoutMaintenanceController implements Initializable {

    @FXML
    private Button ajouterm;

    @FXML
    private TextField date_m;

    @FXML
    private TableColumn<Maintenance_eq, String> date_ma;

    @FXML
    private ChoiceBox<etat_m> choicebox;

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
        ///
        MaintenanceService c = new MaintenanceService();
        List<Maintenance_eq> cat = c.afficher();
        ObservableList<Maintenance_eq> observableList = FXCollections.observableList(cat);
        tableview.setItems(observableList);
        id_ma.setCellValueFactory(new PropertyValueFactory<>("id_m"));
        date_ma.setCellValueFactory(new PropertyValueFactory<>("date_m"));
        etat_ma.setCellValueFactory(new PropertyValueFactory<>("etat_m"));

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                id_masselected = newSelection.getId_m();
                //
                id_m.setText(String.valueOf(newSelection.getId_m()));
                date_m.setText(newSelection.getDate_m().toString());
            } else {
                id_masselected = -1;
            }
        });

    }



    @FXML
    private TableView<Maintenance_eq> tableview;




    @FXML
    void ajouter(ActionEvent event)  throws IOException {
        MaintenanceService es = new MaintenanceService();
        etat_m selectedEtat = choicebox.getValue();

        if (selectedEtat != null) {
            es.ajouter(new Maintenance_eq(Timestamp.valueOf(date_m.getText()), selectedEtat));

            // Affichage de l'alerte pour la première valeur ajoutée
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Maintenance ajoutée");
            alerte.setContentText("Maintenance bien ajoutée");
            alerte.show();

            // Charger le FXML correctement (vérifiez le chemin)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutMaintenance.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } else {
            // Gérer le cas où la valeur de choicebox est nulle (non sélectionnée)
            Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
            alerteErreur.setTitle("Erreur");
            alerteErreur.setContentText("Veuillez sélectionner une valeur dans la ChoiceBox.");
            alerteErreur.show();
        }

// Ajouter un autre if pour traiter une deuxième valeur (par exemple, la deuxième option dans l'enum)
        etat_m deuxiemeEtat = choicebox.getValue();
        if (deuxiemeEtat != null && deuxiemeEtat.equals(etat_m.necessite_maintenance)) {
            // Traiter la deuxième valeur sélectionnée, si nécessaire
            // ...
        }
    }

        @FXML
    void modifier(ActionEvent event)  {


            MaintenanceService es = new MaintenanceService();
           /* Maintenance_eq maintenance_eqselected = es.recherchem_eq(id_masselected);
            System.out.println("yo");
            Maintenance_eq m = new Maintenance_eq();
            int id_ma = Integer.parseInt(id_m.getText());
            String cat = date_m.getText();
*/
            es.modifier(new Maintenance_eq(id_masselected,Timestamp.valueOf(date_m.getText()), choicebox.getValue()));

        }




    @FXML
    void supprimer(ActionEvent event) {
        MaintenanceService c=new MaintenanceService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Maintenance_eq catASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(catASupprimer);

            // Mettez à jour la TableView
            tableview.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("ma nest pas selectionner");
            alerte.show();
        }


    }
    @FXML
    void refresh(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any}
    }

}}