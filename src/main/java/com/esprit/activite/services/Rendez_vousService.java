package com.esprit.activite.services;

import com.esprit.activite.modeles.Categorie_eq;
import com.esprit.activite.modeles.Equipement;
import com.esprit.activite.modeles.Maintenance_eq;
import com.esprit.activite.modeles.Rendez_vous;
import com.esprit.activite.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Rendez_vousService implements Iservice <Rendez_vous>{
    private Connection connection;

    public Rendez_vousService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Rendez_vous r) {
        String req = "INSERT into rendez_vous (date_rv,ref_eq ,id_coach) values ('" + r.getDate_rv() + "' ,'" + r.getRef_eq().getRef_eq() + "', '" + r.getId_coach() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("rendez-vous ajouté !");
        } catch (SQLException rv) {
            System.out.println(rv.getMessage());
            System.out.println("ma5dimch");
        }
    }

    @Override
    public void modifier(Rendez_vous rv) {
        String req = "UPDATE rendez_vous set date_rv = '" + rv.getDate_rv() + "', ref_eq = '" + rv.getRef_eq().getRef_eq()+ "', id_coach = '" + rv.getId_coach()  + "' where id_rv = " + rv.getId_rv() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("rendez_vous modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Rendez_vous rv) {
        String req = "DELETE from rendez_vous where id_rv = " + rv.getId_rv() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("rendez_vous supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
/*
    @Override
    public List<Rendez_vous> afficher() {
        List<Rendez_vous> rv = new ArrayList<>();

        String req = "SELECT * from rendez_vous";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                rv.add(new Rendez_vous(rs.getDate("date_rv"), rs.getTime("heure_rv"), rs.getInt("id_eq"), rs.getInt("id_rv")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return rv;
    }*/


    public List<Rendez_vous> afficher() {
        List<Rendez_vous> c = new ArrayList<>();

        String req = "SELECT * FROM rendez_vous";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Equipement ref_eq = rechercheref_eq(rs.getString("ref_eq"));
               //cle etrangere coach a ajouter
                if (ref_eq != null) {
                    c.add(new  Rendez_vous(rs.getTimestamp("date_rv"),rs.getInt("id_rv"), ref_eq,rs.getInt("id_coach")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // or use a logger
        }

        return c;
    }
    ////////////////////////////////////////////////////////
    public Equipement rechercheref_eq (String ref_eq) {
        Equipement eqs = null;
        String req =  "SELECT * FROM equipement WHERE ref_eq = '" + ref_eq + "'";;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                eqs = new Equipement();
                eqs.setRef_eq(rs.getString("ref_eq"));
                eqs.setNom_eq(rs.getString("nom_eq"));
                eqs.setDescription_eq(rs.getString("description_eq"));
                eqs.setQuantite_dispo(rs.getInt("quantite_dispo"));
                eqs.setId_coach(rs.getInt("id_coach"));
                eqs.setId_espace(rs.getInt("id_espace"));
                Categorie_eq categorie_eq = new Categorie_eq();
                categorie_eq.setId_ceq(rs.getInt("id_ceq"));
                eqs.setId_ceq(categorie_eq);

                Maintenance_eq maintenance_eq = new Maintenance_eq();
                maintenance_eq.setId_m(rs.getInt("id_m"));
                eqs.setId_m(maintenance_eq);

                eqs.setImage(rs.getString("image"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return eqs;
    }

}
