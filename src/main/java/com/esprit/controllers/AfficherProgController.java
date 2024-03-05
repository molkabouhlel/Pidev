package com.esprit.controllers;

import com.esprit.models.Objectif;
import com.esprit.models.PdfExporter;
import com.esprit.models.Programme;
import com.esprit.services.ObjectifServices;
import com.esprit.services.ProgrammeServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
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
            PdfExporter.exportToPDF(EspaceObservableList, file);
        }

    }
    @FXML
    public void save1(ActionEvent event) {
        ProgrammeServices es = new ProgrammeServices();
        List<Programme> listEspace = es.afficher();
        ObservableList<Programme> EspaceObservableList = FXCollections.observableArrayList(listEspace);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            exportToExcel(EspaceObservableList, file);
        }
    }

    private void exportToExcel(ObservableList<Programme> data, File file) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            // Create cell styles for header and bold text
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Programme");
            headerRow.createCell(1).setCellValue("Nom Programme");
            headerRow.createCell(2).setCellValue("Description Programme");
            headerRow.createCell(3).setCellValue("Rate Programme");
            headerRow.createCell(4).setCellValue("Etat Initial Programme");
            headerRow.createCell(5).setCellValue("Etat Final Programme");
            headerRow.createCell(6).setCellValue("Date Début Programme");
            headerRow.createCell(7).setCellValue("Date Fin Programme");
            headerRow.createCell(8).setCellValue("ID Utilisateur Programme");
            headerRow.createCell(9).setCellValue("ID Objectif");
            headerRow.createCell(10).setCellValue("Description Objectif");

            // Apply header style to header row
            for (Cell cell : headerRow) {
                cell.setCellStyle(headerStyle);
            }

            // Populate data rows
            int rowIndex = 1;
            for (Programme programme : data) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(programme.getID_prog());
                row.createCell(1).setCellValue(programme.getNom_prog());
                row.createCell(2).setCellValue(programme.getDesc_prog());
                row.createCell(3).setCellValue(programme.getRate());
                row.createCell(4).setCellValue(programme.getEtat_initial());
                row.createCell(5).setCellValue(programme.getEtat_final());
                row.createCell(6).setCellValue(programme.getDate_debut().toString());
                row.createCell(7).setCellValue(programme.getDate_fin().toString());
                row.createCell(8).setCellValue(programme.getID_user());

                // Add Objectifs for the Programme
                ObjectifServices obj = new ObjectifServices();
                List<Objectif> allobject = obj.RecupererListObj(programme.getID_prog());
                for (Objectif objectif : allobject) {
                    Row objectifRow = sheet.createRow(rowIndex++);
                    objectifRow.createCell(9).setCellValue(objectif.getID_obj());
                    objectifRow.createCell(10).setCellValue(objectif.getDescription_obj());
                }
            }

            // Write the workbook to the file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                System.out.println("Data exported to Excel successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
