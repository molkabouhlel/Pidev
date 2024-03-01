package com.esprit.controllers;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;


import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.util.List;

import com.esprit.models.Programme;
import com.esprit.services.ProgrammeServices;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AfficherProgController implements Initializable {

    @FXML
    private Button Btajout_prog;

    @FXML
    private Button Btmodifier_prog;

    @FXML
    private Button Btsupp_prog;

    @FXML
    private TableColumn<Programme, Integer> Iduser_prog;

    @FXML
    private TableView<Programme> Prog_TableView;

    @FXML
    private TableColumn<Programme, Date> datedeb_prog;

    @FXML
    private TableColumn<Programme, Date> datefin_prog;
    @FXML
    private TableColumn<Programme, String> delete;

    @FXML
    private TableColumn<Programme, String> description_prog;

    @FXML
    private TableColumn<Programme, String> etatfin_prog;

    @FXML
    private TableColumn<Programme, String> etatinitial_prog;

    @FXML
    private TableColumn<Programme, String> nom_prog;

    @FXML
    private TableColumn<Programme, Float> rate_prog;

    private int Id_prog_Selected;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProgrammeServices es = new ProgrammeServices();
        List<Programme> listProg = es.afficher();
        //System.out.println(es.afficher());

        ObservableList<Programme> ProgObservableList = FXCollections.observableArrayList(listProg);
        Prog_TableView.setItems(ProgObservableList);
        nom_prog.setCellValueFactory(new PropertyValueFactory<>("nom_prog"));
        description_prog.setCellValueFactory(new PropertyValueFactory<>("desc_prog"));
        rate_prog.setCellValueFactory(new PropertyValueFactory<>("rate"));
        etatinitial_prog.setCellValueFactory(new PropertyValueFactory<>("etat_initial"));
        etatfin_prog.setCellValueFactory(new PropertyValueFactory<>("etat_final"));
        datedeb_prog.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        datefin_prog.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        Iduser_prog.setCellValueFactory(new PropertyValueFactory<>("ID_user"));


        Prog_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Id_prog_Selected = newSelection.getID_prog();
            } else {
                Id_prog_Selected = -1;
            }
        });
        Prog_TableView.setEditable(true);
        nom_prog.setCellFactory(TextFieldTableCell.forTableColumn());
        description_prog.setCellFactory(TextFieldTableCell.forTableColumn());
        etatinitial_prog.setCellFactory(TextFieldTableCell.forTableColumn());
        etatfin_prog.setCellFactory(TextFieldTableCell.forTableColumn());

        ProgrammeServices ss = new ProgrammeServices();
        // Save changes on commit
        nom_prog.setOnEditCommit(event -> {
            Programme P = event.getRowValue();
            P.setNom_prog(event.getNewValue());
            ss.modifier(P);
        });

        description_prog.setOnEditCommit(event -> {
            Programme s = event.getRowValue();
            s.setDesc_prog(event.getNewValue());
            ss.modifier(s);
        });
        etatinitial_prog.setOnEditCommit(event -> {
            Programme s = event.getRowValue();
            s.setDesc_prog(event.getNewValue());
            ss.modifier(s);
        });
        etatfin_prog.setOnEditCommit(event -> {
            Programme s = event.getRowValue();
            s.setDesc_prog(event.getNewValue());
            ss.modifier(s);
        });


        ///////////////////////////////////// DELETE PROGRAMM FROM TABLE ///////////////////////////////////////////////////

        Callback<TableColumn<Programme, String>, TableCell<Programme, String>> cellFactory =
                new Callback<TableColumn<Programme, String>, TableCell<Programme, String>>() {
                    @Override
                    public TableCell<Programme, String> call(final TableColumn<Programme, String> param) {
                        final TableCell<Programme, String> cell = new TableCell<Programme, String>() {

                            final Button deleteButton = new Button("Delete");

                            {
                                deleteButton.setOnAction((ActionEvent event) -> {
                                    Programme item = getTableView().getItems().get(getIndex());
                                    // Call your function to delete from the database
                                    ss.supprimer(item);
                                    getTableView().getItems().remove(item);
                                });
                            }

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    setGraphic(deleteButton);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        delete.setCellFactory(cellFactory);


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    void RedirectToModifyForm_prog(ActionEvent event) throws IOException {
        if (Id_prog_Selected != -1) {
            ProgrammeServices es = new ProgrammeServices();
            Programme Espace_Selected = es.rechercheProgramme(Id_prog_Selected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ModifyProg.fxml"));
            Parent root = loader.load();
            ModifyProgController MEC = loader.getController();
            MEC.initData(Espace_Selected);
            Stage currentStage = (Stage) Prog_TableView.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }

    }

    @FXML
    void SupprimerProg(ActionEvent event) {
        if (Id_prog_Selected != -1) {
            ProgrammeServices es = new ProgrammeServices();
            Programme Espace_Selected = es.rechercheProgramme(Id_prog_Selected);
            es.supprimer(Espace_Selected);
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("suppresion programme");
            alerte.setContentText("programme  supprime");
            alerte.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/AfficherProg.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) Prog_TableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void redirecttoAjoutForm_prog(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/addprog.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Prog_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));
    }


    @FXML
    void save(ActionEvent event) {
        ProgrammeServices es = new ProgrammeServices();
        List<Programme> listEspace = es.afficher();
        ObservableList<Programme> EspaceObservableList = FXCollections.observableArrayList(listEspace);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            exportToPDF(EspaceObservableList, file);
        }

    }
    public void exportToPDF(ObservableList<Programme> tableView, File filename){
        try {
            PdfWriter writer = new PdfWriter(filename.getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            for (Programme espace : tableView) {
                document.add(new Paragraph(espace.toString()));
            }

            document.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void RedirectToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Menu.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Prog_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void exportStat(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stat.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Prog_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

}
