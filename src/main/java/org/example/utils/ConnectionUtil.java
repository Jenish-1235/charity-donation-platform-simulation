package org.example.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {

    public static Connection getConnection() throws SQLException {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS charity_sim");
            stmt.close();
            conn.close();
        }catch(SQLException e){
            System.out.println("Failed to create database");
            throw e;
        }
        return getDatabaseConnection();
    }
    public static Connection getDatabaseConnection() throws SQLException {
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/charity_sim", "root", "root");
        }catch (SQLException e){
            System.out.println("Failed to connect to database");
            throw e;
        }
    }
}
