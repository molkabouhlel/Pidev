package com.esprit.services;

import com.esprit.models.Club;
import com.esprit.utils.DataSource;


import java.sql.*;
import java.util.*;

public class ClubService implements IService<Club> {

    private Connection connection;

    public ClubService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Club C) {
        String req = "INSERT into club( nom_club,  description_club,  capacite,  id_cours,  id_espace,  id_usr) values ('" + C.getNom_club() + "', '" + C.getDescription_club() + "' , '" + C.getCapacite() + "'   , '" +   C.getId_cours() + "'  , '" + C.getId_espace() + "' , '" + C.getId_usr() + "');";
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
        String req = "UPDATE club set nom_club = '" + C.getNom_club() + "', description_club = '"  + C.getDescription_club() + "' , capacite = '" + C.getCapacite() + "', id_cours = '" + C.getId_cours() + "', id_espace = '" + C.getId_espace() + "', id_usr = '" + C.getId_usr() + "' where id_club = " + C.getId_club() + ";";
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
                C.add(new Club(rs.getInt("id_club"), rs.getString("nom_club"), rs.getString("description_club") , rs.getInt("capacite") , rs.getInt("id_cours"),rs.getInt("id_espace"),rs.getInt("id_usr")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }
}

