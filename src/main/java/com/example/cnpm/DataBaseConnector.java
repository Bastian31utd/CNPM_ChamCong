package com.example.cnpm;

import java.sql.*;

public class DataBaseConnector {
    private static Connection connection;
    private final String databaseName;

    public DataBaseConnector() {
        this.databaseName = "database.db";
    }

    public void connect() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);

            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DataBaseConnector connector = new DataBaseConnector();

        // Connect to the database
        connector.connect();
    }
}
