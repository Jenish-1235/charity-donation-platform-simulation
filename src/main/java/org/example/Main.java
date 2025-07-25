package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static Connection connection;

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        System.out.println("Connected to database successfully");


        System.out.println("This is simulation schema operations of charity donation platform using core java and jdbc");



        while (true) {
            System.out.println("Launch simulation");

            System.out.println("Enter 1 to start simulation");
            System.out.println("Enter 2 to exit simulation");
            int choice = sc.nextInt();
            if (choice == 1) {

                // TODO : Option to create tables
                // TODO : Option to exit


            }else if (choice == 2) {
                break;
            }
        }
        System.out.println("Thanks !");
    }
}