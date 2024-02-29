package com.esprit.services;

import com.esprit.models.Personne;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class PersonneService implements IService<Personne> {

    private Connection connection;

    public PersonneService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Personne personne) {
        String req = "INSERT into personne(nom, prenom) values ('" + personne.getNom() + "', '" + personne.getPrenom() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Personne personne) {
        String req = "UPDATE personne set nom = '" + personne.getNom() + "', prenom = '" + personne.getPrenom() + "' where id = " + personne.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Personne personne) {
        String req = "DELETE from personne where id = " + personne.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Personne> afficher() {
        List<Personne> personnes = new ArrayList<>();

        String req = "SELECT * from personne";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                personnes.add(new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return personnes;
    }
}
