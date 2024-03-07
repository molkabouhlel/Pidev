package com.esprit.controllers;
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

public class Affichagestat {
    @FXML
    private PieChart statscore;

    public void initialize() {
        ScoreService p = new ScoreService();
        Map<Integer, Double> scoresMap = p.afficherscore();
        List<score> scoreList = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : scoresMap.entrySet()) {
            int idProduit = entry.getKey();
            System.out.println(idProduit);
            double moyenneNote = entry.getValue();
            System.out.println(moyenneNote);
            scoreList.add(new score(p.recherchecat(idProduit), moyenneNote));
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        // this.statscore.setItems(pieChartData);
        for (score s : scoreList) {
            statscore.getData().add(new PieChart.Data(s.getId_produit().getDesc(), s.getNote()));

        }

    }
}
