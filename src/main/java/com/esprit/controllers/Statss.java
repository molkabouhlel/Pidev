package com.esprit.controllers;

import com.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Statss {
      private Connection connection;

    @FXML
    void retour(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/backadmin.fxml")));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }
    }
    public Statss() {
        connection = DataSource.getInstance().getConnection();
    }

    @FXML
    private PieChart pieChart;

    private ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        // Utilize a Statement to execute the SQL query
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT role, COUNT(*) FROM usr GROUP BY role")) {

            // Traverse the results and add data to the PieChart
            while (resultSet.next()) {
                String role = resultSet.getString("role");
                int nombreEvenements = resultSet.getInt(2); // Use column index 2 for the count column

                PieChart.Data slice = new PieChart.Data(role, nombreEvenements);
                pieChartData.add(slice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pieChartData;
    }

    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = contc();
        pieChart.setData(pieChartData);
    }
}
