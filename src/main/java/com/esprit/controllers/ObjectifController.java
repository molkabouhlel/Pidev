package com.esprit.controllers;

import com.esprit.models.Objectif;
import com.esprit.models.Programme;
import com.esprit.services.ObjectifServices;
import com.esprit.services.ProgrammeServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ObjectifController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btclear;

    @FXML
    private Button btdelete;

    @FXML
    private Button btsave;

    @FXML
    private Button btupdate;

    @FXML
    private TableColumn<Objectif, String> coldescr;

    @FXML
    private TableColumn<Objectif, Integer> colidcours;

    @FXML
    private TableColumn<Objectif, Integer> colidobj;

    @FXML
    private TableColumn<Objectif, Integer> colidprog;


    @FXML
    private TableView<Objectif> tableobj;

    @FXML
    private TextArea tdesc_obj;

    @FXML
    private TextField tid_cours;


    private ObjectifServices objectifServices;
    @FXML
    private ComboBox<Programme> Programme ;
    private Objectif objectifToModifier;
    private int  List_Selected;
    public void init(Objectif LS) {
        objectifToModifier = LS;

        tid_cours.setText(String.valueOf(LS.getID_cours()));
        Programme.setValue(LS.getProgramme());
        tdesc_obj.setText(LS.getDescription_obj());

    }

    @FXML
    void AjouterObjectif(ActionEvent event) {
        int ID_cours = Integer.parseInt(tid_cours.getText());
        Programme selectedProgramme = Programme.getValue();
        String description = tdesc_obj.getText();

        Objectif newObjectif = new Objectif();
        newObjectif.setID_cours(ID_cours);
        newObjectif.setProgramme(selectedProgramme);
        newObjectif.setDescription_obj(description);

        objectifServices.ajouter(newObjectif);
        afficherObjectifs();
    }

    @FXML
    void ClearObjectif(ActionEvent event) {
        tid_cours.clear();
        Programme.setValue(null);
        tdesc_obj.clear();
    }

    @FXML
    void ModifierObjectif(ActionEvent event) {
        Objectif selectedObjectif = tableobj.getSelectionModel().getSelectedItem();
        if (selectedObjectif != null) {
            int ID_cours = Integer.parseInt(tid_cours.getText());
            Programme selectedProgramme = Programme.getValue();
            String description = tdesc_obj.getText();

            selectedObjectif.setID_cours(ID_cours);
            selectedObjectif.setProgramme(selectedProgramme);
            selectedObjectif.setDescription_obj(description);

            objectifServices.modifier(selectedObjectif);
            afficherObjectifs();
        } else {
            System.out.println("Veuillez sélectionner un objectif à modifier.");
        }
    }

    @FXML
    void SupprimerObjectif(ActionEvent event) {
        Objectif selectedObjectif = tableobj.getSelectionModel().getSelectedItem();
        if (selectedObjectif != null) {
            objectifServices.supprimer(selectedObjectif);
            afficherObjectifs();
        } else {
            System.out.println("Veuillez sélectionner un objectif à supprimer.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        objectifServices = new ObjectifServices();
        initialiserTableauObjectifs();
        afficherObjectifs();
        ProgrammeServices programmeServices = new ProgrammeServices();
        List<Programme> programmes = programmeServices.afficher();
        System.out.println(programmes);
        Programme.setItems(FXCollections.observableArrayList(programmes));

        tableobj.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List_Selected = newSelection.getID_obj();
                init(newSelection);

            } else {
                List_Selected = -1;
            }
        });
    }




    private void initialiserTableauObjectifs() {
        colidobj.setCellValueFactory(new PropertyValueFactory<>("ID_obj"));
        colidcours.setCellValueFactory(new PropertyValueFactory<>("ID_cours"));
        colidprog.setCellValueFactory(new PropertyValueFactory<>("programme"));
        coldescr.setCellValueFactory(new PropertyValueFactory<>("description_obj"));
    }

    private void afficherObjectifs() {
        List<Objectif> objectifs = objectifServices.afficher();
        ObservableList<Objectif> objectifsObservableList = FXCollections.observableArrayList(objectifs);
        tableobj.setItems(objectifsObservableList);
    }
}
