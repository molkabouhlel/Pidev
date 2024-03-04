package com.esprit.activite.services;

//import com.esprit.activite.modeles.Cours;
import com.esprit.activite.modeles.*;
import com.esprit.activite.utils.DataSource;
import javafx.scene.control.TableColumn;

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
        String req = "INSERT into equipement (ref_eq,nom_eq ,description_eq ,quantite_dispo , id_espace,id_ceq,id_m,image) values ( '"+ e.getRef_eq()+"','" + e.getNom_eq() + "',  '" + e.getDescription_eq() + "','" + e.getQuantite_dispo() +  "','" + e.getId_espace()  + "','" + e.getId_ceq().getId_ceq() + "','" + e.getId_m().getId_m() + "','" + e.getImage()+ "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("equipement ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @Override
    public void modifier(Equipement e) {
        String req = "UPDATE equipement set id_eq='"+e.getId_eq()+"',ref_eq='"+e.getRef_eq()+"', nom_eq = '" + e.getNom_eq() + "', description_eq = '" + e.getDescription_eq() + "', quantite_dispo = '" + e.getQuantite_dispo()  + "',id_espace = '" + e.getId_espace()+ "',id_ceq = '" + e.getId_ceq().getId_ceq() + "',id_m = '" + e.getId_m().getId_m() + "',image = '" + e.getImage()   + "' WHERE id_eq = '" + e.getId_eq() + "'";;
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
        String req = "DELETE from equipement WHERE id_eq = " + e.getId_eq() + ";";
        System.out.println(e.getId_eq());
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("equipement supprimé !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

public List<Equipement> afficher() {
    List<Equipement> c = new ArrayList<>();

    String req = "SELECT * FROM equipement";
    try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
        while (rs.next()) {
            Categorie_eq id_ceq = recherchecat_eq(rs.getInt("id_ceq"));
            Maintenance_eq mat = recherchemat(rs.getInt("id_m"));

            //cle etrangere coach a ajouter
            if (id_ceq != null) {
                c.add(new Equipement(rs.getInt("id_eq"), rs.getString("ref_eq"), rs.getString("nom_eq"), rs.getString("description_eq"), rs.getInt("quantite_dispo"),rs.getInt("id_espace"),id_ceq,mat,rs.getString("image")));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();  // or use a logger
    }

    return c;
}
    ////////////////////////////////////////////////////////
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


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return eqs;
    }
    public Maintenance_eq recherchemat (int id_m) {
        Maintenance_eq meqs = null;
        String req = "SELECT * FROM maintenance WHERE id_m = " + id_m;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                meqs = new Maintenance_eq();
                meqs.setId_m(rs.getInt("id_m"));
                meqs.setDate_m(rs.getTimestamp("date_m"));
                String etat_mString = rs.getString("etat_m");




            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return meqs;
    }





    public List<Integer> RecupereridEspace() {
        List<Integer> C = new ArrayList<>();
        String req = "SELECT id_espace from espace";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                C.add(rs.getInt("id_espace"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }


    public List<Integer> RecupereridCoach() {
        List<Integer> C = new ArrayList<>();
        String req = "SELECT cin from usr";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                C.add(rs.getInt("cin"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }



    public List<Equipement> RecupererEquipement() {
        List<Equipement> E = new ArrayList<>();
        String req = "SELECT * from equipement";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Categorie_eq id_ceq = recherchecat_eq(rs.getInt("id_ceq"));
                Maintenance_eq mat = recherchemat(rs.getInt("id_m"));

                if (id_ceq != null) {
                    E.add(new Equipement(rs.getInt("id_eq"),rs.getString("ref_eq"), rs.getString("nom_eq"), rs.getString("description_eq"), rs.getInt("quantite_dispo"),rs.getInt("id_espace"),id_ceq,mat,rs.getString("image")));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return E;
    }

    public Equipement rechercheeq(int id) {
        Equipement C = null;
        String req = "SELECT * FROM equipement WHERE id_eq="+ id ;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                C = new Equipement();
                System.out.println("test");
                Maintenance_eq id_m = recherchemat(rs.getInt("id_m"));
                Categorie_eq cat = recherchecat_eq(rs.getInt("id_ceq"));
                C.setRef_eq(rs.getString("ref_eq"));
               C.setId_eq(rs.getInt("id_eq"));
                C.setId_espace(rs.getInt("id_espace"));
                C.setNom_eq(rs.getString("nom_eq"));
                C.setDescription_eq(rs.getString("description_eq"));
                C.setQuantite_dispo(rs.getInt("quantite_dispo"));
                C.setImage(rs.getString("image"));
                // Set other properties as needed
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }

}
