package com.esprit.activite.services;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.*;

public class CoursService implements Iservice <Cours>{
    private Connection connection;
private typec t =new typec();
    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Cours c) {
        String req = "INSERT into cours (nom, description, imagec, duree, idcoach, idclub, id_typec) values ('" + c.getNom() + "',  '" + c.getDescription() + "','" + c.getImagec() + "', '" + c.getDuree() + "','" + c.getIdcoach() + "','" + c.getIdclub() + "', '" + c.getId_typec().getIdtypec() +"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("coooouuurrr ajoutée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }

    }


    @Override
    public void modifier(Cours c) {
        String req = "UPDATE cours set nom = '" + c.getNom() + "', description = '" + c.getDescription() + "', imagec = '" + c.getImagec() + "', duree = '" + c.getDuree() + "', idcoach = '" + c.getIdcoach() + "', idclub='" + c.getIdclub() + "',id_typec = '" + c.getId_typec().getIdtypec() + "' where id = " + c.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("couur modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Cours c) {
        String req = "DELETE from cours where id = " + c.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("couur supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Cours> afficher() {

        List<Cours> c = new ArrayList<>();

        String req = "SELECT * from cours";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                typec id_typec = recherchetypec(rs.getInt("id_typec"));
                c.add(new Cours(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("imagec"), rs.getTime("duree"), rs.getInt("idcoach"), rs.getInt("idclub"),id_typec));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }
/////////////////
public typec recherchetypec (int id_typec) {
    typec evs = null;
    String req = "SELECT * FROM typec WHERE idtypec = " + id_typec;
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            evs = new typec();
            evs.setIdtypec(rs.getInt("idtypec"));
            evs.setTypecours(rs.getString("typecours"));


        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return evs;
}
    public Cours rechercheCours(int id) {
        Cours C = null;
        String req = "SELECT * FROM cours WHERE id= " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                C = new Cours();
                typec id_typec = recherchetypec(rs.getInt("id_typec"));
                C.setId(rs.getInt("id"));
                C.setNom(rs.getString("nom"));
                C.setDescription(rs.getString("description"));
                C.setDuree(rs.getTime("duree"));
                C.setIdcoach(rs.getInt("idcoach"));
                C.setIdclub(rs.getInt("idclub"));
                C.setId_typec(id_typec);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }
////
public List<Integer> recherchecoach() {
    List<Integer> idC = new ArrayList<>();
    String req = "SELECT cin FROM usr";
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            int idcoach = rs.getInt("cin");
            idC.add(idcoach);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return idC;
}
    public List<Integer> rechercheclub() {
        List<Integer> idCl = new ArrayList<>();
        String req = "SELECT id_club,nom_club FROM club";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_club = rs.getInt("id_club");
                String nomclub =rs.getString("nom_club");
                idCl.add(id_club);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return idCl;
    }
//stat
public ObservableList<PieChart.Data> contc() {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    try {
        // Utilisez un Statement pour exécuter la requête SQL
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT nom, duree, COUNT(*) FROM cours GROUP BY nom, duree")) {

            // Parcours des résultats et ajout des données au PieChart
            while (resultSet.next()) {
                String nomEvenement = resultSet.getString("nom_ev");
                int capacite_max = resultSet.getInt("capacite_max");
                int nombreEvenements = resultSet.getInt(3); // Vous pouvez également utiliser le nom de la colonne "COUNT(*)"

                PieChart.Data slice = new PieChart.Data(nomEvenement + " - Capacité " + capacite_max, nombreEvenements);
                pieChartData.add(slice);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return pieChartData;
}
}
