package com.esprit.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import com.esprit.models.Espace;
import com.esprit.services.EspaceService;

import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.sql.Time;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class AfficherEspaceController {

    @FXML
    private Button AjoutEspace;

    @FXML
    private TableView<Espace> Espace_TableView;

    @FXML
    private TableColumn<Espace, String> description_espace;

    @FXML
    private TableColumn<Espace, Time> heure_debut;

    @FXML
    private TableColumn<Espace, Time> heure_fin;

    @FXML
    private TableColumn<Espace, Integer> id_espace;

    @FXML
    private TableColumn<Espace, String> nom_espace;

    @FXML
    private TableColumn<Espace, String> Delete;

    @FXML
    private ComboBox<String> trie;

    @FXML
    private TextField Recherche;

    private int Id_espace_Selected;

    public void initialize() {
        EspaceService es = new EspaceService();
        List<Espace> listEspace = es.afficher();
        //System.out.println(es.afficher());
        trie.setValue("nom");
        trie.getItems().addAll("nom", "temps", "");

        /////////////////////////////////////////AFFICHAGE TABLE////////////////////////////////////////////
        ObservableList<Espace> EspaceObservableList = FXCollections.observableArrayList(listEspace);
        Espace_TableView.setItems(EspaceObservableList);
        //id_espace.setCellValueFactory(new PropertyValueFactory<>("id_espace"));
        nom_espace.setCellValueFactory(new PropertyValueFactory<>("nom_espace"));
        description_espace.setCellValueFactory(new PropertyValueFactory<>("description_espace"));
        heure_debut.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
        heure_fin.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
        Delete.setCellValueFactory(new PropertyValueFactory<Espace, String>(""));


        /////////////////////////////////RECHERCHE/////////////////////////////////
        Recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filter();
        });
        Recherche.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && Recherche.getText().isEmpty()) {
                Espace_TableView.setItems(EspaceObservableList);
            }
        });


        /////////////////////////////////////////EDITER SUR TABLE////////////////////////////////////////////
        Espace_TableView.setEditable(true);
        nom_espace.setCellFactory(TextFieldTableCell.forTableColumn());
        description_espace.setCellFactory(TextFieldTableCell.forTableColumn());

        heure_debut.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Time>() {
            @Override
            public String toString(Time object) {
                if (object == null) {
                    return "";
                }
                return object.toString();
            }

            @Override
            public Time fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                // Parse the string to LocalTime, assuming it's in HH:mm:ss format
                return Time.valueOf(string);
            }
        }));

        heure_fin.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Time>() {
            @Override
            public String toString(Time object) {
                if (object == null) {
                    return "";
                }
                return object.toString();
            }

            @Override
            public Time fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                // Parse the string to LocalTime, assuming it's in HH:mm:ss format
                return Time.valueOf(string);
            }
        }));


        /////////////////////////////////////////Recupere text TABLE et modifie////////////////////////////////////////////
        EspaceService Es = new EspaceService();
        // Save changes on commit
        nom_espace.setOnEditCommit(event -> {
            Espace e = event.getRowValue();
            e.setNom_espace(event.getNewValue());
            Es.modifier(e);
        });

        description_espace.setOnEditCommit(event -> {
            Espace e = event.getRowValue();
            e.setDescription_espace(event.getNewValue());
            Es.modifier(e);
        });

        heure_debut.setOnEditCommit(event -> {
            Espace e = event.getRowValue();
            e.setHeure_debut(event.getNewValue());
            Es.modifier(e);
        });


        heure_fin.setOnEditCommit(event -> {
            Espace e = event.getRowValue();
            e.setHeure_fin(event.getNewValue());
            Es.modifier(e);
        });

        /////////////////////////////////////////Delete from table////////////////////////////////////////////
        Callback<TableColumn<Espace, String>, TableCell<Espace, String>> cellFactory =
                new Callback<TableColumn<Espace, String>, TableCell<Espace, String>>() {
                    @Override
                    public TableCell<Espace, String> call(final TableColumn<Espace, String> param) {
                        final TableCell<Espace, String> cell = new TableCell<Espace, String>() {

                            final Button deleteButton = new Button("Delete");

                            {
                                deleteButton.setOnAction((ActionEvent event) -> {
                                    Espace item = getTableView().getItems().get(getIndex());
                                    // Call your function to delete from the database
                                    Es.supprimer(item);
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

        Delete.setCellFactory(cellFactory);


        /////////////////////////////////////////Selection TABLE////////////////////////////////////////////
        Espace_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Id_espace_Selected = newSelection.getId_espace();
            } else {
                Id_espace_Selected = -1;
            }
        });


    }

    public void filter(){
        String searchText = Recherche.getText().toLowerCase();

        List<Espace> filteredespace = Espace_TableView.getItems().stream()
                .filter(espace -> espace.getNom_espace().toLowerCase().startsWith(searchText))
                .collect(Collectors.toList());

        ObservableList<Espace> filteredItems = FXCollections.observableArrayList(filteredespace);
        Espace_TableView.setItems(filteredItems);
    }

    @FXML
    void Trier(ActionEvent event) {
        ObservableList<Espace> espace = Espace_TableView.getItems();

        Comparator<Espace> comparator = null;

        if ("nom".equals(trie.getValue())) {
            comparator = Comparator.comparing(Espace::getNom_espace);
        }

        if ("temps".equals(trie.getValue())) {
            comparator = Comparator.comparing(Espace::getHeure_debut);
        }

        if (comparator != null) {
            FXCollections.sort(espace, comparator);
            Espace_TableView.setItems(espace);
        }
    }

    @FXML
    void redirecttoAjoutForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutEspace.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Espace_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }


    @FXML
    void RedirectToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuModuleClub.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) Espace_TableView.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void save(ActionEvent event) {
        EspaceService es = new EspaceService();
        List<Espace> listEspace = es.afficher();
        ObservableList<Espace> EspaceObservableList = FXCollections.observableArrayList(listEspace);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            exportToPDF(EspaceObservableList, file);
        }

    }


    public void exportToPDF(ObservableList<Espace> tableView, File filename) {
        try {
            PdfWriter writer = new PdfWriter(filename.getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            Text title = new Text("Espace Document")
                    .setFontSize(20)
                    .setUnderline()
                    .setBold();
            Paragraph titleParagraph = new Paragraph(title)
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(titleParagraph);
            //document.add(new Paragraph("Espace Document"));
            Table table = new Table(4);

            // Ajouter les en-têtes de colonne
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Espace Name")));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Description")));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Time Open")));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Time Close")));

            for (Espace espace : tableView) {
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(espace.getNom_espace()))));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(espace.getDescription_espace()))));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(espace.getHeure_debut()))));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(String.valueOf(espace.getHeure_fin()))));
            }
            document.add(table);

            document.close();
            writer.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(filename);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}




