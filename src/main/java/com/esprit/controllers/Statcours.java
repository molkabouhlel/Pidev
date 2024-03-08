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

public class Statcours {
    private Connection connection;

    public Statcours() {
        connection = DataSource.getInstance().getConnection();
    }

    @FXML
    private PieChart pieChart;

    @FXML
    void afficherStatistique(ActionEvent event) {

    }

    public ObservableList<PieChart.Data> generatePieChartForTypeEvEvents() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try {
            if (connection != null && !connection.isClosed()) {
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT te.typecours, COUNT(*) as count " +
                             "FROM cours e " +
                             "INNER JOIN typec te ON e.id_typec = te.idtypec " +
                             "GROUP BY te.idtypec")) {

                    int totalCount = 0;

                    while (resultSet.next()) {
                        String typeEv = resultSet.getString("typecours");
                        int count = resultSet.getInt("count");

                        totalCount += count;

                        PieChart.Data slice = new PieChart.Data(typeEv, count);
                        pieChartData.add(slice);
                    }

                    // Calculer le pourcentage pour chaque tranche
                    for (PieChart.Data slice : pieChartData) {
                        double percentage = (slice.getPieValue() / totalCount) * 100;
                        slice.setName(slice.getName() + " - " + String.format("%.2f%%", percentage));
                    }
                }
            } else {
                System.out.println("La connexion à la base de données n'est pas établie correctement.");
                // Affichez un message à l'utilisateur ou journalisez l'erreur.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Affichez un message à l'utilisateur ou journalisez l'erreur.
        }

        return pieChartData;
    }

    // Méthode d'initialisation du contrôleur
    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = generatePieChartForTypeEvEvents();
        pieChart.setData(pieChartData);
    }
}
