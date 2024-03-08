package com.esprit.services;

import com.esprit.models.Espace;
import com.esprit.models.ParticipantClub;
import com.esprit.models.Club;

import com.esprit.utils.DataSource;


import java.sql.*;
import java.util.*;
import java.util.prefs.Preferences;

public class ParticiapantClubService implements IService<ParticipantClub>{
    private Connection connection;
    public ParticiapantClubService() {
        connection = DataSource.getInstance().getConnection();
    }
    Preferences prefs = Preferences.systemNodeForPackage(this.getClass());
    //prefs.put("id",user.getid())
    //prefs.get(id);
    @Override
    public void ajouter(ParticipantClub PC) {
        String req = "INSERT into participantclub(id_user,id_club) values ('" + PC.getId_user() + "', '" + PC.getNom_club().getId_club() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Participant ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(ParticipantClub PC) {
        /*String req = "UPDATE club set nom_club = '" + C.getNom_club() + "', adresse_club = '"  + C.getAdresse_club() + "' , description_club = '"  + C.getDescription_club() + "' , image_club = '" + C.getImage_club() + "', temp_ouverture = '" + C.getTemp_ouverture() + "', id_espace = '" + C.getEspace().getId_espace() + "' where id_club = '" + C.getId_club() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Club modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }


    @Override
    public void supprimer(ParticipantClub PC) {
        /*String req = "DELETE from club where id_club = " + C.getId_club() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("club supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }


    @Override
    public List<ParticipantClub> afficher() {
        List<ParticipantClub> C = new ArrayList<>();
        /*String req = "SELECT * from club";
        ;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                EspaceService es=new EspaceService();
                Espace espace = es.rechercheEspace(rs.getInt("id_espace"));
                if (espace != null) {
                    C.add(new Club(rs.getInt("id_club"), rs.getString("nom_club"), rs.getString("adresse_club"), rs.getString("description_club"), rs.getString("image_club"), rs.getTime("temp_ouverture"), espace));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        return C;
    }
}
