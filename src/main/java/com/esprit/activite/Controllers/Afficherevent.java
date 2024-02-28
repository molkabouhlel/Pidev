package com.esprit.activite.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import com.esprit.activite.modeles.*;
import com.esprit.activite.services.CoursService;
import com.esprit.activite.services.EvenementService;
import com.esprit.activite.services.participerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Afficherevent {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableColumn<Evenement, Void> action;
    @FXML
    private TableColumn<Evenement, Integer> capacite;

    @FXML
    private TableColumn<Evenement, Timestamp> dated;

    @FXML
    private TableColumn<Evenement, Timestamp> datefin;

    @FXML
    private TableColumn<Evenement, String> deseven;

    @FXML
    private TableColumn<Evenement, String> evnom;

    @FXML
    private TableColumn<Evenement, typec> id_c;

    @FXML
    private TableColumn<Evenement, type_ev> id_catev;

    @FXML
    private TableColumn<?, ?> id_es;

    @FXML
    private TableColumn<Evenement, String> imageurl;

    @FXML
    private TableView<Evenement> viewev;
    private int idevselected;
    //stat
    @FXML
    private PieChart pieChart;


    @FXML
    void modifaffich(ActionEvent event) throws IOException {
        if (idevselected != -1) {
            EvenementService es = new EvenementService();
            Evenement evselected = es.rechercheEV(idevselected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateevent.fxml"));
            Parent root = loader.load();
            Updateevent MEC = loader.getController();
            MEC.initData(evselected);
            Stage currentStage = (Stage) viewev.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }

    }

    @FXML
    void retourner(ActionEvent event) {


Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/addevent.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void supp(ActionEvent event) {
        EvenementService c=new EvenementService();


        int selectedID = viewev.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Evenement EvASupprimer = viewev.getItems().get(selectedID);


            c.supprimer(EvASupprimer);

            // Mettez à jour TableView
            viewev.getItems().remove(selectedID);
        } else {
            // Aucune ligne sélectionnée, affichez un message d'erreur ou prenez une autre action appropriée
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("erreur");
            alerte.setContentText("cours nest pas selectioner");
            alerte.show();
        }

    }

    @FXML
    void initialize() {
        EvenementService c = new EvenementService();
        List<Evenement> EV = c.afficher();
        ObservableList<Evenement> observableList = FXCollections.observableList(EV);
        viewev.setItems(observableList);
        evnom.setCellValueFactory(new PropertyValueFactory<>("nom_ev"));
        deseven.setCellValueFactory(new PropertyValueFactory<>("description_ev"));
        imageurl.setCellValueFactory(new PropertyValueFactory<>("image_ev"));
        dated.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        datefin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        capacite.setCellValueFactory(new PropertyValueFactory<>("capacite_max"));
        id_es.setCellValueFactory(new PropertyValueFactory<>("id_espace"));
        id_c.setCellValueFactory(new PropertyValueFactory<>("id_typec"));
        id_catev.setCellValueFactory(new PropertyValueFactory<>("id_type_ev"));

        //

        imageurl.setCellFactory(column -> {
            return new TableCell<Evenement, String>() { private final ImageView imageView = new ImageView();
                {

                    imageView.setFitWidth(60);
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

        //
        viewev.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idevselected = newSelection.getId_ev();
            } else {
                idevselected = -1;
            }
        });
        SUPP();
        modif();

        //
        viewev.setEditable(true);


        evnom.setCellFactory(TextFieldTableCell.forTableColumn());
        EvenementService ss = new EvenementService();
        // Save changes on commit
        evnom.setOnEditCommit(event -> {
            Evenement s = event.getRowValue();
            s.setNom_ev(event.getNewValue());
            ss.modifier(s);
        });
        deseven.setCellFactory(TextFieldTableCell.forTableColumn());

        // Save changes on commit
        deseven.setOnEditCommit(event -> {
            Evenement s = event.getRowValue();
            s.setDescription_ev(event.getNewValue());
            ss.modifier(s);
        });

    }
    private void SUPP() {
        action.setCellFactory(col -> new TableCell<Evenement, Void>() {
            private final Button participerButton = new Button("supprimer");

            {
                participerButton.setOnAction(event -> {
                    //   tableview.edit(-1, null);
                    Evenement e = getTableView().getItems().get(getIndex());
                    supprimer(e);
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
    private void supprimer(Evenement ev) {
        EvenementService p = new EvenementService();
        p.supprimer(ev);
        // Actualisez la TableView pour refléter la suppression
        viewev.getItems().remove(ev);
    }


    //todo
    private void modif() {
        viewev.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
              //  Cours selectedItem = viewev.getSelectionModel().getSelectedItem();
                try {
                    afficherFormulaireModification();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    private void afficherFormulaireModification() throws IOException {
        if (idevselected != -1) {
            EvenementService es = new EvenementService();
            Evenement evselected = es.rechercheEV(idevselected);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateevent.fxml"));
            Parent root = loader.load();
            Updateevent MEC = loader.getController();
            MEC.initData(evselected);
            Stage currentStage = (Stage) viewev.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }

    }
    @FXML
    void addev(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/addevent.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();

    }
    //stat

    @FXML
    void stat(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/stat.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}


