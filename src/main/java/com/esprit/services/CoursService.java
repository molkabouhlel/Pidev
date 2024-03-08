package com.esprit.services;

import com.esprit.models.*;
import com.esprit.services.IService;
import com.esprit.models.Coach;
import com.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements IService<Cours> {
    private Connection connection;
private typec t =new typec();
    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Cours c) {
        String req = "INSERT into cours (nom, description, imagec, duree, idcoach, idclub, id_typec) values ('" + c.getNom() + "',  '" + c.getDescription() + "','" + c.getImagec() + "', '" + c.getDuree() + "','" + c.getIdcoach().getCin() + "','" + c.getIdclub().getId_club() + "', '" + c.getId_typec().getIdtypec() +"');";
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
        String req = "UPDATE cours set nom = '" + c.getNom() + "', description = '" + c.getDescription() + "', imagec = '" + c.getImagec() + "', duree = '" + c.getDuree() + "', idcoach = '" + c.getIdcoach().getCin() + "', idclub='" + c.getIdclub().getId_club() + "',id_typec = '" + c.getId_typec().getIdtypec() + "' where id = " + c.getId() + ";";
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
                Club  id_club =rechercheclubaffich(rs.getInt("idclub"));
                Coach id_coach =rechercheCoachaffich(rs.getInt("idcoach"));
                c.add(new Cours(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("imagec"), rs.getTime("duree"), id_coach, id_club,id_typec));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return c;
    }
/////////////////
public typec recherchetypec (int id_typec) {
    typec evs = null;
    String req = "SELECT typecours FROM typec WHERE idtypec = " + id_typec;
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            evs = new typec();
           // evs.setIdtypec(rs.getInt("idtypec"));
            evs.setTypecours(rs.getString("typecours"));


        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return evs;
}
    public Club rechercheclub (int id_club) {
        Club evs = null;
        String req = "SELECT nom_club FROM club WHERE id_club = " + id_club;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                evs = new Club();
               //  evs.setId_club(rs.getInt("idclub"));
                evs.setId_club(rs.getInt("id_club"));
                evs.setNom_club(rs.getString("nom_club"));
               evs.setAdresse_club(rs.getString("adresse_club"));
                evs.setDescription_club(rs.getString("description_club"));
                evs.setImage_club(rs.getString("image_club"));
               evs.setTemp_ouverture(rs.getTime("temp_ouverture"));



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
                Club id_club =rechercheclub(rs.getInt("idclub"));
               Coach id_coach = rechercheCoach(rs.getInt("idcoach"));
                C.setId(rs.getInt("id"));
                C.setNom(rs.getString("nom"));
                C.setDescription(rs.getString("description"));
                C.setDuree(rs.getTime("duree"));
                C.setIdcoach(id_coach);
                C.setIdclub(id_club);
                C.setId_typec(id_typec);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return C;
    }
////
public List<Integer> recherchecoach() {
    List<Integer> idC = new ArrayList<>();
    String req = "SELECT * FROM usr";
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            int idcoach = rs.getInt("cin");
            idC.add(idcoach);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return idC;
}
  /*  public List<Integer> rechercheclub() {
        List<Integer> idCl = new ArrayList<>();
        String req = "SELECT id_club,nom_club FROM club";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_club = rs.getInt("id_club");
                String nomclub =rs.getString("nom_club");
                idCl.add(id_club);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return idCl;
    }*/
