package com.esprit.Services;

import com.esprit.Interfaces.Interface;
import  com.esprit.Models.Admin;
import  com.esprit.Models.Coach;
import  com.esprit.Models.Membre;
import  com.esprit.Models.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import  com.esprit.Utils.*;
import javafx.scene.control.TextField;


public class UserServices implements Interface<User> {

    Connection cnx =Connexion.getInstance().getCnx();

    public boolean login(String email, String password) throws SQLException {
        String query = "SELECT * FROM usr WHERE email = '" + email + "' AND mdp = '" + password + "'";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            return rs.next(); // Returns true if there's at least one result, indicating a successful login
        }
    }

    @Override
    public  void add(User u) {

        System.out.println("Adding user: " + u);
        String req="";
        if (u.getRole().equals("Admin")) {
            Admin admin = (Admin) u;
            req = "INSERT INTO usr ( email, mdp, nom, prenom, numT,role,abonnement,adresse) VALUES ('" + u.getEmail() + "','" + u.getMdp() + "','" + u.getNom() + "','" + u.getPrénom() + "','" + u.getNumT() + "','" + u.getRole() + "', '', '');";
        }
        else   if (u.getRole().equals("Membre")) {
            Membre membre = (Membre) u; // Casting to Membre
            req = "INSERT INTO usr ( email, mdp, nom, prenom, numT,role,abonnement,adresse) VALUES ( '" + u.getEmail() + "','" + u.getMdp() + "','" + u.getNom() + "','" + u.getPrénom() + "','" + u.getNumT() + "','" + u.getRole() + "', '"+((Membre) u).getAbonnement()+"', '');";

        } else if (u.getRole().equals("Coach")) {
            Coach coach = (Coach) u; // Casting to Coach
            req = "INSERT INTO usr ( email, mdp, nom, prenom, numT,role,abonnement,adresse) VALUES ('" + u.getEmail() + "','" + u.getMdp() + "','" + u.getNom() + "','" + u.getPrénom() + "','" + u.getNumT() + "','" + u.getRole() + "', '','"+((Coach) u).getAdresse()+"');";

        }

        System.out.println("Generated SQL query: " + req);

        try (Statement st = cnx.createStatement()) {
            st.executeUpdate(req);

            System.out.println("User added successfully");
        }
        catch (SQLException ex) {
            System.err.println("Error during SQL operation: " + ex.getMessage());
        }
    }

    @Override
    public void update(User user) {

    }


    public void updateAdmin(User admin) {
        System.out.println("updating admin: " + admin);
        String req = "UPDATE usr SET email = ?, mdp = ?, nom = ?, prenom = ?, numT = ?, role = ? WHERE cin = ?";

        System.out.println("Generated SQL query: " + req);

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, admin.getEmail());
            st.setString(2, admin.getMdp());
            st.setString(3, admin.getNom());
            st.setString(4, admin.getPrénom());
            st.setInt(5, admin.getNumT());
            st.setString(6, admin.getRole());
            st.setInt(7, admin.getCin());

            st.executeUpdate();

            System.out.println("Admin updated successfully");
        } catch (SQLException ex) {
            System.err.println("Error during SQL operation: " + ex.getMessage());
        }
    }






    public void updateMembre(Membre membre) {
        System.out.println("updating coach: " + membre);
        String req = "UPDATE usr SET email = ?, mdp = ?, nom = ?, prenom = ?, numT = ?, role = ? WHERE cin = ?";

        System.out.println("Generated SQL query: " + req);

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, membre.getEmail());
            st.setString(2, membre.getMdp());
            st.setString(3, membre.getNom());
            st.setString(4, membre.getPrénom());
            st.setInt(5, membre.getNumT());
            st.setString(6, membre.getRole());
            st.setInt(7, membre.getCin());

            st.executeUpdate();

            System.out.println("Admin updated successfully");
        } catch (SQLException ex) {
            System.err.println("Error during SQL operation: " + ex.getMessage());
        }
    }



    public void updateCoach(Coach coach) {
        System.out.println("updating coach: " + coach);
        String req = "UPDATE usr SET email = ?, mdp = ?, nom = ?, prenom = ?, numT = ?, role = ? WHERE cin = ?";

        System.out.println("Generated SQL query: " + req);

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, coach.getEmail());
            st.setString(2, coach.getMdp());
            st.setString(3, coach.getNom());
            st.setString(4, coach.getPrénom());
            st.setInt(5, coach.getNumT());
            st.setString(6, coach.getRole());
            st.setInt(7, coach.getCin());

            st.executeUpdate();

            System.out.println("Admin updated successfully");
        } catch (SQLException ex) {
            System.err.println("Error during SQL operation: " + ex.getMessage());
        }
    }


    @Override
    public void supp(User u) {

        String req = "DELETE from usr where cin = " + u.getCin() + ";";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("user supprimer supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<User> show() {
        List<User> userList = new ArrayList<>();

        String req = "SELECT * from usr";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                String role = rs.getString("role");

                if ("Admin".equals(role)) {
                    User user = new User(rs.getInt("cin"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("numT"), role);
                    userList.add(user);
                } else if ("Coach".equals(role)) {
                    System.out.println(rs.getString(("adresse")));
                    String s=rs.getString("adresse");
                    Coach user = new Coach(rs.getInt("cin"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("numT"), role, s);
                    userList.add(user);
                } else if ("Membre".equals(role)) {
                    System.out.println(rs.getString(("abonnement")));
                    String s=rs.getString("abonnement");
                    Membre user = new Membre(rs.getInt("cin"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("numT"), role, s);
                    userList.add(user);
                } else {
                    // Handle other roles or skip if necessary
                    continue;
                }


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }


    public List<User> showAdmin() {
        List<User> userList = new ArrayList<>();

        String req = "SELECT * FROM usr WHERE role = 'Admin'";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                int cin = rs.getInt("cin");
                String email = rs.getString("email");
                String password = rs.getString("mdp");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int numT = rs.getInt("numT");
                String role = rs.getString("role");

                User admin = new User(cin, email, password, nom, prenom, numT, role);
                userList.add(admin);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    public List<Membre> showMembre() {
        List<Membre> userList = new ArrayList<>();

        String req = "SELECT * FROM usr WHERE role = 'Membre'";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                int cin = rs.getInt("cin");
                String email = rs.getString("email");
                String password = rs.getString("mdp");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int numT = rs.getInt("numT");
                String role = rs.getString("role");

                Membre admin = new Membre(cin, email, password, nom, prenom, numT, role,"");
                userList.add(admin);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }
    public Object rechercheUser(int id) {
        Object user = null;
        String req = "SELECT * FROM usr WHERE cin = " + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                if(((rs.getString("role")).equals("Admin"))){
                    User admin = new User();
                    admin.setCin(rs.getInt("cin"));
                    admin.setNom(rs.getString("nom"));
                    admin.setPrénom(rs.getString("prenom"));
                    admin.setNumT(rs.getInt("numT"));
                    admin.setRole(rs.getString("role"));
                    return admin;




                } else if (((rs.getString("role")).equals("Coach"))) {
                    Coach coach  = new Coach();
                    coach.setCin(rs.getInt("cin"));
                    coach.setNom(rs.getString("nom"));
                    coach.setPrénom(rs.getString("prenom"));
                    coach.setNumT(rs.getInt("numT"));
                    coach.setRole(rs.getString("role"));
                    coach.setAdresse(rs.getString("adresse"));
                    return coach;



                } else if (((rs.getString("role")).equals("Membre")))
                {
                    Membre membre = new Membre();
                    membre.setCin(rs.getInt("cin"));
                    membre.setNom(rs.getString("nom"));
                    membre.setPrénom(rs.getString("prenom"));
                    membre.setNumT(rs.getInt("numT"));
                    membre.setRole(rs.getString("role"));
                    membre.setAbonnement(rs.getString("abonnement"));
                    return membre;

                }  else{


                    throw new IllegalArgumentException("Unsupported role: " + rs.getString("role"));
                }
            }
        } catch (SQLException eq) {
            System.out.println(eq.getMessage());
        }
        return user;
    }

    public List<Coach> showCoach() {
        List<Coach> coachList = new ArrayList<>();

        String req = "SELECT * FROM usr WHERE role = 'Coach'";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                int cin = rs.getInt("cin");
                String email = rs.getString("email");
                String password = rs.getString("mdp");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int numT = rs.getInt("numT");
                String role = rs.getString("role");
                String adresse = rs.getString("adresse");

                // Use the Coach constructor
                Coach coach = new Coach(cin, email, password, nom, prenom, numT, role, adresse);
                coachList.add(coach);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return coachList;
    }



    public User getUserByEmail(String userEmail) {
        String query = "SELECT * FROM usr WHERE email = '" + userEmail + "'";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                String role = rs.getString("role");
                int cin = rs.getInt("cin");
                String email = rs.getString("email");
                String password = rs.getString("mdp");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int numT = rs.getInt("numT");

                switch (role) {
                    case "Admin":
                        return new User(cin, email, password, nom, prenom, numT, role);
                    case "Membre":
                        String abonnement = rs.getString("abonnement");
                        return new Membre(cin, email, password, nom, prenom, numT, role, abonnement);
                    case "Coach":
                        String adresse = rs.getString("adresse");
                        return new Coach(cin, email, password, nom, prenom, numT, role, adresse);
                    // Add other cases if needed for different roles
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return null; // Return null if user not found
    }
    public User findemail(String email)
    {
        User s=new User();
        try {
            String req="SELECT * FROM usr WHERE `email`='"+email+"'";
            Statement stat=cnx.createStatement();
            ResultSet res=stat.executeQuery(req);
            while(res.next())
            {

                s.setCin(res.getInt("cin"));
                s.setNom(res.getString("nom"));
                s.setPrénom(res.getString("prenom"));
                s.setNumT(res.getInt("NumT"));
                s.setEmail(res.getString("email"));
                s.setMdp(res.getString("mdp"));
                s.setRole(res.getString("role"));


                System.out.println("Found");
                System.out.println(s);}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return s;
    }
    public void updatepassword(User u) {
        String req = "UPDATE usr SET mdp = ? WHERE cin = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,u.getMdp());
            ps.setInt(2,u.getCin());
            ps.executeUpdate();
            System.out.println("mot de passe modifié!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


























