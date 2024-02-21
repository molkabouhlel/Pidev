package com.esprit.activite.services;

import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.typec;
import com.esprit.activite.utils.DataSource;

import java.sql.*;
import java.util.*;

public class CoursService implements Iservice <Cours>{
    private Connection connection;
private typec t =new typec();
    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Cours c) {
        String req = "INSERT into cours (nom, description, imagec, duree, idcoach, idclub, id_typec) values ('" + c.getNom() + "',  '" + c.getDescription() + "','" + c.getImagec() + "', '" + c.getDuree() + "','" + c.getIdcoach() + "','" + c.getIdclub() + "', '" + c.getId_typec().getIdtypec() +"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("coooouuurrr ajoutée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }

    }


    @Override
    public void modifier(Cours c) {
        String req = "UPDATE cours set nom = '" + c.getNom() + "', description = '" + c.getDescription() + "', imagec = '" + c.getImagec() + "', duree = '" + c.getDuree() + "', idcoach = '" + c.getIdcoach() + "', idclub='" + c.getIdclub() + "',id_typec = '" + c.getId_typec().getIdtypec() + "' where id = " + c.getId() + ";";
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
                typec id_typec = recherchetypec(rs.getInt("id_typec"));
                c.add(new Cours(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("imagec"), rs.getTime("duree"), rs.getInt("idcoach"), rs.getInt("idclub"),id_typec));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }
/////////////////
public typec recherchetypec (int id_typec) {
    typec evs = null;
    String req = "SELECT * FROM typec WHERE idtypec = " + id_typec;
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            evs = new typec();
            evs.setIdtypec(rs.getInt("idtypec"));
            evs.setTypecours(rs.getString("typecours"));


        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return evs;
}
    public Cours rechercheCours(int id) {
        Cours C = null;
        String req = "SELECT * FROM cours WHERE id= " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                C = new Cours();
                typec id_typec = recherchetypec(rs.getInt("id_typec"));
                C.setId(rs.getInt("id"));
                C.setNom(rs.getString("nom"));
                C.setDescription(rs.getString("description"));
                C.setDuree(rs.getTime("duree"));
                C.setIdcoach(rs.getInt("idcoach"));
                C.setIdclub(rs.getInt("idclub"));
                // Set other properties as needed
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }



}
