package com.esprit.equipement.services;

import com.esprit.equipement.modeles.Categorie_eq;
import com.esprit.equipement.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Categorie_eqService implements Iservice<Categorie_eq>
{private Connection connection;

    public Categorie_eqService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Categorie_eq c) {
        String req = "INSERT into categorie_eq (type_ceq,desc_ceq) values ('" + c.getType_ceq() + "','"+ c.getDesc_ceq()+ "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void modifier(Categorie_eq c) {
        String req = "UPDATE categorie_eq set type_ceq='"+c.getType_ceq() +"', desc_ceq = '" +c.getDesc_ceq()+"' where id_ceq = " + c.getId_ceq() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Categorie_eq c) {
        String req = "DELETE from categorie_eq where id_ceq = " + c.getId_ceq() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("categorie supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie_eq> afficher() {
        List<Categorie_eq> c = new ArrayList<>();

        String req = "SELECT * from categorie_eq";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                c.add(new Categorie_eq(rs.getInt("id_ceq"), rs.getString("type_ceq"), rs.getString("desc_ceq")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }
    public Categorie_eq recherchecat_eq (int id_ceq) {
        Categorie_eq eqs = null;
        String req = "SELECT * FROM categorie_eq WHERE id_ceq = " + id_ceq;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                eqs = new Categorie_eq();
                eqs.setId_ceq(rs.getInt("id_ceq"));
                eqs.setType_ceq(rs.getString("type_ceq"));
                eqs.setDesc_ceq(rs.getString("desc_ceq"));



            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return eqs;
    }
}


