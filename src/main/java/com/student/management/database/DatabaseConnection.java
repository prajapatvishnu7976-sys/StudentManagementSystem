package com.student.management.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private Properties properties;

    private DatabaseConnection() {
        try {
            properties = new Properties();
            // Try to load from resources, if not found use default values
            try {
                FileInputStream fis = new FileInputStream("src/resources/database.properties");
                properties.load(fis);
                fis.close();
            } catch (IOException e) {
                // Use default embedded database
                properties.setProperty("db.url", "jdbc:sqlite:studentdb.db");
                properties.setProperty("db.driver", "org.sqlite.JDBC");
            }

            String driver = properties.getProperty("db.driver", "org.sqlite.JDBC");
            Class.forName(driver);
            
            String url = properties.getProperty("db.url", "jdbc:sqlite:studentdb.db");
            connection = DriverManager.getConnection(url);
            
            System.out.println("✅ Database connection established successfully!");
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null || instance.getConnection() == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = properties.getProperty("db.url", "jdbc:sqlite:studentdb.db");
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.err.println("Error reconnecting to database: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}