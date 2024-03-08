package com.esprit.services;

import com.esprit.models.*;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Rendez_vousService implements IService <Rendez_vous>{
    private Connection connection;

    public Rendez_vousService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Rendez_vous r) {
        String req = "INSERT into rendez_vous (date_rv,id_eq ,id_coach) values ('" + r.getDate_rv() + "' ,'" + r.getId_eq().getId_eq() + "', '" + r.getId_coach().getCin() + "');";
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
        String req = "UPDATE rendez_vous set date_rv = '" + rv.getDate_rv() + "', id_eq = '" + rv.getId_eq().getId_eq()+ "', id_coach = '" + rv.getId_coach() .getCin() + "' where id_rv = " + rv.getId_rv() + ";";
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



    public List<Rendez_vous> afficher() {
        List<Rendez_vous> c = new ArrayList<>();

        String req = "SELECT * FROM rendez_vous";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Equipement id_eq = rechercheid_eq(rs.getInt("id_eq"));
                CoursService e=new CoursService();
                Coach esp=e.rechercheCoachaffich(rs.getInt("id_coach"));
               //cle etrangere coach a ajouter
                if (id_eq != null) {
                    c.add(new  Rendez_vous(rs.getTimestamp("date_rv"),rs.getInt("id_rv"), id_eq,esp));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // or use a logger
        }

        return c;
    }
    ////////////////////////////////////////////////////////
    public Equipement rechercheid_eq (int id_eq) {
        Equipement eqs = null;
        String req =  "SELECT * FROM equipement WHERE id_eq = '" + id_eq + "'";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                eqs = new Equipement();
                eqs.setId_eq(rs.getInt("id_eq"));
                eqs.setRef_eq(rs.getString("ref_eq"));

                eqs.setNom_eq(rs.getString("nom_eq"));
                eqs.setDescription_eq(rs.getString("description_eq"));
                eqs.setQuantite_dispo(rs.getInt("quantite_dispo"));
               // eqs.setId_coach(rs.getInt("id_coach"));
                EvenementService e=new EvenementService();
              Espace ep=e.recherchees(rs.getInt("id_espace"));
                eqs.setId_espace(ep);
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
    public List<String> listeq() {
        List<Equipement> eqList = listeqnom();
        List<String> nomEqList = new ArrayList<>();

        for (Equipement equipement : eqList) {
            nomEqList.add(equipement.getNom_eq());
        }

        return nomEqList;
    }

    public Equipement rechercherEqParNom(String nom_eq) {
        String req = "SELECT * FROM equipement WHERE nom_eq = '" + nom_eq + "'";
        Equipement equipement = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                equipement=new Equipement(rs.getInt("id_eq"), rs.getString("nom_eq"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return equipement;
    }
   public List<Equipement> listeqnom() {
        List<Equipement> eqList = new ArrayList<>();
        String req = "SELECT nom_eq FROM equipement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Equipement e = new Equipement();
                e.setNom_eq(rs.getString("nom_eq"));
                eqList.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return eqList;
    }

}
