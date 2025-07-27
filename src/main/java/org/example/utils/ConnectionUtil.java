package org.example.utils;


import org.example.model.DatabaseConnectionResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {

    private static final String DEFAULT_DB_NAME = "charity_sim";
    private static final String HOSTED_DB_URL = "jdbc:mysql://database-1.cmpa84ewe4i3.us-east-1.rds.amazonaws.com:3306/" + DEFAULT_DB_NAME;
    private static final String HOSTED_DB_USER = "root";
    private static final String HOSTED_DB_PASS = "root1234";

    public static DatabaseConnectionResult getLocalDbConnection(String url, String username, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            var rs = stmt.executeQuery("SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = '" + DEFAULT_DB_NAME + "'");
            boolean dbExists = rs.next();

            if (!dbExists) {
                System.out.println("⚙️ Creating database: " + DEFAULT_DB_NAME);
                stmt.execute("CREATE DATABASE " + DEFAULT_DB_NAME);
            } else {
                System.out.println("✅ Database '" + DEFAULT_DB_NAME + "' already exists.");
            }

            Connection dbConn = DriverManager.getConnection(url + DEFAULT_DB_NAME, username, password);
            return new DatabaseConnectionResult(dbConn, !dbExists);
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
