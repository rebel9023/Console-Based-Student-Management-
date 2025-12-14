package com.sms;

import com.sms.dao.StudentDaoMemoryImpl;
import com.sms.service.StudentService;
import com.sms.ui.ConsoleUI;

/**
 * Main application entry point for Student Management System.
 * Initializes the application with in-memory DAO (Phase 1).
 * Can be switched to JDBC DAO (Phase 2) by modifying the DAO initialization.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class App {

    /**
     * Main method - entry point of the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting Student Management System Application");

        try {
            com.sms.dao.StudentDao studentDao = null;

            try {
                // Initializing Database Schema
                System.out.println("Initializing database connection...");
                com.sms.util.DatabaseInitializer.initialize();

                // Phase 2: Initialize with JDBC DAO (Database persistence)
                studentDao = new com.sms.dao.StudentDaoJdbcImpl();
                System.out.println("Database mode initialized successfully.");

            } catch (Exception dbEx) {
                System.err.println("WARNING: Failed to connect to database: " + dbEx.getMessage());
                System.err.println(
                        "Check application.properties credentials. Falling back to In-Memory mode (data will not be saved).");

                // Fallback to Phase 1: In-Memory DAO
                studentDao = new StudentDaoMemoryImpl();
            }

            // Initialize service layer
            StudentService studentService = new StudentService(studentDao);

            // Initialize and start console UI
            ConsoleUI ui = new ConsoleUI(studentService);
            ui.start();

            System.out.println("Application terminated normally");

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
