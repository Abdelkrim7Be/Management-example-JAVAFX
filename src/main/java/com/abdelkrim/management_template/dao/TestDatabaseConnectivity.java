package com.abdelkrim.management_template.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabaseConnectivity {

    public static void main(String[] args) {
        // Attempt to get a connection to the database
        Connection connection = SingletonConnexionDB.getConnexion();

        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
