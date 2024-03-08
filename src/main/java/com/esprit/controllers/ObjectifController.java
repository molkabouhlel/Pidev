package com.esprit.controllers;

import com.esprit.models.Objectif;
import com.esprit.models.Programme;
import com.esprit.services.ObjectifServices;
import com.esprit.services.ProgrammeServices;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ObjectifController {
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
        programToModify = program;
        programIdTextField.setText(String.valueOf(program.getID_prog()));
        programDescriptionTextArea.setText(program.getDesc_prog());
        rateTextField.setText(String.valueOf(program.getRate()));
        initialStateTextField.setText(program.getEtat_initial());
        finalStateTextField.setText(program.getEtat_final());
        userIdTextField.setText(String.valueOf(program.getID_user()));
        selectedProgramId = program.getID_prog();
    }

    public void initObjective(Objectif objective) {
        ObjectifServices ob = new ObjectifServices();
        objectifToModify = objective;
        if (objectifToModify != null && objectifToModify.getID_obj() != 0) {
            Programme programme = objectifToModify.getProgramme();
          //  objectifIdTextField.setText(String.valueOf(objectifToModify.getID_obj()));
            courseIdComboBox.setValue(objectifToModify.getID_cours());
            programmeField.setText(String.valueOf(programme.getID_prog()));
            objectiveDescriptionTextArea.setText(objectifToModify.getDescription_obj());
        } else {
           // objectifIdTextField.setText(null);
            courseIdComboBox.setValue(null);
            programmeField.setText(null);
            objectiveDescriptionTextArea.setText(null);
        }
    }


    public void initialize(Programme program) {
        ///////////////////////////CONTROLE SAISIE/////////////////////////////////////////////////////////////////////
        programIdTextField.setEditable(false);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        programToModify = program;
        ProgrammeServices programService = new ProgrammeServices();

        List<Integer> lc = programService.RecupereridCours();
        courseIdComboBox.setItems(FXCollections.observableArrayList(lc));


        ObjectifServices objectiveService = new ObjectifServices();
        List objectif = objectiveService.RecupererListObj(programToModify.getID_prog());
        System.out.println(objectif);

        ObservableList<Objectif> observableObjectiveList = FXCollections.observableArrayList(objectif);
        objectiveTableView.setItems(observableObjectiveList);
        colIdObj.setCellValueFactory(new PropertyValueFactory<>("ID_obj"));
        colDescr.setCellValueFactory(new PropertyValueFactory<>("description_obj"));
        // Assurez-vous de retirer les autres colonnes que vous ne souhaitez pas afficher
        objectiveTableView.getColumns().clear();
        objectiveTableView.getColumns().addAll(colIdObj, colDescr);
        // ...

        objectiveTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedObjectiveId = newSelection.getID_obj();
                System.out.println(selectedObjectiveId);
                Objectif objective = objectiveService.rechercheObj(selectedObjectiveId);
                System.out.println(objective);
                initObjective(objective);
            } else {
                selectedObjectiveId = -1;
            }
        });
    }

    @FXML
    void addObjective(ActionEvent event) throws IOException {
        ProgrammeServices programService = new ProgrammeServices();
        Programme program = programService.rechercheProgramme(Integer.parseInt(programmeField.getText()));

        ObjectifServices objectiveService = new ObjectifServices();
        objectiveService.ajouter(new Objectif((courseIdComboBox.getValue()), program, objectiveDescriptionTextArea.getText()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Objective Added");
        alert.setContentText("Objective successfully added");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AffichageProg1.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void clearObjective(ActionEvent event) {
        //courseIdComboBox.clear();
        programmeField.clear();
        objectiveDescriptionTextArea.clear();
    }


    @FXML
    void updateObjective(ActionEvent event) throws IOException {
        ObjectifServices objectiveService = new ObjectifServices();
        ProgrammeServices programService = new ProgrammeServices();
        Programme program = programService.rechercheProgramme(Integer.parseInt(programmeField.getText()));

        objectifToModify.setID_obj(Integer.parseInt(objectifIdTextField.getText()));
        objectifToModify.setID_cours((courseIdComboBox.getValue()));
        objectifToModify.setProgramme(program);
        objectifToModify.setDescription_obj(objectiveDescriptionTextArea.getText());

        objectiveService.modifier(objectifToModify);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Objective");
        alert.setContentText("Modification successful");
        alert.showAndWait();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AffichageProg1.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void deleteObjective(ActionEvent event) throws IOException {
        ObjectifServices objectiveService = new ObjectifServices();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AffichageProg1.fxml"));
        Parent root = loader.load();
        Objectif selectedObjective = objectiveTableView.getSelectionModel().getSelectedItem();
        if (selectedObjective != null) {
            objectiveService.supprimer(selectedObjective);
            Stage currentStage = (Stage) objectiveTableView.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } else {
            System.out.println("Please select an objective to delete.");
        }
    }
    @FXML
    void ReturnToAffichage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AffichageProg1.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) objectiveTableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }


}
