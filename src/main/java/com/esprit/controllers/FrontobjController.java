package com.esprit.controllers;

import com.esprit.models.Objectif;
import com.esprit.models.Programme;
import com.esprit.services.ObjectifServices;
import com.esprit.services.ProgrammeServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FrontobjController {
    @FXML
    private Button ReturntoAffichage;

    @FXML
    private TextField programmeField;
    @FXML
    private TextField programIdTextField;
    @FXML
    private Button clearButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<Objectif, String> colDescr;

    @FXML
    private TableColumn<Objectif, Integer> colIdCours;

    @FXML
    private TableColumn<Objectif, Integer> colIdObj;
    @FXML
    private TableColumn<Objectif, Integer> colId;

    @FXML
    private TableColumn<Objectif, Integer> colIdProg;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextArea programDescriptionTextArea;

    @FXML
    private TextField finalStateTextField;

    @FXML
    private TextField initialStateTextField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField objectifIdTextField;

    @FXML
    private TextField rateTextField;

    @FXML
    private TableView<Objectif> objectiveTableView;

    @FXML
    private TextArea objectiveDescriptionTextArea;

    @FXML
    private ComboBox<Integer> courseIdComboBox;

    private Objectif objectifToModify;
    private int selectedProgramId;
    private Programme programToModify;
    private int selectedObjectiveId;

   public void init(Programme program) {
       // programToModify = program;
        //  programIdTextField.setText(String.valueOf(program.getID_prog()));
       // programDescriptionTextArea.setText(program.getDesc_prog());
       // rateTextField.setText(String.valueOf(program.getRate()));
       // initialStateTextField.setText(program.getEtat_initial());
        //finalStateTextField.setText(program.getEtat_final());
       // userIdTextField.setText(String.valueOf(program.getID_user()));
        selectedProgramId = program.getID_prog();
    }

  /*public void initObjective(Objectif objective) {
        ObjectifServices ob = new ObjectifServices();
        objectifToModify = objective;
        if (objectifToModify != null && objectifToModify.getID_obj() != 0) {
            Programme programme = objectifToModify.getProgramme();
            // objectifIdTextField.setText(String.valueOf(objectifToModify.getID_obj()));
            //courseIdComboBox.setValue(objectifToModify.getID_cours());
            // programmeField.setText(String.valueOf(programme.getID_prog()));
           // objectiveDescriptionTextArea.setText(objectifToModify.getDescription_obj());
        } else {
           // objectifIdTextField.setText(null);
           // courseIdComboBox.setValue(null);
           // programmeField.setText(null);
            //objectiveDescriptionTextArea.setText(null);
        }
    }*/



    @FXML
    void initialize(Programme program) {
        // Programme sélectionné est passé en paramètre

        // Récupérer les objectifs associés à ce programme
        ObjectifServices objectiveService = new ObjectifServices();
        List<Objectif> objectifs = objectiveService.RecupererListObj(program.getID_prog());

        // Afficher les descriptions des objectifs dans le TableView
        ObservableList<Objectif> observableObjectiveList = FXCollections.observableArrayList(objectifs);
        objectiveTableView.setItems(observableObjectiveList);
        colDescr.setCellValueFactory(new PropertyValueFactory<>("description_obj"));

        // Vous pouvez ajouter d'autres colonnes au besoin
    }











    @FXML
    void ReturnToAffichage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/frontprog.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) objectiveTableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }


}
