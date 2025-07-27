package org.example.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {

    private static final String DEFAULT_DB_NAME = "charity_sim";
    private static final String HOSTED_DB_URL = "jdbc:mysql://jenish.site:3306/" + DEFAULT_DB_NAME;
    private static final String HOSTED_DB_USER = "root";
    private static final String HOSTED_DB_PASS = "root";

    public static Connection getLocalDbConnection(String url, String username, String password) throws SQLException {
        try {
            try (Connection localConn = DriverManager.getConnection(url, username, password);
                 Statement statement = localConn.createStatement()) {
                statement.execute("CREATE DATABASE IF NOT EXISTS " + DEFAULT_DB_NAME);
                return DriverManager.getConnection(url + DEFAULT_DB_NAME, username, password);
            }
        }
        catch (SQLException e) {
            System.out.println("Local Database connection error. Falling back to hosted connection");
            e.printStackTrace();
            return getHostedDbConnection();
        }
    }

    public static Connection getHostedDbConnection() throws SQLException {
        try {
            return DriverManager.getConnection(HOSTED_DB_URL, HOSTED_DB_USER, HOSTED_DB_PASS);
        } catch (SQLException e) {
            System.out.println("Failed to connect to hosted database.");
            throw e;
        }
    }

}
