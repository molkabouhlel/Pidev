package com.esprit.services;
import com.esprit.models.Categorie;
import com.esprit.models.MoyenneScores;
import com.esprit.models.Produit;
import com.esprit.models.score;

import java.sql.*;
import java.util.*;

import com.esprit.utils.DataSource;
public class ScoreService implements IService<score>{
    private Connection connection = DataSource.getInstance().getConnection();
    public ScoreService() {
    }
    public void ajouter(score score) {
        MoyenneScores MS = new MoyenneScores();
        String req = "INSERT into score (id_produit,note) values ('"  + score.getId_produit().getId() + "','" + score.getNote()+ "');";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("produit ajoutée !");
            MS.ajouterScore(score);
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

    public void modifier(score score) {

        String req = "UPDATE score set id_produit = '" + score.getId_produit().getId() + "', note = " + score.getNote() +  ";";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("produit modifiée !");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

    public void supprimer(score score) {
        String req = "DELETE from score where id_score = " + score.getId_score() + ";";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("produit supprmiée !");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

    public List<score> afficher() {
        List<score> scores = new ArrayList();
        String req = "SELECT * from score";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while(rs.next()) {
                Produit id_produit = this.recherchecat(rs.getInt("id_produit"));

                scores.add(new score(rs.getInt("id_score"),id_produit,  rs.getDouble("note")));
            }
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }

        return scores;
    }
    public Map<Integer, Double> afficherscore() {
        Map<Integer, Double> Ma = new HashMap<>();
        //String req = "SELECT * from score";
        String req = "SELECT id_produit, AVG(note) AS moyenne_note FROM score GROUP BY id_produit";
        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while(rs.next()) {
              //  Produit id_produit = this.recherchecat(rs.getInt("id_produit"));

                Ma.put(rs.getInt("id_produit"),rs.getDouble("moyenne_note"));
            }
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }

        return Ma;
    }
    public Produit recherchecat(int id_produit) {
        Produit evs = null;
        String req = "SELECT * FROM produit WHERE id_produit = " + id_produit;

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                evs = new Produit();
                evs.setId(rs.getInt("id_produit"));
                evs.setDesc(rs.getString("description_produit"));
                evs.setPrix(rs.getFloat("prix"));
                evs.setQuant(rs.getInt("quantite_produit"));
                evs.setMarque(rs.getString("marque"));
              //  evs.setId_categorie(rs.getInt("id_categorie"));
                evs.setImage(rs.getString("image"));
                evs.setSku(rs.getString("sku"));
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return evs;
    }

}
