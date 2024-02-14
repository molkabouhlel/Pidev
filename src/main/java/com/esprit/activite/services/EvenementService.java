package com.esprit.activite.services;

import com.esprit.activite.modeles.*;
import com.esprit.activite.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements Iservice <Evenement> {
    private Connection connection;

    public EvenementService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Evenement e) {
        String req = "INSERT into evenement (nom_ev,description_ev, date_ev, heure_ev, id_espace, nb_participant) values ('" + e.getNom_ev() + "',  '" + e.getDescription_ev() + "','" + e.getDate_ev() + "', '" + e.getHeure_ev() + "','" + e.getId_espace() + "','" + e.getNb_participant() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("evvvvvv ajoutée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
      /*  String req = "INSERT INTO evenement( nom_ev,description_ev, date_ev, heure_ev, id_espace, nb_participant) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, e.getNom_ev());
            st.setString(2, e.getDescription_ev());
            st.setString(3, e.getDate_ev());
            st.setString(4, e.getHeure_ev());
            st.setInt(5, e.getId_espace());
            st.setInt(6,  e.getNb_participant());


            st.executeUpdate();
            System.out.println("evvvveennement ajouté !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }*/
    }


   @Override
    public void modifier(Evenement e) {
        String req = "UPDATE evenement set nom_ev = '" + e.getNom_ev() + "', description_ev = '" + e.getDescription_ev() + "', date_ev = '" + e.getDate_ev() + "', heure_ev = '" + e.getHeure_ev() + "', id_espace = '" + e.getId_espace() + "', nb_participant ='" + e.getNb_participant() + "' where id_ev = " + e.getId_ev() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("evvvvv modifiée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }

    @Override
    public void supprimer(Evenement ev) {
        String req = "DELETE from evenement where id_ev = " + ev.getId_ev() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("evvvvvv supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Evenement> afficher() {
        List<Evenement> c = new ArrayList<>();

        String req = "SELECT * from evenement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                c.add(new Evenement(rs.getInt("id_ev"), rs.getString("nom_ev"), rs.getString("description_ev"), rs.getString("date_ev"), rs.getString("heure_ev"), rs.getInt("id_espace"), rs.getInt("nb_participant")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }
}
