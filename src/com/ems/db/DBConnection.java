package com.ems.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages a single JDBC connection to the MySQL database.
 * Update DB_URL, USER, and PASSWORD to match your environment.
 */
public class DBConnection {

    private static final String DB_URL  = "jdbc:mysql://localhost:3306/employee_management?useSSL=false&serverTimezone=UTC";
    private static final String USER     = "root";       // change if needed
    private static final String PASSWORD = "your_password_here"; // change to your MySQL password

    private static Connection connection = null;

    // Private constructor — utility class
    private DBConnection() {}

    /**
     * Returns a singleton Connection instance.
     * Re-opens the connection if it has been closed.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                System.out.println("[DB] Connected to MySQL successfully.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[DB] MySQL JDBC Driver not found. Place mysql-connector-j-*.jar in the lib/ folder.");
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("[DB] Connection failed: " + e.getMessage());
            System.exit(1);
        }
        return connection;
    }

    /** Closes the connection when the application exits. */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error closing connection: " + e.getMessage());
        }
    }
}
