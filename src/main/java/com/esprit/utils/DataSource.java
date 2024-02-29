package com.esprit.utils;

import java.sql.*;

public class DataSource {

    private Connection connection;
    private static DataSource instance;
    private final String URL = "jdbc:mysql://localhost:3306/pidev";
    private final String USER = "root";
    private final String PASSWORD = "";

    private DataSource() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            System.out.println("Connection a été établie");
        } catch (SQLException var2) {
            System.out.println(var2.getMessage());
            System.out.println("3awed galit , jarib 7adek mara o5ra hhhhh ");
        }

    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }

        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
