package com.esprit.controllers;
import com.esprit.models.MedianCalculator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;

public class Afficherbarchart {

    @FXML
    private BarChart<String, Number> barChart;
    public void initialize() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Produit");
        yAxis.setLabel("Quantité");

        // Configuration de l'axe X pour afficher les catégories en ordre alphabétique
        xAxis.setGapStartAndEnd(false);
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList("Produit 1", "Produit 2", "Produit 3", "Produit 4", "Produit 5")));

        // Configuration du BarChart
        barChart.setTitle("Quantités des Produits");
        barChart.setLegendVisible(false);
        barChart.setAnimated(false); // Désactiver l'animation pour une meilleure performance

        // Appel de la classe MedianCalculator pour récupérer les données
        MedianCalculator calculator = new MedianCalculator();

        // Récupération des quantités et création de la série de données
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Quantités");
        for (int i = 0; i < calculator.getQuantities().size(); i++) {
            series.getData().add(new XYChart.Data<>("Produit " + (i + 1), calculator.getQuantities().get(i)));
        }

        // Ajout de la série au BarChart
        barChart.getData().add(series);

        // Calcul de la médiane
        double median = calculator.calculateMedian();

        // Création d'une série de données pour afficher la médiane
        XYChart.Series<String, Number> medianSeries = new XYChart.Series<>();
        medianSeries.getData().add(new XYChart.Data<>("Médiane", median));
        // Ajout de la série de médiane au BarChart
        barChart.getData().add(medianSeries);
    }
        @FXML
        void retur(ActionEvent event) {
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





   /* public void initialize() {
        // Création des axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Produit");
        yAxis.setLabel("Quantité");

        // Configuration de l'axe X pour afficher les catégories en ordre alphabétique
        xAxis.setGapStartAndEnd(false);
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList("Produit 1", "Produit 2", "Produit 3", "Produit 4", "Produit 5")));

        // Configuration du BarChart
        barChart.setTitle("Quantités des Produits");
        barChart.setLegendVisible(false);
        barChart.setAnimated(false); // Désactiver l'animation pour une meilleure performance

        // Appel de la classe MedianCalculator pour récupérer les données
        MedianCalculator calculator = new MedianCalculator();

        // Récupération des quantités et création de la série de données
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Quantités");
        for (int i = 0; i < calculator.getQuantities().size(); i++) {
            series.getData().add(new XYChart.Data<>("Produit " + (i + 1), calculator.getQuantities().get(i)));
        }

        // Ajout de la série au BarChart
        barChart.getData().add(series);
    }
    @FXML
    void retur(ActionEvent event) {
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
    }*/
}

