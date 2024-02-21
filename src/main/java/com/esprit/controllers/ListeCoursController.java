package com.esprit.controllers;

import com.esprit.models.Club;

import com.esprit.models.ListCours;
import com.esprit.services.ClubService;

import com.esprit.services.ListCoursService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

public class ListeCoursController implements Initializable {

    @FXML
    private Button Ajout_ListeCours;

    @FXML
    private ComboBox<Integer> Club;

    @FXML
    private Button DeleteListeCours;

    @FXML
    private TableView<ListCours> ListeCourd_TableView;

    @FXML
    private Button ModifyListeCours;

    @FXML
    private TableColumn<ListCours, Integer> cours;

    @FXML
    private TableColumn<ListCours, Integer> id;

    @FXML
    private TableColumn<ListCours, Club> id_club;

    @FXML
    private TextField id_ListeCours;

    @FXML
    private ComboBox<Integer> id_cours;

    private int  List_Selected;
    private ListCours ListCoursToModifier;


    public void init(ListCours LS) {
        ListCoursToModifier = LS;

        id_ListeCours.setText(String.valueOf(LS.getId()));
        Club.setValue(LS.getClub().getId_club());
        id_cours.setValue(LS.getId_cours());

    }



    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClubService cs=new ClubService();
        List<Integer> l=cs.RecupereridClub();                //HEDHI COMBOBOX BL LES ATTRIBUT ESPACE LKOL
        Club.setItems(FXCollections.observableArrayList(l));

        List<Integer> lc=cs.RecupereridCours();                //HEDHI COMBOBOX BL LES ATTRIBUT ESPACE LKOL
        id_cours.setItems(FXCollections.observableArrayList(lc));


        ListCoursService listCoursServices=new ListCoursService();
        List<ListCours> listCours = listCoursServices.afficher();


        ObservableList<ListCours> ListCoursObservableList = FXCollections.observableArrayList(listCours);
        ListeCourd_TableView.setItems(ListCoursObservableList);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_club.setCellValueFactory(new PropertyValueFactory<>("club"));
        cours.setCellValueFactory(new PropertyValueFactory<>("id_cours"));



        ListeCourd_TableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List_Selected = newSelection.getId();
                init(newSelection);

            } else {
                List_Selected = -1;
            }
        });


    }





    @FXML
    void Ajout_ListeCours(ActionEvent event) throws IOException {
        ClubService cs = new ClubService();

        Club C=cs.rechercheClub(Club.getValue());

        ListCoursService LS=new ListCoursService();

        LS.ajouter(new ListCours(C,id_cours.getValue()) );

        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("ListCours ajout");
        alerte.setContentText("ListCours bien ajoutee");
        alerte.show();
        id_ListeCours.setText("");
        Club.setValue(null);
        id_cours.setValue(null);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCours.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) id_ListeCours.getScene().getWindow();
        currentStage.setScene(new Scene(root));

    }



    @FXML
    void DeleteListeCours(ActionEvent event) {
        if (List_Selected != -1) {
            ListCoursService LS = new ListCoursService();

            ListCours list_Selected = LS.rechercheListeCours(List_Selected);
            LS.supprimer(list_Selected);
            Alert alerte= new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("suppresion Espace");
            alerte.setContentText("Espace  supprime");
            alerte.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCours.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) ListeCourd_TableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void ModifyListeCours(ActionEvent event) {

    }

}

