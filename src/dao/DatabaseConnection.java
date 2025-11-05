package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/movie_ticket_system";
    private static final String USER = "movie_user";
    private static final String PASSWORD = "movie_pass";
    
    static {
        try {
            // Load the MySQL JDBC driver quietly
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Please add mysql-connector-java-8.0.x.jar to your build path.");
            System.err.println("Download from: https://dev.mysql.com/downloads/connector/j/");
        }
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection:");
            System.err.println("URL: " + URL);
            System.err.println("User: " + USER);
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test FAILED: " + e.getMessage());
            
            // Provide troubleshooting tips
            if (e.getMessage().contains("Access denied")) {
                System.err.println("Troubleshooting:");
                System.err.println("1. Check if username/password is correct");
                System.err.println("2. Verify user has privileges: GRANT ALL ON movie_ticket_system.* TO 'movie_user'@'localhost'");
            } else if (e.getMessage().contains("Unknown database")) {
                System.err.println("Troubleshooting:");
                System.err.println("1. Create database: CREATE DATABASE movie_ticket_system");
            } else if (e.getMessage().contains("Communications link failure")) {
                System.err.println("Troubleshooting:");
                System.err.println("1. Make sure MySQL server is running");
                System.err.println("2. Check if MySQL is listening on port 3306");
            }
            
            return false;
        }
    }
}