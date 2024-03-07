package com.esprit.services;
import com.esprit.models.Produit;
import com.esprit.models.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.esprit.utils.DataSource;
public class ProduitService implements IService<Produit> {
    private Connection connection = DataSource.getInstance().getConnection();

    public ProduitService() {
    }

    public void ajouter(Produit produit) {
        String var10000 = produit.getDesc();
        String req = "INSERT into produit (description_produit,prix, quantite_produit, marque, id_categorie, image, sku) values ('" + var10000 + "',  '" + produit.getPrix() + "','" + produit.getQuant() + "', '" + produit.getMarque() + "','" + produit.getId_categorie().getId_cat() + "','"+ produit.getImage() + "','"+ produit.getSku() + "');";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("produit ajoutée !");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

    public void modifier(Produit produit) {
        String var10000 = produit.getDesc();
        String req = "UPDATE produit set description_produit = '" + var10000 + "', prix = '" + produit.getPrix() + "', quantite_produit = '" + produit.getQuant() + "', marque = '" + produit.getMarque() + "', id_categorie = '" + produit.getId_categorie().getId_cat()+ "', image = '" + produit.getImage() +"', sku = '" + produit.getSku() + "' where id_produit = " + produit.getId() + ";";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("produit modifiée !");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

    public void supprimer(Produit produit) {
        String req = "DELETE from produit where id_produit = " + produit.getId() + ";";

        try {
            Statement st = this.connection.createStatement();
            st.executeUpdate(req);
            System.out.println("produit supprmiée !");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

    public List<Produit> afficher() {
        List<Produit> produits = new ArrayList();
        String req = "SELECT * from produit";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while(rs.next()) {
                Categorie id_categorie = this.recherchecat(rs.getInt("id_categorie"));

                produits.add(new Produit(rs.getInt("id_produit"), rs.getString("description_produit"), rs.getFloat("prix"), rs.getInt("quantite_produit"), rs.getString("marque"), id_categorie, rs.getString("image"),rs.getString("sku")));
            }
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }

        return produits;
    }
    public List<Double> listnote() {
        List<Double> produits = new ArrayList();
        String req = "SELECT note FROM score";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while(rs.next()) {
                produits.add(rs.getDouble("note" ));
            }
        } catch (SQLException var5) {
            System.out.println(var5.getMessage());
        }

        return produits;
    }
    public Categorie recherchecat(int id_categorie) {
        Categorie evs = null;
        String req = "SELECT * FROM categorie WHERE id_categorie = " + id_categorie;

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                evs = new Categorie();
                evs.setId_cat(rs.getInt("id_categorie"));
                evs.setNom_cat(rs.getString("nom_cat"));
                evs.setDescr(rs.getString("description_cat"));
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return evs;
    }
    public void trierProduitsParPrixCroissant(List<Produit> listeProduits) {
        Collections.sort(listeProduits, Comparator.comparing(Produit::getQuant));
    }

    public List<Produit> SearchByContent(String description_produit) throws SQLException {
        String query;
        PreparedStatement ps;

        if (description_produit.isEmpty()) {
            // If search content is empty, retrieve all forums
            query = "SELECT * FROM produit";
            ps = this.connection.prepareStatement(query);
        } else {
            // If search content is not empty, perform the search
            query = "SELECT * FROM produit WHERE description_produit LIKE ?";
            ps = this.connection.prepareStatement(query);
            ps.setString(1, "%" + description_produit + "%");

        }
        System.out.println("SQL Query: " + ps.toString());
        ResultSet rs = ps.executeQuery();
        List<Produit> forums = new ArrayList<>();

        while (rs.next()) {
            Categorie id_categorie = this.recherchecat(rs.getInt("id_categorie"));
            Produit f = new Produit(rs.getInt("id_produit"), rs.getString("description_produit"), rs.getFloat("prix"), rs.getInt("quantite_produit"), rs.getString("marque"), id_categorie, rs.getString("image"),rs.getString("sku"));

            forums.add(f);
        }

        return forums;
    }
    public List<Produit> triProduit() throws SQLException {
        String query;
        PreparedStatement ps;


            // If search content is not empty, perform the search
            query = "SELECT * FROM produit ORDER BY description_produit";
            ps = this.connection.prepareStatement(query);
           // ps.setString(1, "%" + description_produit + "%");


        System.out.println("SQL Query: " + ps.toString());
        ResultSet rs = ps.executeQuery();
        List<Produit> forums = new ArrayList<>();

        while (rs.next()) {
            Categorie id_categorie = this.recherchecat(rs.getInt("id_categorie"));
            Produit f = new Produit(rs.getInt("id_produit"), rs.getString("description_produit"), rs.getFloat("prix"), rs.getInt("quantite_produit"), rs.getString("marque"), id_categorie, rs.getString("image"),rs.getString("sku"));

            forums.add(f);
        }

        return forums;
    }
    public List<Produit> trimarque() throws SQLException {
        String query;
        PreparedStatement ps;

            // If search content is not empty, perform the search
            query = "SELECT * FROM produit ORDER BY marque";
            ps = this.connection.prepareStatement(query);
            // ps.setString(1, "%" + description_produit + "%");
        System.out.println("SQL Query: " + ps.toString());
        ResultSet rs = ps.executeQuery();
        List<Produit> forums = new ArrayList<>();

        while (rs.next()) {
            Categorie id_categorie = this.recherchecat(rs.getInt("id_categorie"));
            Produit f = new Produit(rs.getInt("id_produit"), rs.getString("description_produit"), rs.getFloat("prix"), rs.getInt("quantite_produit"), rs.getString("marque"), id_categorie, rs.getString("image"),rs.getString("sku"));

            forums.add(f);
        }

        return forums;
    }
    public List<Produit> filter1() throws SQLException {
        String query;
        PreparedStatement ps;

        // If search content is not empty, perform the search
        query = "SELECT * FROM produit WHERE quantite_produit >= 10 AND quantite_produit <= 20";
        ps = this.connection.prepareStatement(query);
        // ps.setString(1, "%" + description_produit + "%");
        System.out.println("SQL Query: " + ps.toString());
        ResultSet rs = ps.executeQuery();
        List<Produit> forums = new ArrayList<>();

        while (rs.next()) {
            Categorie id_categorie = this.recherchecat(rs.getInt("id_categorie"));
            Produit f = new Produit(rs.getInt("id_produit"), rs.getString("description_produit"), rs.getFloat("prix"), rs.getInt("quantite_produit"), rs.getString("marque"), id_categorie, rs.getString("image"),rs.getString("sku"));

            forums.add(f);
        }

        return forums;
    }
    public List<Produit> filter20() throws SQLException {
        String query;
        PreparedStatement ps;

        // If search content is not empty, perform the search
        query = "SELECT * FROM produit WHERE quantite_produit >= 21 AND quantite_produit <= 30";
        ps = this.connection.prepareStatement(query);
        // ps.setString(1, "%" + description_produit + "%");
        System.out.println("SQL Query: " + ps.toString());
        ResultSet rs = ps.executeQuery();
        List<Produit> forums = new ArrayList<>();

        while (rs.next()) {
            Categorie id_categorie = this.recherchecat(rs.getInt("id_categorie"));
            Produit f = new Produit(rs.getInt("id_produit"), rs.getString("description_produit"), rs.getFloat("prix"), rs.getInt("quantite_produit"), rs.getString("marque"), id_categorie, rs.getString("image"),rs.getString("sku"));

            forums.add(f);
        }

        return forums;
    }
}
