package com.esprit.activite.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import com.esprit.activite.modeles.Categorie_eq;
import com.esprit.activite.modeles.Equipement;
import com.esprit.activite.modeles.Maintenance_eq;
import com.esprit.activite.services.CategorieService;
import com.esprit.activite.services.EquipementService;
import com.esprit.activite.services.MaintenanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjoutEquipementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ajoutereq;

    @FXML
    private TextField description_eq;

    @FXML
    private ChoiceBox<Categorie_eq> id_ceq;

    @FXML
    private ChoiceBox<Integer> id_coach;

    @FXML
    private ChoiceBox<Integer> id_espace;

    @FXML
    private ChoiceBox<Maintenance_eq> id_m;

    @FXML
    private TextField image;

    @FXML
    private TextField nom_eq;

    @FXML
    private TextField quantite_dispo;

    @FXML
    private TextField ref_eq;

    @FXML
    void ajouerEquipement(ActionEvent event) throws IOException {
        EquipementService es = new EquipementService();

        CategorieService cs = new CategorieService();
        MaintenanceService ms = new MaintenanceService();


        es.ajouter(new Equipement(ref_eq.getText(),nom_eq.getText(),description_eq.getText(),Integer.parseInt( quantite_dispo.getText()),id_coach.getValue(),id_espace.getValue(),id_ceq.getValue(),id_m.getValue(),image.getText()) );
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("EQ ajout");
        alerte.setContentText("EQ bien ajoutee");
        alerte.show();



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipement.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) nom_eq.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void initialize() {
        EquipementService eqs=new EquipementService();

        CategorieService es=new CategorieService();
        List<Categorie_eq> C=es.afficher();
        System.out.println(C);
        id_ceq.setItems(FXCollections.observableArrayList(C));

        MaintenanceService ms=new MaintenanceService();
        List<Maintenance_eq> M=ms.afficher();
        System.out.println(M);
        id_m.setItems(FXCollections.observableArrayList(M));

        List<Integer> E=eqs.RecupereridEspace();
        id_espace.setItems(FXCollections.observableArrayList(E));

        List<Integer> Co=eqs.RecupereridCoach();
        id_coach.setItems(FXCollections.observableArrayList(Co));
    }

    private final EquipementService es = new EquipementService();

}