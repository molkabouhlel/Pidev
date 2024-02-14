package com.esprit.activite.services;

import com.esprit.activite.modeles.Equipement;
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
        String req = "INSERT into rendez_vous (date_rv ,heure_rv ,id_eq ,id_rv) values ('" + r.getDate_rv() + "',  '" + r.getHeure_rv() + "','" + r.getId_eq() + "', '" + r.getId_rv() +  "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("rendez-vous ajouté !");
        } catch (SQLException rv) {
            System.out.println(rv.getMessage());
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
    public void modifier(Rendez_vous rv) {
        String req = "UPDATE rendez_vous set date_rv = '" + rv.getDate_rv() + "', heure_rv = '" + rv.getHeure_rv() + "', id_eq = '" + rv.getId_eq() + "', id_rv = '" + rv.getId_rv()  + "' where id_rv = " + rv.getId_rv() + ";";
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
    }
}
