package com.esprit.services;


import com.esprit.models.Club;
import com.esprit.models.Espace;
import com.esprit.models.ListCours;
import com.esprit.services.EspaceService;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;
public class ListCoursService implements IService<ListCours>{

    private Connection connection;

    public ListCoursService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
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

    @Override
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

    @Override
    public void supprimer(ListCours L) {
        String req = "DELETE from liste_cours where id = " + L.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("cours supprmié dans liste!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public ListCours rechercheListeCours(int id) {
        ListCours ListCours = new ListCours();

        String req = "SELECT * FROM liste_cours WHERE id = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                ListCours.setId(rs.getInt("id"));

                ClubService cs=new ClubService();;
                Club C =cs.rechercheClub(rs.getInt("id_club"));

                ListCours.setClub(C);
                ListCours.setId_cours(rs.getInt("id_cours"));

                // Set other properties as needed
            }
        } catch (SQLException eq) {
            System.out.println(eq.getMessage());
        }
        return ListCours;
    }



    @Override
    public List<ListCours> afficher() {
        Club c=new Club();

        List<ListCours> L = new ArrayList<>();
        String req = "SELECT * from liste_cours";
        ;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ClubService cs=new ClubService();
                Club club = cs.rechercheClub(rs.getInt("id_club"));
                if (club != null) {
                    L.add(new ListCours(rs.getInt("id"), club, rs.getInt("id_cours")));
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return L;
    }





}
