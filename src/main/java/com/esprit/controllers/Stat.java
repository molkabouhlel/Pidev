package com.esprit.controllers;

import com.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stat {
    private Connection connection;

    public Stat() {
        connection = DataSource.getInstance().getConnection();
    }

    @FXML
    private PieChart pieChart;

    @FXML
    void afficherStatistique(ActionEvent event) {
        initialize(); // Mise à jour du graphique lorsqu'un événement est déclenché
    }

    private ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            // Utilisez un Statement pour exécuter la requête SQL
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT rate, COUNT(*) FROM programme GROUP BY rate")) {

                // Parcours des résultats et ajout des données au PieChart
                while (resultSet.next()) {
                    float rate = resultSet.getFloat("rate");
                    int nombreEvenements = resultSet.getInt(2); // Vous pouvez également utiliser le nom de la colonne "COUNT(*)"

                    PieChart.Data slice = new PieChart.Data(rate + " - rate ", nombreEvenements);
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
}
