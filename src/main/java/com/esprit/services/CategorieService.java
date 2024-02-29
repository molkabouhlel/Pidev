package com.esprit.services;
import com.esprit.models.Categorie;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.esprit.utils.DataSource;
public class CategorieService implements IService <Categorie> {
    private Connection connection;

    public CategorieService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Categorie categorie) {
        String req = "INSERT into categorie(nom_cat, description_cat) values ('" + categorie.getNom_cat() + "', '" + categorie.getDescr() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Categorie ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Categorie categorie) {
        String req = "UPDATE categorie set nom_cat = '" + categorie.getNom_cat() + "', description_cat = '" + categorie.getDescr() + "' where id_categorie = " + categorie.getId_cat() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Categorie categorie) {
        String req = "DELETE from categorie where id_categorie = " + categorie.getId_cat() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Categorie> afficher() {
        List<Categorie> categories = new ArrayList<>();

        String req = "SELECT * from categorie";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                categories.add(new Categorie(rs.getInt("id_categorie"), rs.getString("nom_cat"), rs.getString("description_cat")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }
    public List<String> affichernom() {
        List<String> categories = new ArrayList<>();

        String req = "SELECT * from categorie";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                categories.add(rs.getString("nom_cat"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }
    public List<String> Recuperernom() {
        List<String> C = new ArrayList<>();
        String req = "SELECT nom_cat from categorie";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                C.add(rs.getString("nom_cat"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }
    public List<Integer> Return_idCategorie() {
        List<Integer> E = new ArrayList<>();
        String req = "SELECT id_categorie from categorie ";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                E.add(rs.getInt("id_categorie"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return E;
    }
}
