package com.sms.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database utility class for managing database connections.
 * Uses try-with-resources for automatic resource management.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class DatabaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;
    private static String dbDriver;

    static {
        loadProperties();
    }

    /**
     * Loads database properties from application.properties file.
     */
    private static void loadProperties() {
        Properties props = new Properties();
        try (InputStream input = DatabaseUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                props.load(input);
                dbUrl = props.getProperty("db.url");
                if (dbUrl == null)
                    dbUrl = props.getProperty("spring.datasource.url");

                dbUsername = props.getProperty("db.username");
                if (dbUsername == null)
                    dbUsername = props.getProperty("spring.datasource.username");

                dbPassword = props.getProperty("db.password");
                if (dbPassword == null)
                    dbPassword = props.getProperty("spring.datasource.password");

                dbDriver = props.getProperty("db.driver");
                if (dbDriver == null)
                    dbDriver = props.getProperty("spring.datasource.driver-class-name");

                // Use defaults if still not configured
                if (dbUrl == null) {
                    logger.warn("Database URL not found in properties. Using defaults.");
                    setDefaults();
                } else {
                    // Update defaults if loaded successfully (for other methods that might rely on
                    // them)
                    // But here we just proceed.

                    // Load JDBC driver
                    if (dbDriver == null)
                        dbDriver = "com.mysql.cj.jdbc.Driver"; // Default driver if url is present but driver is not

                    try {
                        Class.forName(dbDriver);
                        logger.info("Database driver loaded successfully: {}", dbDriver);
                    } catch (ClassNotFoundException e) {
                        logger.error("Failed to load database driver: {}", dbDriver, e);
                    }
                }
            } else {
                logger.warn("application.properties file not found. Using default configuration.");
                setDefaults();
            }
        } catch (IOException e) {
            logger.error("Error loading application.properties", e);
            setDefaults();
        }
    }

    /**
     * Sets default database configuration if properties file is not found.
     */
    private static void setDefaults() {
        dbUrl = "jdbc:mysql://localhost:3306/student_management_system?useSSL=false&allowPublicKeyRetrieval=true";
        dbUsername = "root";
        dbPassword = "password";
        dbDriver = "com.mysql.cj.jdbc.Driver";
    }

    /**
     * Gets a database connection using try-with-resources pattern.
     * The connection should be used in a try-with-resources statement.
     * 
     * @return a new database connection
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            logger.debug("Database connection established successfully");
            return connection;
        } catch (SQLException e) {
            logger.error("Failed to establish database connection", e);
            throw new SQLException("Database connection failed: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if database connection is available.
     * 
     * @return true if connection is available, false otherwise
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            logger.info("Database connection test successful");
            return true;
        } catch (SQLException e) {
            logger.error("Database connection test failed", e);
            return false;
        }
    }

    /**
     * Gets the database URL.
     * 
     * @return the database URL
     */
    public static String getDbUrl() {
        return dbUrl;
    }

    /**
     * Sets the database URL (for testing purposes).
     * 
     * @param url the database URL to set
     */
    public static void setDbUrl(String url) {
        dbUrl = url;
    }

    /**
     * Gets the database username.
     * 
     * @return the database username
     */
    public static String getDbUsername() {
        return dbUsername;
    }

    /**
     * Sets the database username (for testing purposes).
     * 
     * @param username the database username to set
     */
    public static void setDbUsername(String username) {
        dbUsername = username;
    }

    /**
     * Gets the database password.
     * 
     * @return the database password
     */
    public static String getDbPassword() {
        return dbPassword;
    }

    /**
     * Sets the database password (for testing purposes).
     * 
     * @param password the database password to set
     */
    public static void setDbPassword(String password) {
        dbPassword = password;
    }

    /**
     * Gets the database driver class name.
     * 
     * @return the database driver
     */
    public static String getDbDriver() {
        return dbDriver;
    }

    /**
     * Sets the database driver (for testing purposes).
     * 
     * @param driver the database driver to set
     */
    public static void setDbDriver(String driver) {
        dbDriver = driver;
    }
}
