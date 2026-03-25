package com.student.management;

import com.student.management.database.DatabaseConnection;
import com.student.management.database.DatabaseInitializer;
import com.student.management.ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("================================================================================");
        System.out.println("                                                                                ");
        System.out.println("                    INITIALIZING SYSTEM...                                      ");
        System.out.println("                                                                                ");
        System.out.println("================================================================================");
        System.out.println();

        try {
            // Initialize Database Connection
            System.out.println("  Connecting to database...");
            DatabaseConnection.getInstance();
            
            // Initialize Database Tables
            System.out.println("  Initializing database tables...");
            DatabaseInitializer.initializeDatabase();
            
            // Insert sample data (optional - only for first run)
            System.out.println("  Loading sample data...");
            DatabaseInitializer.insertSampleData();
            
            System.out.println("\n  System initialized successfully!");
            System.out.println("\n  Starting application...");
            
            // Small delay for effect
            Thread.sleep(1500);
            
            // Start the main menu
            MainMenu mainMenu = new MainMenu();
            mainMenu.displayMainMenu();
            
            // Close database connection on exit
            DatabaseConnection.getInstance().closeConnection();
            
        } catch (Exception e) {
            System.err.println("\n  ERROR: Failed to initialize system!");
            System.err.println("  Error details: " + e.getMessage());
            e.printStackTrace();
        }
    }
}