package com.esprit.activite.services;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.utils.DataSource;

import java.sql.*;
import java.util.*;

public class CoursService implements Iservice <Cours>{
    private Connection connection;

    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Cours c) {
        String req = "INSERT into cours (nom, datedebut, datefin, heure, nomcoach, idcoach, nbparticipant, description) values ('" + c.getNom() + "',  '" + c.getDatedebut() + "','" + c.getDatefin() + "', '" + c.getHeure() + "','" + c.getNomcoach() + "','" + c.getIdcoach() + "','" + c.getNbparticipant() + "','" + c.getDescription() +"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("coooouuurrr ajoutée !");
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
    public void modifier(Cours c) {
        String req = "UPDATE cours set nom = '" + c.getNom() + "', datedebut = '" + c.getDatedebut() + "', datefin = '" + c.getDatefin() + "', heure = '" + c.getHeure() + "', nomcoach = '" + c.getNomcoach() + "', idcoach='" + c.getIdcoach() + "',nbparticipant = '" + c.getNbparticipant() + "', description = '" + c.getDescription() + "' where id = " + c.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("couur modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Cours c) {
        String req = "DELETE from cours where id = " + c.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("couur supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Cours> afficher() {
        List<Cours> c = new ArrayList<>();

        String req = "SELECT * from cours";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                c.add(new Cours(rs.getInt("id"), rs.getString("nom"), rs.getString("datedebut"), rs.getString("datefin"), rs.getString("heure"), rs.getString("nomcoach"), rs.getInt("idcoach"), rs.getInt("nbparticipant"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }
}
