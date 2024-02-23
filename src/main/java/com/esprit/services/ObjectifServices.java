package com.esprit.services;

import com.esprit.models.Objectif;
import com.esprit.models.Programme;
import com.esprit.utils.DataSource;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ObjectifServices implements IService<Objectif>{
    private Connection connection;

    public ObjectifServices() {
        connection = DataSource.getInstance().getConnection();
    }


    @Override
    public void ajouter(Objectif O) {
        String req = "INSERT into objectif(  ID_cours,ID_prog,description) values  ('" + O.getID_cours() + "', '" + O.getProgramme().getID_prog() + "', '" + O.getDescription_obj() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Objectif ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Objectif ajout");
        alerte.setContentText("Objectif bien ajoutee");
        alerte.show();

    }

    @Override
    public void modifier(Objectif O) {
        String req = "UPDATE objectif set ID_cours = '" + O.getID_cours()+ "', ID_prog = '"  + O.getProgramme().getID_prog() + "' , description = '"  + O.getDescription_obj() + "' where ID_obj = '" + O.getID_obj() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Objectif modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Objectif modifiee");
        alerte.setContentText("Objectif bien modifiee");
        alerte.show();

    }

    @Override
    public void supprimer(Objectif O) {
        String req = "DELETE from objectif where ID_obj = " + O.getID_obj() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("club supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Alert alerte= new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Objectif supprime");
        alerte.setContentText("Objectif bien supprimee");
        alerte.show();

    }

    @Override
    public List<Objectif> afficher() {
        List<Objectif> O = new ArrayList<>();
        String req = "SELECT * from objectif";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ProgrammeServices es=new ProgrammeServices();
                Programme programme = es.rechercheProgramme(rs.getInt("ID_prog"));
                if (programme != null) {
                    O.add(new Objectif(rs.getInt("ID_obj"), rs.getInt("ID_cours"),programme,  rs.getString("description")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return O;
    }


    public Objectif rechercheObj(int id) {
        Objectif objectif = null;
        Programme P = new Programme();
        String req = "SELECT * FROM objectif WHERE ID_obj = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                objectif  = new Objectif();
                objectif.setID_obj (rs.getInt("ID_obj"));
                objectif.setID_cours(rs.getInt("ID_cours"));
                objectif.setDescription_obj(rs.getString("description"));

                ProgrammeServices es=new ProgrammeServices();;
                Programme programme = es.rechercheProgramme(rs.getInt("ID_prog"));
                objectif.setProgramme(programme);
                // Set other properties as needed
            }
        } catch (SQLException eq) {
            System.out.println(eq.getMessage());
        }
        return objectif;
    }

}
