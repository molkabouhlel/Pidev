package Services;

import Interfaces.Interface;
import Models.Admin;
import Models.Coach;
import Models.Membre;
import Models.User;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Utils.Connexion;


public class UserServices implements Interface<User> {

Connection cnx =Connexion.getInstance().getCnx();
    @Override
    public void add(User u) {

            System.out.println("Adding user: " + u);
            String req="";
            if (u.getRole().equals("Admin")) {

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
                    userList.add(new User(rs.getInt("cin"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("numT"), role));
                } else if ("Coach".equals(role)) {
                    userList.add(new Coach(rs.getInt("cin"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("numT"), role, rs.getString("abonnement")));
                } else if ("Membre".equals(role)) {
                    userList.add(new Membre(rs.getInt("cin"), rs.getString("email"), rs.getString("mdp"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("numT"), role, rs.getString("adresse")));
                }
                // Handle other roles or skip if necessary
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    }


























