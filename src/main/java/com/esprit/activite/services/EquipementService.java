package com.esprit.activite.services;

//import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.Equipement;
import com.esprit.activite.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EquipementService implements Iservice <Equipement>{
    private Connection connection;

    public EquipementService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Equipement e) {
        String req = "INSERT into equipement (nom_eq ,description_eq ,quantite_dispo ,etat ,date_maintenance, id_coach, id_espace) values ('" + e.getNom_eq() + "',  '" + e.getDescription_eq() + "','" + e.getQuantite_dispo() + "', '" + e.getEtat() + "','" + e.getDate_maintenance() + "','" + e.getId_coach() + "','" + e.getId_espace() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("equipement ajouté !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
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
    public void modifier(Equipement e) {
        String req = "UPDATE equipement set nom_eq = '" + e.getNom_eq() + "', description_eq = '" + e.getDescription_eq() + "', quantite_dispo = '" + e.getQuantite_dispo() + "', etat = '" + e.getEtat() + "', date_maintenance = '" + e.getDate_maintenance() + "', id_coach='" + e.getId_coach() + "',id_espace = '" + e.getId_espace()  + "' where id_eq = " + e.getId_eq() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("equipement modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Equipement e) {
        String req = "DELETE from equipement where id_eq = " + e.getId_eq() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("equipement supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Equipement> afficher() {
        List<Equipement> e = new ArrayList<>();

        String req = "SELECT * from equipement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                e.add(new Equipement(rs.getInt("id_eq"), rs.getString("nom_eq"), rs.getString("description_eq"), rs.getInt("quantite_dispo"), rs.getString("etat"), rs.getDate("date_maintenance"), rs.getInt("id_coach"), rs.getInt("id_espace")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return e;
    }
}
