package com.esprit.activite.Controllers;

import com.esprit.activite.utils.DataSource;
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

    }

    private ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT nom_ev, capacite_max, COUNT(*) FROM evenement GROUP BY nom_ev, capacite_max")) {


                while (resultSet.next()) {
                    String nomEvenement = resultSet.getString("nom_ev");
                    int capacite_max = resultSet.getInt("capacite_max");
                    int nombreEvenements = resultSet.getInt(3);

                    PieChart.Data slice = new PieChart.Data(nomEvenement + " - Capacité " + capacite_max, nombreEvenements);
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

