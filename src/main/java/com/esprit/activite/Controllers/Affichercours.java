package com.esprit.activite.Controllers;
import com.esprit.activite.modeles.Cours;
import com.esprit.activite.services.CoursService;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

import java.net.MalformedURLException;
import java.sql.Time;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Paragraph;

public class Affichercours {
    @FXML
    private TableColumn<Cours,Void> action;
    @FXML
    private TableColumn<Cours,String> desc;

    @FXML
    private TableColumn<Cours, Time> dureec;

    @FXML
    private TableColumn<Cours,Integer> id_typc;

    @FXML
    private TableColumn<Cours,Integer> idcc;
    @FXML
    private ComboBox<String> trie;

    @FXML
    private TableColumn<Cours,Integer> idclub;

    @FXML
    private TableColumn<Cours, String> imgc;

    @FXML
    private TableColumn<Cours, String> nomc;

    @FXML
    private TableView<Cours> tableview;
    private int idcoursselected;
    @FXML
    private TextField recherche;
    @FXML
    private ChoiceBox<String> valcat;
    @FXML
    private PieChart pieChart;
   private ObservableList<Cours> observableList1 = FXCollections.observableArrayList();
    @FXML
    void returnlist(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/interfacecours.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @FXML
    void initialize() {
        List<String> sortTypes = new ArrayList<>();
        sortTypes.add("duree");
        sortTypes.add("nom");

        trie.getItems().addAll(sortTypes);
        CoursService c = new CoursService();
        List<Cours> cours = c.afficher();
       ObservableList<Cours> observableList = FXCollections.observableList(cours);
       observableList1 = FXCollections.observableList(cours);
        tableview.setItems(observableList);
        nomc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        imgc.setCellValueFactory(new PropertyValueFactory<>("imagec"));
        dureec.setCellValueFactory(new PropertyValueFactory<>("duree"));
        idcc.setCellValueFactory(new PropertyValueFactory<>("idcoach"));
        idclub.setCellValueFactory(new PropertyValueFactory<>("idclub"));
        id_typc.setCellValueFactory(new PropertyValueFactory<>("id_typec"));
        ///
        tableview.setEditable(true);
        nomc.setCellFactory(TextFieldTableCell.forTableColumn());
        desc.setCellFactory(TextFieldTableCell.forTableColumn());
        CoursService ss = new CoursService();
        // Save changes on commit
        nomc.setOnEditCommit(event -> {
            Cours s = event.getRowValue();
            s.setNom(event.getNewValue());
            ss.modifier(s);
        });

        desc.setOnEditCommit(event -> {
            Cours s = event.getRowValue();
            s.setDescription(event.getNewValue());
            ss.modifier(s);
        });

      /*  dureec.setOnEditCommit(event -> {
            Cours s = event.getRowValue();
            s.setDuree(event.getNewValue());
            ss.modifier(s);
        });*/

        //////
        imgc.setCellFactory(column -> {
            return new TableCell<Cours, String>() { private final ImageView imageView = new ImageView();
                {

                    imageView.setFitWidth(150);
                    imageView.setFitHeight(130);
                    setGraphic(imageView);
                }

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);
                    if (imagePath == null || empty) {
                        imageView.setImage(null);
                    } else {
                        try {

                            Image image = new Image(new File(imagePath).toURI().toString());
                            imageView.setImage(image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        });
        ///
        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idcoursselected = newSelection.getId();
            } else {
                idcoursselected = -1;
            }
        });
        ///
        boutonsupp();
        modif();
        //search
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch(newValue);
        });
        //filtre
        ObservableList<String> categories = getCategorieList();
        valcat.setItems(categories);
        valcat.setValue(null);

