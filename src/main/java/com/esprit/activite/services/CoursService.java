package com.esprit.activite.services;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.club;
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
        String req = "INSERT into cours (nom, description, imagec, duree, idcoach, idclub, id_typec) values ('" + c.getNom() + "',  '" + c.getDescription() + "','" + c.getImagec() + "', '" + c.getDuree() + "','" + c.getIdcoach() + "','" + c.getIdclub().getId_club() + "', '" + c.getId_typec().getIdtypec() +"');";
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
        String req = "UPDATE cours set nom = '" + c.getNom() + "', description = '" + c.getDescription() + "', imagec = '" + c.getImagec() + "', duree = '" + c.getDuree() + "', idcoach = '" + c.getIdcoach() + "', idclub='" + c.getIdclub().getId_club() + "',id_typec = '" + c.getId_typec().getIdtypec() + "' where id = " + c.getId() + ";";
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
                club  id_club =rechercheclubaffich(rs.getInt("idclub"));
                c.add(new Cours(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("imagec"), rs.getTime("duree"), rs.getInt("idcoach"), id_club,id_typec));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }
/////////////////
public typec recherchetypec (int id_typec) {
    typec evs = null;
    String req = "SELECT typecours FROM typec WHERE idtypec = " + id_typec;
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            evs = new typec();
           // evs.setIdtypec(rs.getInt("idtypec"));
            evs.setTypecours(rs.getString("typecours"));


        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return evs;
}
    public club rechercheclub (int id_club) {
        club evs = null;
        String req = "SELECT nom_club FROM club WHERE id_club = " + id_club;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                evs = new club();
               //  evs.setId_club(rs.getInt("idclub"));
                evs.setId_club(rs.getInt("id_club"));
                evs.setNom_club(rs.getString("nom_club"));


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
                club id_club =rechercheclub(rs.getInt("idclub"));
                C.setId(rs.getInt("id"));
                C.setNom(rs.getString("nom"));
                C.setDescription(rs.getString("description"));
                C.setDuree(rs.getTime("duree"));
                C.setIdcoach(rs.getInt("idcoach"));
                C.setIdclub(id_club);
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
  /*  public List<Integer> rechercheclub() {
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
    }*/
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
/////////////////sans id /////////////////////
public List<String> listcategorie() {
    List<typec> catList = listedecatdenom();
    List<String> nomCoursList = new ArrayList<>();

    for (typec categorie : catList) {
        nomCoursList.add(categorie.getTypecours());
    }

    return nomCoursList;
}
    public typec rechercherCatParNom(String typecours) {
        String req = "SELECT * FROM typec WHERE typecours = '" + typecours + "'";
        typec cat = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                cat=new typec(rs.getInt("idtypec"), rs.getString("typecours"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return cat;
    }
    public List<typec> listedecatdenom() {
        List<typec> catList = new ArrayList<>();
        String req = "SELECT typecours FROM typec";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                typec c = new typec();
                c.setTypecours(rs.getString("typecours"));
                catList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return catList;
    }
    //club
    public List<String> listclub() {
        List<club> catList = listedeclubdenom();
        List<String> nomCoursList = new ArrayList<>();

        for (club c : catList) {
            nomCoursList.add(c.getNom_club());
        }

        return nomCoursList;
    }
    public club rechercherClubparnom(String nom_club) {
        String req = "SELECT * FROM club WHERE nom_club = '" + nom_club + "'";
        club cl = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                cl=new club(rs.getInt("id_club"),rs.getString("nom_club"),rs.getString("adresse_club"),rs.getString( "description_club"),rs.getString("image_club"),rs.getTime("temp_ouverture"),rs.getInt("id_espace"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return cl;
    }
    public List<club> listedeclubdenom() {
        List<club> catList = new ArrayList<>();
        String req = "SELECT nom_club FROM club";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                club c = new club();
                c.setNom_club(rs.getString("nom_club"));
                catList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return catList;
    }
//
public club rechercheclubaffich (int id_club) {
    club evs = null;
    String req = "SELECT *FROM club WHERE id_club = " + id_club;
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            evs = new club();
            //  evs.setId_club(rs.getInt("idclub"));
            evs.setId_club(rs.getInt("id_club"));
            evs.setNom_club(rs.getString("nom_club"));



        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return evs;
}
//qr
public String getDescriptionAndDureeById(int coursId) {
    String req = "SELECT description, duree FROM cours WHERE id = " + coursId;
    try (Statement statement = connection.createStatement();
         ResultSet rs = statement.executeQuery(req)) {

        if (rs.next()) {
            String description = rs.getString("description");
            Time duree = rs.getTime("duree");

            // Retourne la description et la durée formatées
            return "Description: " + description + "\nDurée: " + duree.toString();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}


}