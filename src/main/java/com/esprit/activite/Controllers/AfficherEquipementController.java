package com.esprit.activite.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.esprit.activite.modeles.Equipement;
import com.esprit.activite.services.EquipementService;
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



public class AfficherEquipementController {

    @FXML
    private ResourceBundle resources;
    private int idc;
    private Equipement eqrecherche;
    private int ref;

    @FXML
    private URL location;
    @FXML
    private ComboBox<?> combobox;
    @FXML
    private TextField recherche;
    @FXML
    private Button trie;
    @FXML
    private TableColumn<Equipement, Void> action;
    @FXML
    private TableColumn<Equipement, Integer> id_eq;

    @FXML
    private TableColumn<?, ?> description_eq;

    @FXML
    private TableColumn<?, ?> id_ceq;
    // @FXML
   // private TableColumn<?, ?> id_coach;


    @FXML
    private TableColumn<?, ?> id_espace;
    @FXML
    private Button supprimereq;


    @FXML
    private TableColumn<?, ?> id_m;

    @FXML
    private TableColumn<Equipement, String> image;

    @FXML
    private TableColumn<?, ?> nom_eq;

    @FXML
    private TableColumn<?, ?> quantite_dispo;

    @FXML
    private TableColumn<Equipement, String> ref_eq;

    @FXML
    private TableView<Equipement> tableview;
   private String idsel;
private int id_eqq;

    @FXML
    void modifier(ActionEvent event) throws IOException {
       // id_eqq= id_eq.getCellData();
        if (idc != 0) {
            EquipementService es = new EquipementService();
            Equipement evselected = es.rechercheeq(idc);
           // System.out.println(evselected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEquipement.fxml"));
            Parent root = loader.load();
            ModifierEquipementController MEC = loader.getController();
            MEC.initData(evselected);
            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }

        }

    @FXML
    void initialize() {
///////////////////////recherche
        //recherche.textProperty().addListener((observable,oldValue,newValue)->{recherche();});

        EquipementService c = new EquipementService();
        List<Equipement> catr = c.afficher();
        ObservableList<Equipement> observableList = FXCollections.observableList(catr);
        tableview.setItems(observableList);
        id_eq.setCellValueFactory(new PropertyValueFactory<>("id_eq"));
        ref_eq.setCellValueFactory(new PropertyValueFactory<>("ref_eq"));
        nom_eq.setCellValueFactory(new PropertyValueFactory<>("nom_eq"));
        description_eq.setCellValueFactory(new PropertyValueFactory<>("description_eq"));
        quantite_dispo.setCellValueFactory(new PropertyValueFactory<>("quantite_dispo"));
       // id_coach.setCellValueFactory(new PropertyValueFactory<>("id_coach"));

        id_espace.setCellValueFactory(new PropertyValueFactory<>("id_espace"));
        id_ceq.setCellValueFactory(new PropertyValueFactory<>("id_ceq"));
        id_m.setCellValueFactory(new PropertyValueFactory<>("id_m"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        image.setCellFactory(column -> {
            return new TableCell<Equipement, String>() { private final ImageView imageView = new ImageView();
                {
                    // Set the size of the ImageView as needed
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
                            // Load the image from the imagePath and set it in the ImageView
                            Image image = new Image(new File(imagePath).toURI().toString());
                            imageView.setImage(image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        });



        EquipementService rs = new EquipementService();

        ObservableList<Equipement> reponsesData = FXCollections.observableArrayList(rs.afficher());

        // Create FilteredList for real-time search
        FilteredList<Equipement> filteredData = new FilteredList<>(reponsesData, b -> true);
        tableview.setItems(filteredData);

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipement -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (equipement.getNom_eq().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });





        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               idc  = newSelection.getId_eq();
                System.out.println(idc);
             //   ref = newSelection.getId_eq();
               // id_eq.setText(String.valueOf(newSelection.getId_eq()));
                //  date_rv.setText(Timestamp.valueOf(newSelection.getDate_rv()));
            } else {
                idc = -1;
                //ref = -1;
            }
        });
        boutonsupp();
        modif();
    }
    @FXML
    void supprimer(ActionEvent event) {
        EquipementService c=new EquipementService();


        int selectedID = tableview.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Equipement catASupprimer = tableview.getItems().get(selectedID);


            c.supprimer(catASupprimer);

            // Mettez à jour la TableView
            tableview.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("eq nest pas selectioner");
            alerte.show();
        }

    }
    //TODO **************BOUTON SUPPRIMER cree tableview
    private void boutonsupp() {
        action.setCellFactory(col -> new TableCell<Equipement, Void>() {
            private final Button participerButton = new Button("supprimer");

            {
                participerButton.setOnAction(event -> {
                    //   tableview.edit(-1, null);
                    Equipement equipement = getTableView().getItems().get(getIndex());
                 // supprimer(equipement);


                    supprimerE(equipement);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(participerButton);
                }
            }
        });
    }
    @FXML
    void refresh(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjoutEquipement.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any

    }
}
    private void supprimerE(Equipement ev) {
        EquipementService p = new EquipementService();
        p.supprimer(ev);

        // Actualisez la TableView pour refléter la suppression
        tableview.getItems().remove(ev);
    }

    private void modif() {
        tableview.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Equipement selectedItem = tableview.getSelectionModel().getSelectedItem();
                try {
                    afficherFormulaireModification();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    private void afficherFormulaireModification() throws IOException {
        if (idc != -1) {
            EquipementService es = new EquipementService();
            Equipement eqselected = es.rechercheeq(idc);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEquipement.fxml"));
            Parent root = loader.load();
            ModifierEquipementController MEC = loader.getController();
            MEC.initData(eqselected);
            Stage currentStage = (Stage) tableview.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }
    }

    private void recherche() {
        EquipementService rs = new EquipementService();

        ObservableList<Equipement> reponsesData = FXCollections.observableArrayList(rs.afficher());

        // Create FilteredList for real-time search
        FilteredList<Equipement> filteredData = new FilteredList<>(reponsesData, b -> true);
        tableview.setItems(filteredData);

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(equipement -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (equipement.getNom_eq().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

    }

}