        valcat.setOnAction(event -> {
            String selectedCategorie = valcat.getValue();
            if (selectedCategorie == null) {
                tableview.setItems(observableList);
            } else {
                ObservableList<Cours> filteredList = observableList.filtered(cc -> cc.getId_typec().getTypecours().equals(selectedCategorie));
                tableview.setItems(filteredList);
            }
        });

    }
    private ObservableList<String> getCategorieList() {
        Set<String> categorieSet = observableList1.stream()
                .map(cours -> cours.getId_typec().getTypecours())
                .collect(Collectors.toSet());
        return FXCollections.observableArrayList(categorieSet);
    }


    @FXML
    void modifiercours(ActionEvent event) throws IOException {
        if (idcoursselected != -1) {
            CoursService es = new CoursService();
            Cours coursselected = es.rechercheCours(idcoursselected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierCours.fxml"));
            Parent root = loader.load();
            ModifierCours MEC = loader.getController();
            MEC.initData(coursselected);
            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }



    }


    @FXML
    void supprimercours(ActionEvent event) {
        CoursService c=new CoursService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Cours coursASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(coursASupprimer);


            tableview.getItems().remove(selectedID);
        } else {

            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("cours nest pas selectioner");
            alerte.show();
        }
    }

    //TODO **************BOUTON SUPPRIMER cree tableview
    private void boutonsupp() {
        action.setCellFactory(col -> new TableCell<Cours, Void>() {
            private final Button supprimerButton = new Button("supprimer");

            {
                supprimerButton.setOnAction(event -> {
                 //   tableview.edit(-1, null);
                    Cours cours = getTableView().getItems().get(getIndex());
                    supprimer(cours);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(supprimerButton);
                }
            }
        });
    }

    private void supprimer(Cours cours) {
      CoursService p = new CoursService();
        p.supprimer(cours);

        tableview.getItems().remove(cours);
    }

//todo
    private void modif() {
     tableview.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2) {
            Cours selectedItem = tableview.getSelectionModel().getSelectedItem();
            try {
                afficherFormulaireModification();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    });

}
    private void afficherFormulaireModification() throws IOException {
        if (idcoursselected != -1) {
            CoursService es = new CoursService();
            Cours coursselected = es.rechercheCours(idcoursselected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierCours.fxml"));
            Parent root = loader.load();
            ModifierCours MEC = loader.getController();
            MEC.initData(coursselected);
            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
    }
}


    @FXML
    void add(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/interfacecours.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }
    @FXML
    void stat(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/statcours.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();

    }
    @FXML
    void pdf(ActionEvent event) {
        CoursService es = new CoursService();
        List<Cours> listEspace = es.afficher();
        ObservableList<Cours> EspaceObservableList = FXCollections.observableArrayList(listEspace);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            exportToPDF(EspaceObservableList, file);
        }
    }

    public void exportToPDF(ObservableList<Cours> tableView, File filename) {
        try {
            PdfWriter writer = new PdfWriter(filename.getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer); // Créez un objet PdfDocument à partir de PdfWriter
            Document document = new Document(pdf);

            Paragraph title = new Paragraph("liste des cours")
                    .setFontSize(25)
                    .setBold()
                    .setFontColor(new DeviceRgb(0, 0, 255))
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Ajout de la bordure bleue
            Table outerTable = new Table(1);
            outerTable.addCell(new Cell().setBorder(new SolidBorder(new DeviceRgb(0, 0, 255), 2)).add(new Paragraph("")));
            // Création du tableau avec 3 colonnes
           // Table table = new Table(3);
            Table table = new Table(4);

            // Ajout des en-têtes de colonnes
            table.addCell(new Cell().add(new Paragraph("Nom")));
            table.addCell(new Cell().add(new Paragraph("Description")));
            table.addCell(new Cell().add(new Paragraph("duree"))); // Ajoutez autant de colonnes que nécessaire
            table.addCell(new Cell().add(new Paragraph("categorie cours")));
            table.addCell(new Cell().add(new Paragraph("club")));
            table.addCell(new Cell().add(new Paragraph("coach")));
            // Ajout des données à chaque ligne du tableau
            for (Cours cours : tableView) {
                table.addCell(new Cell().add(new Paragraph(cours.getNom())));
                table.addCell(new Cell().add(new Paragraph(cours.getDescription())));
                table.addCell(new Cell().add(new Paragraph(cours.getDuree().toString())));
                table.addCell(new Cell().add(new Paragraph(cours.getId_typec().getTypecours())));
                table.addCell(new Cell().add(new Paragraph(cours.getIdclub().getNom_club())));
                // table.addCell(new Cell().add(new Paragraph(cours.getIdcoach().toString())));
            }

// Appliquer une bordure bleue autour du tableau
            table.setBorder(new SolidBorder(new DeviceRgb(0, 0, 255), 2));
            Paragraph centeredTableParagraph = new Paragraph().add(table);
                  centeredTableParagraph.setTextAlignment(TextAlignment.CENTER);
            // Ajout du tableau au document
            document.add(table);

            document.close();
            pdf.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //petit prob de retour
    private void handleSearch(String searchText) {
        // Create a filtered list based on the search text
        ObservableList<Cours> filteredList = tableview.getItems().filtered(cours ->
                cours.getNom().toLowerCase().contains(searchText.toLowerCase()));

        // Update the TableView with the filtered list
        tableview.setItems(filteredList);
    }

    @FXML
    void trier(ActionEvent event) {
        FilteredList<Cours> filteredData = new FilteredList<>(tableview.getItems());

        SortedList<Cours> sortedData = new SortedList<>(filteredData);
        // Trié par date par défaut
        Comparator<Cours> nomComparator = Comparator.comparing(Cours::getNom);
        // Trié par nom
        Comparator<Cours> dureeComparator = Comparator.comparing(Cours::getDuree);

        sortedData.comparatorProperty().bind(trie.getSelectionModel().selectedItemProperty().asString().map(s -> {
            if (s.equals("nom")) {
                return nomComparator;
            } else if (s.equals("duree")) {
                return dureeComparator;
            }
            return null; // Ajustez cela selon vos besoins
        }));

        tableview.setItems(sortedData);
    }
    }