//stat
public ObservableList<PieChart.Data> contc() {
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    try {
        // Utilisez un Statement pour exécuter la requête SQL
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT nom, duree, COUNT(*) FROM cours GROUP BY nom, duree")) {

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
/////////////////sans id /////////////////////
public List<String> listcategorie() {
    List<typec> catList = listedecatdenom();
    List<String> nomCoursList = new ArrayList<>();

    for (typec categorie : catList) {
        nomCoursList.add(categorie.getTypecours());
    }

    return nomCoursList;
}
    public typec rechercherCatParNom(String typecours) {
        String req = "SELECT * FROM typec WHERE typecours = '" + typecours + "'";
        typec cat = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                cat=new typec(rs.getInt("idtypec"), rs.getString("typecours"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return cat;
    }
    public List<typec> listedecatdenom() {
        List<typec> catList = new ArrayList<>();
        String req = "SELECT typecours FROM typec";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                typec c = new typec();
                c.setTypecours(rs.getString("typecours"));
                catList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return catList;
    }
    //club
    public List<String> listclub() {
        List<Club> catList = listedeclubdenom();
        List<String> nomCoursList = new ArrayList<>();

        for (Club c : catList) {
            nomCoursList.add(c.getNom_club());
        }

        return nomCoursList;
    }
    public Club rechercherClubparnom(String nom_club) {
    EvenementService ev=new EvenementService();
        String req = "SELECT * FROM club WHERE nom_club = '" + nom_club + "'";
        Club cl = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Espace e=ev.recherchees(rs.getInt("id_espace"));
                cl=new Club(rs.getInt("id_club"),rs.getString("nom_club"),rs.getString("adresse_club"),rs.getString( "description_club"),rs.getString("image_club"),rs.getTime("temp_ouverture"),e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return cl;
    }
    public List<Club> listedeclubdenom() {
        List<Club> catList = new ArrayList<>();
        String req = "SELECT nom_club FROM club";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Club c = new Club();
                c.setNom_club(rs.getString("nom_club"));
                catList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return catList;
    }
//
public Club rechercheclubaffich (int id_club) {
    Club evs = null;
    String req = "SELECT *FROM club WHERE id_club = " + id_club;
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            evs = new Club();
            //  evs.setId_club(rs.getInt("idclub"));
            evs.setId_club(rs.getInt("id_club"));
            evs.setNom_club(rs.getString("nom_club"));



        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return evs;
}
//qr
public String getDescriptionAndDureeById(int coursId) {
    String req = "SELECT description, duree FROM cours WHERE id = " + coursId;
    try (Statement statement = connection.createStatement();
         ResultSet rs = statement.executeQuery(req)) {

        if (rs.next()) {
            String description = rs.getString("description");
            Time duree = rs.getTime("duree");

            // Retourne la description et la durée formatées
            return "Description: " + description + "\nDurée: " + duree.toString();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
//coach
public Coach rechercheCoachaffich(int cin) {
    Coach evs = null;
    String req = "SELECT * FROM usr WHERE cin = '" + cin + "' AND role = 'coach'";
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            evs = new Coach();
            evs.setCin(rs.getInt("cin"));
            evs.setNom(rs.getString("nom"));
            evs.setRole(rs.getString("role"));
            evs.setMdp(rs.getString("mdp"));
            evs.setAdresse(rs.getString("adresse"));
            evs.setEmail(rs.getString("email"));
            evs.setNumT(rs.getInt("numT"));
            evs.setPrénom(rs.getString("prenom"));


        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return evs;
}
public Coach rechercheCoach (int cin) {
    Coach evs = null;
    String req = "SELECT nom FROM usr WHERE cin = '" + cin + "' AND role = 'coach'";
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            evs = new Coach();
            evs.setCin(rs.getInt("cin"));
            evs.setNom(rs.getString("nom"));
            evs.setRole(rs.getString("role"));
            evs.setMdp(rs.getString("mdp"));
            evs.setAdresse(rs.getString("adresse"));
            evs.setEmail(rs.getString("email"));
            evs.setNumT(rs.getInt("numT"));
            evs.setPrénom(rs.getString("prenom"));


        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return evs;
}
//coach combo
public Coach rechercherCatParNomCoach(String nom) {
    String req = "SELECT * FROM usr WHERE nom = '" + nom + "' AND role = 'coach'";
    Coach coach = null;
    try {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        if (rs.next()) {
            coach = new Coach(
                    rs.getInt("cin"),
                    rs.getString("email"),
                    rs.getString("mdp"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("numT"),
                    rs.getString("role"),
                    rs.getString("adresse")
            );
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return coach;
}

    public List<String> listCoach() {
        List<Coach> coachList = listNomCoach();
        List<String> nomCoachList = new ArrayList<>();

        for (Coach coach : coachList) {
            nomCoachList.add(coach.getNom());
        }

        return nomCoachList;
    }

    public List<Coach> listNomCoach() {
        List<Coach> coachList = new ArrayList<>();
        String req = "SELECT nom FROM usr WHERE role = 'coach'";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Coach coach = new Coach();
                coach.setNom(rs.getString("nom"));
                coachList.add(coach);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return coachList;
    }

}
