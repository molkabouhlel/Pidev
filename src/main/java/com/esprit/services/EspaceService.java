package com.esprit.services;

import com.esprit.models.Espace;
import com.esprit.utils.DataSource;
import java.sql.Time;

import java.sql.*;
import java.util.*;
public class EspaceService implements IService<Espace> {

        private Connection connection;

        public EspaceService() {
            connection = DataSource.getInstance().getConnection();
        }
        @Override
        public void ajouter(Espace E) {
            String req = "INSERT into espace(nom_espace,categorie,description_espace,heure_overture,disponibilite) values ('" + E.getNom_espace() + "', '" + E.getCategorie() + "' , '" + E.getDescription_espace() + "'   , '" +   E.getHeure_overture() + "'  , '" + E.getDisponibilite() + "');";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("Espace ajoutée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void modifier(Espace E) {
            String req = "UPDATE espace set nom_espace = '" + E.getNom_espace() + "', categorie = '"  + E.getCategorie() + "' , description_espace = '" + E.getDescription_espace() + "', heure_overture = '" + E.getHeure_overture() + "', disponibilite = '" + E.getDisponibilite() + "' where id_espace = " + E.getId_espace() + ";";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("Espace modifié !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void supprimer(Espace E) {
            String req = "DELETE from espace where id_espace = " + E.getId_espace() + ";";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("espace supprmié !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public List<Espace> afficher() {
            List<Espace> E = new ArrayList<>();
            String req = "SELECT * from espace ";
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {
                    E.add(new Espace(rs.getInt("id_espace"), rs.getString("nom_espace"), rs.getString("categorie") , rs.getString("description_espace") , rs.getTime("heure_overture"),rs.getString("disponibilite")));

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return E;
        }
    }



