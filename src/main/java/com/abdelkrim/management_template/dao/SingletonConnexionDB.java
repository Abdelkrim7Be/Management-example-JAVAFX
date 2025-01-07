package com.abdelkrim.management_template.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnexionDB {

    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/employes_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private SingletonConnexionDB() {}

    public static Connection getConnexion() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("On est connecté à la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Une erreur s'est produite lors de la connexion à la BD");
            }
        }
        return connection;
    }
}
