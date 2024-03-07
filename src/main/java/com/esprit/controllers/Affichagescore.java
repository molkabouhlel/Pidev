package com.esprit.controllers;
import com.esprit.models.Categorie;
import com.esprit.models.score;
import com.esprit.services.ScoreService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.esprit.models.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import com.esprit.models.Produit;
import com.esprit.services.ProduitService;
import com.esprit.services.CategorieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import com.esprit.models.score;
import com.esprit.services.ScoreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class Affichagescore {
        @FXML
        private TableColumn<score, Double> tf_note;
    @FXML
    private PieChart piechartnote;
        @FXML
        private TableColumn<score, String> tf_prd;
    @FXML
    private TableView<score> tableviewsc;
    public Affichagescore(){ }
    @FXML
    void initialize() {
        ScoreService p = new ScoreService();
        Map<Integer, Double> scoresMap = p.afficherscore();
        List<score> scoreList = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : scoresMap.entrySet()) {
            int idProduit = entry.getKey();
            System.out.println(idProduit);
            double moyenneNote = entry.getValue();
            System.out.println(moyenneNote);
            scoreList.add(new score(p.recherchecat(idProduit), moyenneNote));
            //System.out.println(new score(idProduit, moyenneNote));
        }
        ObservableList<score> observableList = FXCollections.observableList(scoreList);
        this.tableviewsc.setItems(observableList);

        tf_prd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId_produit().getDesc()));


        this.tf_note.setCellValueFactory(new PropertyValueFactory("note"));
        this.tf_prd.setCellFactory(TextFieldTableCell.forTableColumn());
        this.tf_note.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tableviewsc.setEditable(true);
        ScoreService pp = new ScoreService();
        Map<Integer, Double> scoresMapp = pp.afficherscore();
        List<score> scoreListt = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : scoresMapp.entrySet()) {
            int idProduit = entry.getKey();
            System.out.println(idProduit);
            double moyenneNote = entry.getValue();
            System.out.println(moyenneNote);
            scoreListt.add(new score(pp.recherchecat(idProduit), moyenneNote));
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        // this.statscore.setItems(pieChartData);
        for (score s : scoreListt) {
            piechartnote.getData().add(new PieChart.Data(s.getId_produit().getDesc(), s.getNote()));

        }
    }
    @FXML
    void retret(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage
        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffichageProduit.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }
}


