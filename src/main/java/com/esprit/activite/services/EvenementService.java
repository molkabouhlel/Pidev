package com.esprit.activite.services;

import com.esprit.activite.modeles.*;
import com.esprit.activite.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements Iservice <Evenement> {
    private Connection connection;
  // private typec tc = new typec();
  // private  type_ev tev=new type_ev();

    public EvenementService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Evenement e) {
        String req = "INSERT into evenement (nom_ev,description_ev, image_ev, date_debut, date_fin, capacite_max ,id_espace ,id_typec ,id_type_ev) values ('" + e.getNom_ev() + "',  '" + e.getDescription_ev() + "','" + e.getImage_ev() + "', '" + e.getDate_debut() + "','" + e.getDate_fin() + "','" + e.getCapacite_max() + "','"+ e.getId_espace().getId_espace()+ "','" +e.getId_typec().getIdtypec() + "','" + e.getId_type_ev().getId_typeev() +"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("evvvvvv ajoutée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }


   @Override
    public void modifier(Evenement e) {
        String req = "UPDATE evenement set nom_ev = '" + e.getNom_ev() + "', description_ev = '" + e.getDescription_ev() + "', image_ev = '" + e.getImage_ev() + "', date_debut = '" + e.getDate_debut() + "', date_fin = '" + e.getDate_fin() + "', capacite_max ='" + e.getCapacite_max() + "', id_espace ='"+e.getId_espace().getId_espace()+"',id_typec ='" +e.getId_typec().getIdtypec()+"',id_type_ev ='" + e.getId_type_ev().getId_typeev()+ "'   where id_ev = " + e.getId_ev() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("evvvvv modifiée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }

    @Override
    public void supprimer(Evenement ev) {
        String req = "DELETE from evenement where id_ev = " + ev.getId_ev() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("evvvvvv supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Evenement> afficher() {
        List<Evenement> c = new ArrayList<>();

        String req = "SELECT * FROM evenement";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                typec id_typec = recherchetypec(rs.getInt("id_typec"));
                type_ev tv = recherchetyeev(rs.getInt("id_type_ev"));
                espace e =recherchees(rs.getInt("id_espace"));

                if (id_typec != null) {
                    c.add(new Evenement(rs.getInt("id_ev"), rs.getString("nom_ev"), rs.getString("description_ev"), rs.getString("image_ev"), rs.getTimestamp("date_debut"), rs.getTimestamp("date_fin"), rs.getInt("capacite_max"),e, id_typec, tv));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // or use a logger
        }

        return c;
    }
    ////////////////////////////////////////////////////////
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
    public type_ev recherchetyeev (int id_typeev) {
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

 /////////////////////////////////////////////////////////////////

//non
   public List<espace> rechercheIdEspace() {
      List<espace> idEspaces = new ArrayList<>();
        /*  String req = "SELECT id_espace, nom_espace FROM espace";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int idEspace = rs.getInt("id_espace");
                String nomClub = rs.getString("nom_espace");
                espace clubInfo = new espace(idEspace, nomClub);
                idEspaces.add(clubInfo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        return idEspaces;
    }
    //selectid
    public Evenement rechercheEV(int id_ev) {
        Evenement C = null;
        String req = "SELECT * FROM evenement WHERE id_ev= " + id_ev;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                C = new Evenement();
                C.setId_ev(rs.getInt("id_ev"));
                C.setNom_ev(rs.getString("nom_ev"));
                C.setDescription_ev(rs.getString("description_ev"));
                C.setImage_ev(rs.getString("image_ev"));
                C.setDate_debut(rs.getTimestamp("date_debut"));
                C.setDate_fin(rs.getTimestamp("date_fin"));
                C.setCapacite_max(rs.getInt("capacite_max"));
                typec id_typec = recherchetypec(rs.getInt("id_typec"));
                C.setId_typec(id_typec);
                espace e =recherchees(rs.getInt("id_espace"));
                C.setId_espace(e);
                 type_ev id_evv= recherchetyeev(rs.getInt("id_type_ev"));
                C.setId_type_ev(id_evv);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }
    public espace recherchees (int id_e) {
        espace ev = null;
        String req = "SELECT * FROM espace WHERE id_espace = " + id_e;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                ev = new espace();
                ev.setId_espace(rs.getInt("id_espace"));
                ev.setNom_espace(rs.getString("nom_espace"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ev;
    }
//stat
    public ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            // Utilisez un Statement pour exécuter la requête SQL
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT nom_ev, capacite_max, COUNT(*) FROM evenement GROUP BY nom_ev, capacite_max")) {

                // Parcours des résultats et ajout des données au PieChart
                while (resultSet.next()) {
                    String nomEvenement = resultSet.getString("nom_ev");
                    int capacite_max = resultSet.getInt("capacite_max");
                    int nombreEvenements = resultSet.getInt(3); // Vous pouvez également utiliser le nom de la colonne "COUNT(*)"

                    PieChart.Data slice = new PieChart.Data(nomEvenement + " - Capacité " + capacite_max, nombreEvenements);
                    pieChartData.add(slice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }
///
public List<String> listcategorieevent() {
    List<type_ev> catList = listedecateve();
    List<String> nomcatevList = new ArrayList<>();

    for (type_ev categorie : catList) {
        nomcatevList.add(categorie.getType_ev());
    }

    return nomcatevList;
}
    public type_ev rechercherCateveParNom(String type_ev) {
        String req = "SELECT * FROM type_ev WHERE type_ev = '" + type_ev + "'";
        type_ev cat = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                cat=new type_ev(rs.getInt("id_typeev"), rs.getString("type_ev"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return cat;
    }
    public List<type_ev> listedecateve() {
        List<type_ev> catevList = new ArrayList<>();
        String req = "SELECT type_ev FROM type_ev";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                type_ev c = new type_ev();
                c.setType_ev(rs.getString("type_ev"));
                catevList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return catevList;
    }
    //espace
    public List<String> listespace() {
        List<espace> catesList = listedeespacedenom();
        List<String> nomespaceList = new ArrayList<>();

        for (espace c : catesList) {
            nomespaceList.add(c.getNom_espace());
        }

        return nomespaceList;
    }
    public espace rechercherespaceparnom(String nom_espace) {
        String req = "SELECT * FROM espace WHERE nom_espace = '" + nom_espace + "'";
        espace es = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                es =new espace(rs.getInt("id_espace"),rs.getString("nom_espace"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return es;
    }
    public List<espace> listedeespacedenom() {
        List<espace> esList = new ArrayList<>();
        String req = "SELECT nom_espace FROM espace";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                espace c = new espace();
                c.setNom_espace(rs.getString("nom_espace"));
                esList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return esList;
    }



}
