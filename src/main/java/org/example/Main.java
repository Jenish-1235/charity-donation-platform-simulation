package org.example;

import org.example.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println("Simulating Charity Donation Platform");

        Scanner sc = new Scanner(System.in);
        Connection conn = null;

        try {
            System.out.println("Choose : \n" +
                    "1. Use local Database. (Will create schema and add dummy data locally.) \n" +
                    "2. Use hostede Database. (Schema and dummy data already setup in cloud.) \n");
            System.out.println("Enter your choice (1 or 2) : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter Database url: ");
                    String url = sc.next();
                    System.out.println("Enter Database username: ");
                    String username = sc.next();
                    System.out.println("Enter Database password: ");
                    String password = sc.next();

                    conn = ConnectionUtil.getLocalDbConnection(url, username, password);
                    break;
                case 2:
                    System.out.println("Connecting to existing database... \n" +
                            "info : This database already consists of schema created and dummy data inserted.");
                    conn = ConnectionUtil.getHostedDbConnection();
                    break;
                default:
                    System.out.println("Wrong choice. Exiting.");
                    return;

            }
        }
        catch (SQLException e){
            System.out.println("Database connection error. Please try again.");
        }

    }

    private static void simulateSystem(Scanner sc, Connection conn) {
        System.out.println("CLI Implementation will go here.");
    }
}