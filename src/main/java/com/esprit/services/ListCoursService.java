package com.esprit.services;


import com.esprit.models.Club;
import com.esprit.models.Espace;
import com.esprit.models.ListCours;
import com.esprit.services.EspaceService;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;
public class ListCoursService {

    private Connection connection;

    public ListCoursService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(ListCours L) {
        String req = "INSERT into liste_cours( id_club,id_cours) values ('" + L.getClub().getId_club() + "', '" + L.getId_cours() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("cours ajoutée dans liste!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void modifier(ListCours L) {
        //Club c=new Club(8);
        String req = "UPDATE liste_cours set id_club = '" + L.getClub().getId_club() + "', id_cours = '"  + L.getId_cours() + "'  where id = '" + L.getId() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("cours modifie dans liste!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(ListCours L) {
        String req = "DELETE from liste_cours where id = " +L.getId()+ ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println(req);
            System.out.println(req);
            System.out.println("cours supprmié dans liste!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ListCours> RecupererListeCours(int id) {
        List<ListCours> L = new ArrayList<>();
        String req = "SELECT * FROM liste_cours WHERE id_club = " + id;

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ClubService cs=new ClubService();
                Club C =cs.rechercheClub(rs.getInt("id_club"));
                L.add(new ListCours(rs.getInt("id"),C,rs.getInt("id_cours")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return L;
    }


    public ListCours rechercheListeCours(int id) {
        ListCours L = null;
        String req = "SELECT * FROM liste_cours WHERE id = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                L = new ListCours();
                L.setId(rs.getInt("id"));

                ClubService cs=new ClubService();;
                Club C = cs.rechercheClub(rs.getInt("id_club"));
                L.setClub(C);

                L.setId_cours(rs.getInt("id_cours"));

                // Set other properties as needed
            }
        } catch (SQLException eq) {
            System.out.println(eq.getMessage());
        }
        return L;
    }



    public List<ListCours> RechercheIdCours(int id) {

        List<ListCours> L = new ArrayList<>();
        String req = "SELECT id_cours FROM liste_cours where id_club = " + id;


        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                    L.add(new ListCours(rs.getInt("id_cours")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return L;

    }





}
