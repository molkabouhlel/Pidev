package com.esprit.services;

import com.esprit.models.Cours;
import com.esprit.models.Participer;
import com.esprit.services.IService;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class participerService implements IService<Participer> {

        private Connection connection;
        private Participer c =new Participer();
        public participerService() {
            connection = DataSource.getInstance().getConnection();
        }
        @Override
        public void ajouter(Participer c) {
            String req = "INSERT into participant (idcours,nomCours,nom, prenom, email ,numT,id_mbr) values ('" + c.getIdcours().getId() + "', '"+ c.getNomCours()+"', '" + c.getNom() + "','" + c.getPrenom() + "', '" + c.getEmail() + "', '"+c.getNumT()+"','"+c.getId_mbr()+"');";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("participant ajoutée !");
            } catch (SQLException ev) {
                System.out.println(ev.getMessage());
            }
        }



        @Override
        public void modifier(Participer c) {
            String req = "UPDATE participant set nomCours = '"+c.getNomCours()+"' where id = " + c.getId() + ";";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("participant modifiée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        @Override
        public void supprimer(Participer c) {
            String req = "DELETE from participant where id = " + c.getId() + ";";
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("couur supprmiée !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        @Override
        public List<Participer> afficher() {

           List<Participer> c = new ArrayList<>();

          /*  String req = "SELECT * from participant";
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {
                    Cours id_c = rechercheCours(rs.getInt("id"));

                    c.add(new Participer(rs.getInt("id"),id_c,rs.getString("nomCours"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }*/

            return c;
        }

        //combobox
        /*public Cours rechercheCours(int id) {
            Cours C = null;
            String req = "SELECT * FROM cours WHERE id= " + id;
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(req);
                if (rs.next()) {
                    C = new Cours();
                    CoursService c=new CoursService();
                    typec id_typec = c.recherchetypec(rs.getInt("id_typec"));
                    C.setId(rs.getInt("id"));
                    C.setNom(rs.getString("nom"));
                    C.setDescription(rs.getString("description"));
                    C.setDuree(rs.getTime("duree"));
                    C.setIdcoach(rs.getInt("idcoach"));
                    C.setIdclub(rs.getInt("idclub"));
                    C.setId_typec(id_typec);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return C;
        }*/

//
   public List<Cours> listedecourdenom() {
        List<Cours> coursList = new ArrayList<>();
        String req = "SELECT nom FROM cours";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Cours cours = new Cours();
                cours.setNom(rs.getString("nom"));
                coursList.add(cours);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return coursList;
    }
    public Cours rechercherCoursParNom(String nomCours) {
        String req = "SELECT * FROM cours WHERE nom = '" + nomCours + "'";
      Cours cours = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                cours=new Cours(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("imagec"), rs.getTime("duree"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       

        return cours;
    }

    public List<String> listnomcours() {
        List<Cours> coursList = listedecourdenom();
        List<String> nomCoursList = new ArrayList<>();

        for (Cours cours : coursList) {
            nomCoursList.add(cours.getNom());
        }

        return nomCoursList;
    }

    public List<Participer> rechercheparticipant(String nom, String prenom) {
        List<Participer> participantList = new ArrayList<>();
        String req = "SELECT * FROM participant WHERE nom = '" + nom + "' AND prenom = '" + prenom + "'";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {

                participantList.add(new Participer(rs.getInt("id"),
                        null,
                        rs.getString("nomCours"),  // Ne pas inclure l'objet Cours
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("numT"),
                        rs.getInt("id_mbr")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return participantList;
    }
    public boolean utilisateurExiste(String nom, String prenom) {
        String req = "SELECT 1 FROM usr WHERE nom = '" + nom + "' AND prenom = '" + prenom + "' LIMIT 1";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            // Retourne true si au moins une ligne correspondante est trouvée, sinon retourne false
            return rs.next();

        } catch (SQLException e) {
            // Gérez les exceptions de manière appropriée, par exemple, loggez l'erreur ou renvoyez false
            System.out.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            return false;
        }
    }


}



