package com.esprit.activite.services;

import com.esprit.activite.modeles.Categorie_eq;
import com.esprit.activite.modeles.Equipement;
import com.esprit.activite.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements Iservice<Categorie_eq>
{private Connection connection;

    public CategorieService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Categorie_eq c) {
        String req = "INSERT into categorie_eq (type_ceq) values ('" + c.getType_ceq() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
     /*   String req = "INSERT INTO cours(nom, datedebut, datefin, heure, nomcoach, idcoach, nbparticipant, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, c.getNom());
            st.setString(2, c.getDatedebut());
            st.setString(3, c.getDatefin());
            st.setString(4, c.getHeure());
            st.setString(5, c.getNomcoach());
            st.setInt(6, c.getIdcoach());
            st.setInt(7, c.getNbparticipant());
            st.setString(8, c.getDescription());

            st.executeUpdate();
            System.out.println("Cours ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }


    @Override
    public void modifier(Categorie_eq c) {
        String req = "UPDATE categorie_eq set type_ceq = '" + c.getType_ceq()   + "' where id_ceq = " + c.getId_ceq() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Categorie_eq c) {
        String req = "DELETE from categorie_eq where id_ceq = " + c.getId_ceq() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie_eq> afficher() {
        List<Categorie_eq> c = new ArrayList<>();

        String req = "SELECT * from categorie_eq";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                c.add(new Categorie_eq(rs.getInt("id_ceq"), rs.getString("type_ceq")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    public Categorie_eq recherchecat_eq (int id_ceq) {
        Categorie_eq eqs = null;
        String req = "SELECT * FROM categorie_eq WHERE id_ceq = " + id_ceq;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                eqs = new Categorie_eq();
                eqs.setId_ceq(rs.getInt("id_ceq"));
                eqs.setType_ceq(rs.getString("type_ceq"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return eqs;
    }
}


