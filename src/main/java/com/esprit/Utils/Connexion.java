package com.esprit.Utils;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;
public class Connexion {
    static final String URL ="jdbc:mysql://localhost:3306/pidev";
    static final String USER ="root";
    static final String PASSWORD ="";


    private Connection cnx;
    static Connexion instance;


    private Connexion(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connected successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("mat3adech!");
        }
    }


    public Connection getCnx() {
        return cnx;
    }


    public static Connexion getInstance() {
        if(instance == null)
            instance = new Connexion();

        return instance;
    }


}
