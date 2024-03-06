package com.esprit.services;

import com.esprit.models.Programme;
import com.esprit.models.Objectif;

import com.esprit.utils.DataSource;


import java.sql.*;
import java.util.*;

public class ProgrammeServices implements IService<Programme> {

    private Connection connection;

    public ProgrammeServices() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Programme P) {
        String req = "INSERT into Programme( nom_prog,description,etat_initial,date_debut,date_fin,ID_user) values ('" + P.getNom_prog()+ "', '" + P.getDesc_prog()+ "','" +P.getEtat_initial() +"' , '" + P.getDate_debut() + "'  , '" + P.getDate_fin() + "'  , '" +P.getID_user()+"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("prog ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Programme P) {
        String req = "UPDATE programme set nom_prog = '" + P.getNom_prog() +"',description='" +P.getNom_prog()+ "', rate= '" + P.getRate() + " ', etat_initial = '"  + P.getEtat_initial() + "' , etat_final = '" + P.getEtat_final() + "', date_debut = '" + P.getDate_debut() + "', date_fin = '" + P.getDate_fin() + "',ID_user ='"+P.getID_user()+ "' where ID_prog = '" + P.getID_prog() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("programme modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(Programme P) {
        String req = "DELETE from programme where ID_prog = " + P.getID_prog() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Programme supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Programme> afficher() {
        List<Programme> P = new ArrayList<>();
        String req = "SELECT * from programme";
        ;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next())
                P.add(new Programme(rs.getInt("ID_prog"),rs.getString("nom_prog"),rs.getString("description"), rs.getFloat("rate"), rs.getString("etat_initial"), rs.getString("etat_final"), rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getInt("ID_user")));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return P;
    }
    public Programme rechercheProgramme(int id) {
        Programme Programme = null;
        String req = "SELECT * FROM programme WHERE ID_prog = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                Programme = new Programme();
                Programme.setID_prog(rs.getInt("ID_prog"));
                Programme.setDesc_prog(rs.getString("description"));
                Programme.setRate(rs.getFloat("rate"));
                Programme.setEtat_initial(rs.getString("etat_initial"));
                Programme.setEtat_final(rs.getString("etat_final"));
                Programme.setDate_debut(rs.getDate("Date_debut"));
                Programme.setDate_fin(rs.getDate("Date_fin"));
                Programme.setID_user(rs.getInt("ID_user"));

                // Set other properties as needed
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Programme;
    }
    public List<Integer> RecupereridCours() {
        List<Integer> L = new ArrayList<>();
        String req = "SELECT id from cours";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                L.add(rs.getInt("id"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return L;
    }




    public void mettreAJourProgramme(Programme programme) {
        String requete = "UPDATE programme SET rate = ? WHERE ID_prog = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setFloat(1, programme.getRate());
            preparedStatement.setInt(2, programme.getID_prog());
            preparedStatement.executeUpdate();
            System.out.println("Programme mis à jour avec succès dans la base de données.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du programme dans la base de données : " + e.getMessage());
        }
    }
}