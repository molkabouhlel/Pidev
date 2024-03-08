package com.esprit.services;

import com.esprit.models.typec;
import com.esprit.services.IService;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypecService  implements IService<typec> {
    private Connection connection;

    public TypecService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(typec e) {
        String req = "INSERT into typec(typecours) values ('" + e.getTypecours() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("typeeeeeeee ajoutée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }

    }


    @Override
    public void modifier(typec e) {
        String req = "UPDATE typec set typecours = '" + e.getTypecours() + "' where idtypec = " + e.getIdtypec() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("typeeecouuurs modifiée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }

    @Override
    public void supprimer(typec cv) {
        String req = "DELETE from typec where idtypec = " + cv.getIdtypec() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("typec supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<typec> afficher() {
        List<typec> c = new ArrayList<>();

        String req = "SELECT * from typec";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                c.add(new typec( rs.getInt("idtypec"),rs.getString("typecours")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }
  /*  public typec recherchecat (int id_typec) {
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
    }*/

}


