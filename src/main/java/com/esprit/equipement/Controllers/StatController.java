package com.esprit.equipement.Controllers;

import com.esprit.equipement.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatController {
    private Connection connection;

    public StatController() {
        connection = DataSource.getInstance().getConnection();
    }

    @FXML
    private PieChart pieChart;
    @FXML
    private Button refresh;



    private ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT nom_eq, quantite_dispo, COUNT(*) FROM equipement GROUP BY nom_eq, quantite_dispo")) {

                while (resultSet.next()) {
                    String nomEq = resultSet.getString("nom_eq");
                    int quantite_dispo = resultSet.getInt("quantite_dispo");
                    int nombreEvenements = resultSet.getInt(3); // Vous pouvez également utiliser le nom de la colonne "COUNT(*)"

                    PieChart.Data slice = new PieChart.Data(nomEq + " - quantite " + quantite_dispo, nombreEvenements);
                    pieChartData.add(slice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }
    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = contc();
        pieChart.setData(pieChartData);
    }
    @FXML
    void refresh(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Principale.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}}
