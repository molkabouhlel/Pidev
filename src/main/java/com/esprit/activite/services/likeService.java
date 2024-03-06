package com.esprit.activite.services;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.likeanddislike;
import com.esprit.activite.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class likeService {
    private Connection connection;

    public likeService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(likeanddislike c) {
        String req = "INSERT INTO like_c (like_cours, dislike, id_cours) VALUES ('" + c.getLike_cours() + "', '" + c.getDislike() + "', '" + c.getId_cours().getId() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                c.setId_l(rs.getInt(1));
            }
            System.out.println("like and dislike ajoutée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }

    public void updateLikesDislikes(likeanddislike likeAndDislike) {
        try {
            String req = "UPDATE like_c SET like_cours = '" + likeAndDislike.getLike_cours() + "', dislike = '" + likeAndDislike.getDislike() + "' WHERE id_cours = " + likeAndDislike.getId_cours().getId() + ";";
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("like modifier !");
            // Affichez un message ou effectuez d'autres opérations après la mise à jour si nécessaire
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int getlikeCountsForCours(int idCours) {


        try {

                    // Requête SQL pour récupérer les likes et dislikes pour un cours donné
                    String req = "SELECT like_cours FROM like_c WHERE id_cours = " + idCours;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(req)) {

                // Parcours les résultats de la requête
                while (resultSet.next()) {
                    int likes = resultSet.getInt("like_cours");
                  //  int dislikes = resultSet.getInt("dislike");

                    // Ajoute les valeurs de like_cours et dislike à la liste de chaînes de caractères
                   return likes;
                }

            } catch (SQLException e) {
                e.printStackTrace(); // Gérez l'exception en fonction des besoins de votre application
            }

        } catch (Exception e) {
            e.printStackTrace(); // Gérez l'exception en fonction des besoins de votre application
        }

        return 0;
    }

    public int getDislikeCountsForCours(int idCours) {

        try {

            // Requête SQL pour récupérer les likes et dislikes pour un cours donné
            String req = "SELECT dislike FROM like_c WHERE id_cours = " + idCours;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(req)) {

                // Parcours les résultats de la requête
                while (resultSet.next()) {

                      int dislikes = resultSet.getInt("dislike");

                  return  dislikes;
                }

            } catch (SQLException e) {
                e.printStackTrace(); // Gérez l'exception en fonction des besoins de votre application
            }

        } catch (Exception e) {
            e.printStackTrace(); // Gérez l'exception en fonction des besoins de votre application
        }

        return 0;
    }


    public void supprimer(likeanddislike c) {
        String req = "DELETE FROM like_c WHERE id_l = " + c.getId_l() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("like supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isCoursLiked(int coursId) {
        String req = "SELECT * FROM like_c WHERE id_cours = " + coursId;
        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(req);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}



