package com.esprit.models;
import com.esprit.utils.DataSource;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedianCalculator {
    private static Connection connection;
    public MedianCalculator() {
        connection = DataSource.getInstance().getConnection();
    }
   /* public static void main(String[] args)
    {
        ResultSet rs = null;
        List<Integer> qunatites = new ArrayList<>();
        String req = "SELECT quantite_produit FROM produit";
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(req);
            while (rs.next()) {
                qunatites.add(rs.getInt("quantite_produit"));
            }
            // Convertissez les quantités en tableau d'entiers
            int[] quantitesArray = qunatites.stream().mapToInt(Integer::intValue).toArray();
            // Triez les quantités
            Arrays.sort(quantitesArray);
            // Créez un objet DescriptiveStatistics
            DescriptiveStatistics statsQuantites = new DescriptiveStatistics();
            // Ajoutez les quantités triées à DescriptiveStatistics
            for (int q : quantitesArray) {
                statsQuantites.addValue(q);
            }
            // Calcul de la médiane
            double medianeQuantites = statsQuantites.getPercentile(50);
            // Affichage du résultat
            System.out.println("La médiane des quantités est : " + medianeQuantites);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }*/
   public List<Integer> getQuantities() {
       List<Integer> quantities = new ArrayList<>();
       ResultSet rs = null;
       String req = "SELECT quantite_produit FROM produit";
       try {
           Statement st = connection.createStatement();
           rs = st.executeQuery(req);
           while (rs.next()) {
               quantities.add(rs.getInt("quantite_produit"));
           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       return quantities;
   }

    public double calculateMedian() {
        List<Integer> quantities = getQuantities();

        // Convertissez les quantités en tableau d'entiers
        int[] quantitiesArray = quantities.stream().mapToInt(Integer::intValue).toArray();

        // Triez les quantités
        Arrays.sort(quantitiesArray);

        // Créez un objet DescriptiveStatistics
        DescriptiveStatistics statsQuantites = new DescriptiveStatistics();

        // Ajoutez les quantités triées à DescriptiveStatistics
        for (int q : quantitiesArray) {
            statsQuantites.addValue(q);
        }

        // Calcul de la médiane
        return statsQuantites.getPercentile(50);
    }
    /*public ResultSet listQuant() {
        ResultSet rs = null;

        String req = "SELECT quantite_produit FROM produit";
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }*/

}
