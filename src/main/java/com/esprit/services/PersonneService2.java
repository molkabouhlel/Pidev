package com.esprit.services;

import com.esprit.models.Personne;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneService2 implements IService<Personne> {

    private Connection connection;

    public PersonneService2() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Personne personne) {
        String req = "INSERT into personne(nom, prenom) values (?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(2, personne.getPrenom());
            pst.setString(1, personne.getNom());
            pst.executeUpdate();
            System.out.println("Personne ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Personne personne) {
        String req = "UPDATE personne set nom = ?, prenom = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(3, personne.getId());
            pst.setString(1, personne.getNom());
            pst.setString(2, personne.getPrenom());
            pst.executeUpdate();
            System.out.println("Personne modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Personne personne) {
        String req = "DELETE from personne where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, personne.getId());
            pst.executeUpdate();
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
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                personnes.add(new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return personnes;
    }
}
