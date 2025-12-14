package com.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    private static final String DB_NAME = "student_management_system";

    // Core Schema Definition
    private static final String CREATE_STUDENTS_TABLE = "CREATE TABLE IF NOT EXISTS students (" +
            "    student_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique student identifier'," +
            "    first_name VARCHAR(50) NOT NULL COMMENT 'First name of the student'," +
            "    last_name VARCHAR(50) NOT NULL COMMENT 'Last name of the student'," +
            "    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'Unique email address'," +
            "    phone_number VARCHAR(20) COMMENT 'Contact phone number'," +
            "    date_of_birth DATE COMMENT 'Date of birth of the student'," +
            "    address VARCHAR(255) COMMENT 'Street address'," +
            "    city VARCHAR(50) COMMENT 'City name'," +
            "    state VARCHAR(50) COMMENT 'State or province'," +
            "    zip_code VARCHAR(20) COMMENT 'Postal code'," +
            "    enrollment_date DATE NOT NULL COMMENT 'Date when student enrolled'," +
            "    enrollment_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'Current enrollment status'," +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
            "    KEY idx_email (email)," +
            "    KEY idx_active (enrollment_status)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

    public static void initialize() throws SQLException {
        String url = DatabaseUtil.getDbUrl();
        String user = DatabaseUtil.getDbUsername();
        String password = DatabaseUtil.getDbPassword();

        // 1. Create Database if strictly necessary (checking connectivity first)
        // We parse the base URL to connect without the DB name first
        String baseUrl = url;
        if (url.contains("?")) {
            baseUrl = url.substring(0, url.indexOf("?"));
        }
        int lastSlash = baseUrl.lastIndexOf("/");
        if (lastSlash < 0) {
            logger.warn("Database URL '{}' does not contain a path separator. Skipping automatic database creation.",
                    url);
            return;
        }
        String serverUrl = baseUrl.substring(0, lastSlash);
        String params = url.contains("?") ? url.substring(url.indexOf("?")) : "";

        // Try to connect to server specifically to create DB
        String adminUrl = serverUrl + params;

        try (Connection conn = DriverManager.getConnection(adminUrl, user, password);
                Statement stmt = conn.createStatement()) {

            logger.info("Connected to database server. Checking database '{}'...", DB_NAME);
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            logger.info("Database '{}' checked/created successfully.", DB_NAME);

        } catch (SQLException e) {
            logger.error("Failed to connect to database server to create DB. " +
                    "Please ensure MySQL is running and credentials are correct.\n" +
                    "URL: " + adminUrl + "\nUser: " + user);
            throw e;
        }

        // 2. Connect to the specific database and create tables
        try (Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement()) {

            logger.info("Initializing schema for database '{}'...", DB_NAME);
            stmt.executeUpdate(CREATE_STUDENTS_TABLE);
            logger.info("Schema initialized successfully.");

        } catch (SQLException e) {
            logger.error("Failed to initialize schema.", e);
            throw e;
        }
    }
}
