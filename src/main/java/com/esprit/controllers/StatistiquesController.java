package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class StatistiquesController {

    @FXML
    private PieChart pieChart;

    public void initData(PieChart.Data[] pieChartData) {
        // Ajouter les données au PieChart
        pieChart.getData().addAll(pieChartData);
    }
}
