package com.esprit.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoyenneScores {
    private Map<Integer, List<Double>> scoresProduits = new HashMap<>();

    public void ajouterScore(score score) {
        Produit id_produit = score.getId_produit();
        double nouvelle_note = score.getNote();

        List<Double> scores_produit = scoresProduits.getOrDefault(id_produit.getId(), new ArrayList<>());
        scores_produit.add(nouvelle_note);
        scoresProduits.put(id_produit.getId(), scores_produit);

        double moyenne_scores = calculerMoyenne(scores_produit);

        System.out.println("Moyenne des scores pour le produit " + id_produit + " : " + moyenne_scores);
    }

    private double calculerMoyenne(List<Double> scores) {
        int somme_notes = 0;
        for (double score : scores) {
            somme_notes += score;
        }
        return (double) somme_notes / scores.size();
    }
    public double getMoyenneScoreProduit(int idProduit) {
        if (scoresProduits.containsKey(idProduit)) {
            List<Double> scores = scoresProduits.get(idProduit);
            return calculerMoyenne(scores);
        } else {
            return 0.0; // Retourner 0 si aucun score n'est trouvé pour ce produit
        }
    }
}
