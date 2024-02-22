package com.esprit.activite.services;

import com.esprit.activite.modeles.Categorie_eq;
import com.esprit.activite.modeles.Equipement;
import com.esprit.activite.modeles.Maintenance_eq;
import com.esprit.activite.modeles.etat_m;
import com.esprit.activite.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceService implements Iservice<Maintenance_eq>
{private Connection connection;

    public MaintenanceService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Maintenance_eq m) {
        String req = "INSERT into maintenance (date_m, etat_m) values ('" + m.getDate_m() + "',  '" + m.getEtat_m() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("maintenance ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void modifier(Maintenance_eq m) {
        String s;
        String req = "UPDATE maintenance set date_m = '" + m.getDate_m() + "', etat_m = '" + m.getEtat_m()  + "' where id_m = '" + m.getId_m() + "'";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("maintenance modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Maintenance_eq m) {
        String req = "DELETE from maintenance where id_m = " + m.getId_m() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("maintenance supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Maintenance_eq> afficher() {
        List<Maintenance_eq> m = new ArrayList<>();

        String req = "SELECT * FROM maintenance";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int idMaintenance = rs.getInt("id_m");
                Timestamp dateMaintenance = rs.getTimestamp("date_m");

                // Utiliser la méthode getString pour obtenir la valeur de la colonne ENUM en tant que chaîne
                String etatMaintenanceString = rs.getString("etat_m");

                // Convertir la chaîne en instance de l'énumération EtatMaintenance
                etat_m etatMaintenance = etat_m.valueOf(etatMaintenanceString);

                // Ajouter l'objet Maintenance_eq à la liste
                m.add(new Maintenance_eq(idMaintenance, dateMaintenance, etatMaintenance));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return m;
    }
   /* public Maintenance_eq recherchem_eq (int id_m) {
        Maintenance_eq eqs = null;
        String req = "SELECT * FROM maintenance WHERE id_m = " + id_m;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                eqs = new Maintenance_eq();
                eqs.setId_m(rs.getInt("id_m"));
                eqs.setDate_m(rs.getTimestamp("date_m"));
                eqs.setEtat_m(etat_m.valueOf("etat_m"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return eqs; */
   public Maintenance_eq recherchem_eq (int id_m) {
       Maintenance_eq eqs = null;
       String req = "SELECT * FROM maintenance WHERE id_m = " + id_m;
       try {
           Statement st = connection.createStatement();
           ResultSet rs = st.executeQuery(req);
           if (rs.next()) {
               eqs = new Maintenance_eq();
               eqs.setEtat_m(etat_m.valueOf("etat_m"));


           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       return eqs;
    }

}


