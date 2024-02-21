package com.esprit.Services;

import com.esprit.Interfaces.Interface;
import  com.esprit.Models.Admin;
import  com.esprit.Models.Coach;
import  com.esprit.Models.Membre;
import  com.esprit.Models.User;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import  com.esprit.Utils.*;


public class UserServices implements Interface<User> {

Connection cnx =Connexion.getInstance().getCnx();
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
        public void update(User u) {
            System.out.println("updating user: " + u);
            String req="";
            if (u.getRole().equals("Admin")) {

                req = "UPDATE usr set email = '" + u.getEmail() + "', mdp = '" + u.getMdp() + "' , nom = '" + u.getNom() + "' , prenom = '" + u.getPrénom() + "' , numT = '" + u.getNumT() + "' , role = '" + u.getRole() + "' , abonnement = '' , adresse = ''  where cin = '"+ u.getCin() + "'; ";
            }
            else   if (u.getRole().equals("Membre")) {
                Membre membre = (Membre) u; // Casting to Membre
                req = "UPDATE usr set email = '" + u.getEmail() + "', mdp = '" + u.getMdp() + "' , nom = '" + u.getNom() + "' , prenom = '" + u.getPrénom() + "' , numT = '" + u.getNumT() + "' , role = '" + u.getRole() + "' , abonnement = '" + ((Membre) u).getAbonnement() + "' , adresse = ''  where cin = '"+ u.getCin() + "'; ";

            } else if (u.getRole().equals("Coach")) {
                Coach coach = (Coach) u; // Casting to Coach
                req = "UPDATE usr set email = '" + u.getEmail() + "', mdp = '" + u.getMdp() + "' , nom = '" + u.getNom() + "' , prenom = '" + u.getPrénom() + "' , numT = '" + u.getNumT() + "' , role = '" + u.getRole() + "' , abonnement = '' , adresse = '" + ((Coach) u).getAdresse() + "'  where cin = '"+ u.getCin() + "'; ";

            }

            System.out.println("Generated SQL query: " + req);

            try (Statement st = cnx.createStatement()) {
                st.executeUpdate(req);

                System.out.println("User updated successfully");
            }
            catch (SQLException ex) {
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

    public User rechercheUser(int id) {
        User user = null;
        String req = "SELECT * FROM usr WHERE cin = " + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                user = new User();
                user.setCin(rs.getInt("cin"));
                user.setNom(rs.getString("nom"));
                user.setPrénom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setNumT(rs.getInt("numT"));
                user.setRole(rs.getString("role"));

                if ((rs.getString("role")).equals("Coach")) {
                    ((Coach) user).setAdresse(rs.getString("adresse"));
                } else if ((rs.getString("role")).equals("Membre")) {
                    ((Membre) user).setAbonnement(rs.getString("abonnement"));
                } else {
                    // Handle other roles or throw an exception, depending on your requirements
                    throw new IllegalArgumentException("Unsupported role: " + rs.getString("role"));
                }
            }
        } catch (SQLException eq) {
            System.out.println(eq.getMessage());
        }
        return user;
    }

}


























