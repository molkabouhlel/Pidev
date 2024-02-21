package com.esprit.services;

import com.esprit.models.Club;
import com.esprit.models.Espace;

import com.esprit.utils.DataSource;


import java.sql.*;
import java.util.*;

public class ClubService implements IService<Club>{

    private Connection connection;

    public ClubService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Club C) {
        String req = "INSERT into club( nom_club,adresse_club,description_club,image_club,temp_ouverture,id_espace) values ('" + C.getNom_club() + "', '" + C.getAdresse_club() + "', '" + C.getDescription_club() + "' , '" + C.getImage_club() + "'   , '" +   C.getTemp_ouverture() + "'  , '" + C.getEspace().getId_espace() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("club ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Club C) {
        String req = "UPDATE club set nom_club = '" + C.getNom_club() + "', adresse_club = '"  + C.getAdresse_club() + "' , description_club = '"  + C.getDescription_club() + "' , image_club = '" + C.getImage_club() + "', temp_ouverture = '" + C.getTemp_ouverture() + "', id_espace = '" + C.getEspace().getId_espace() + "' where id_club = '" + C.getId_club() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Club modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Club C) {
        String req = "DELETE from club where id_club = " + C.getId_club() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("club supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<Club> afficher() {
        List<Club> C = new ArrayList<>();
        String req = "SELECT * from club";
                ;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                EspaceService es=new EspaceService();
                Espace espace = es.rechercheEspace(rs.getInt("id_espace"));
                if (espace != null) {
                    C.add(new Club(rs.getInt("id_club"), rs.getString("nom_club"), rs.getString("adresse_club"), rs.getString("description_club"), rs.getString("image_club"), rs.getTime("temp_ouverture"), espace));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }


    public List<Integer> RecupereridClub() {
        List<Integer> C = new ArrayList<>();
        String req = "SELECT id_club from club";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                C.add(rs.getInt("id_club"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }
    public List<Integer> RecupereridCours() {
        List<Integer> C = new ArrayList<>();
        String req = "SELECT id from cours";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                    C.add(rs.getInt("id"));
                }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }



    public Club rechercheClub(int id) {
        Club club = null;
        Espace e = new Espace();
        String req = "SELECT * FROM club WHERE id_club = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                club = new Club();
                club.setId_club(rs.getInt("id_club"));
                club.setNom_club(rs.getString("nom_club"));
                club.setAdresse_club(rs.getString("adresse_club"));
                club.setDescription_club(rs.getString("description_club"));
                club.setImage_club(rs.getString("image_club"));
                club.setTemp_ouverture(rs.getTime("temp_ouverture"));

                EspaceService es=new EspaceService();;
                Espace espace = es.rechercheEspace(rs.getInt("id_espace"));
                club.setEspace(espace);
                // Set other properties as needed
            }
        } catch (SQLException eq) {
            System.out.println(eq.getMessage());
        }
        return club;
    }

}

