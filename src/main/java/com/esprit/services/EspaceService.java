package com.esprit.services;

import com.esprit.models.Club;
import com.esprit.models.Espace;
import com.esprit.utils.DataSource;
import java.sql.Time;

import java.sql.*;
import java.util.*;
public class EspaceService implements IService<Espace> {

        private Connection connection;

        public EspaceService() {
            connection = DataSource.getInstance().getConnection();
        }
        @Override
        public void ajouter(Espace E) {
            String req = "INSERT into espace(nom_espace,description_espace,heure_debut,heure_fin) values ( '" + E.getNom_espace() + "' , '" + E.getDescription_espace() + "'   , '" +   E.getHeure_debut() + "'  , '" + E.getHeure_fin() + "');";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("Espace ajoutée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void modifier(Espace E) {
            String req = "UPDATE espace set nom_espace = '" + E.getNom_espace() + "', description_espace = '"  + E.getDescription_espace() + "' , heure_debut = '" + E.getHeure_debut() + "', heure_fin = '" + E.getHeure_fin() + "' where id_espace = " + E.getId_espace() + ";";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("Espace modifié !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void supprimer(Espace E) {
            String req = "DELETE from espace where id_espace = " + E.getId_espace() + ";";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("espace supprmié !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public List<Espace> afficher() {
            List<Espace> E = new ArrayList<>();
            String req = "SELECT * from espace ";
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {
                    E.add(new Espace(rs.getInt("id_espace"), rs.getString("nom_espace"), rs.getString("description_espace") , rs.getTime("heure_debut") , rs.getTime("heure_fin")));

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return E;
        }


    public Espace rechercheEspace(int id) {
        Espace Espace = null;
        String req = "SELECT * FROM espace WHERE id_espace = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                Espace = new Espace();
                Espace.setId_espace(rs.getInt("id_espace"));
                Espace.setNom_espace(rs.getString("nom_espace"));
                Espace.setDescription_espace(rs.getString("description_espace"));
                Espace.setHeure_debut(rs.getTime("heure_debut"));
                Espace.setHeure_fin(rs.getTime("heure_fin"));
                // Set other properties as needed
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Espace;
    }


    public Espace rechercheEspacenom(String nom_espace) {
        Espace Espace = null;
        String req = "SELECT * FROM espace WHERE nom_espace = '" + nom_espace + "'";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                Espace = new Espace();
                Espace.setId_espace(rs.getInt("id_espace"));
                Espace.setNom_espace(rs.getString("nom_espace"));
                Espace.setDescription_espace(rs.getString("description_espace"));
                Espace.setHeure_debut(rs.getTime("heure_debut"));
                Espace.setHeure_fin(rs.getTime("heure_fin"));
                // Set other properties as needed
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Espace;
    }


    public List<String> affichernomEspace() {
        List<String> E = new ArrayList<>();
        String req = "SELECT nom_espace from espace ";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                E.add(rs.getString("nom_espace"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return E;
    }




    public List<Integer>  Return_idEspace() {
        List<Integer> E = new ArrayList<>();
        String req = "SELECT id_espace from espace ";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                E.add(rs.getInt("id_espace"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return E;
    }



}






