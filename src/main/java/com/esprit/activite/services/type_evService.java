package com.esprit.activite.services;

import com.esprit.activite.modeles.type_ev;
import com.esprit.activite.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class type_evService implements Iservice <type_ev> {
    private Connection connection;

    public type_evService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(type_ev te) {
        String req = "INSERT into type_ev (type_ev) values ('" + te.getType_ev() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("type_ev ajoutée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }


    @Override
    public void modifier(type_ev c) {
        String req = "UPDATE type_ev set type_ev = '" + c.getType_ev() + "' where id_typeev = " + c.getId_typeev() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("type_ev modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(type_ev c) {
        String req = "DELETE from type_ev where id_typeev = " + c.getId_typeev() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("type_ev supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<type_ev> afficher() {
        List<type_ev> c = new ArrayList<>();

        String req = "SELECT * from type_ev";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                c.add(new type_ev(rs.getInt("id_typeev"), rs.getString("type_ev")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }

    public type_ev recherchecatev (int id_typeev) {
        type_ev ev = null;
        String req = "SELECT * FROM type_ev WHERE id_typeev = " + id_typeev;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                ev = new type_ev();
                ev.setId_typeev(rs.getInt("id_typeev"));
                ev.setType_ev(rs.getString("type_ev"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ev;
    }

}
