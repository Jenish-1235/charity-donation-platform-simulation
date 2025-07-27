package org.example;

import java.sql.Connection;
import java.util.Scanner;

public class Simulator {

    public static void execute(Scanner sc, Connection conn) {
        boolean running = true;

        while (running) {
            System.out.println("Simulator");
            System.out.println("1. Test Queries");
            System.out.println("2. User Flow");
            System.out.println("3. Charity Flow");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    runTestQueries(sc, conn);
                    break;
                case "2":
                    runUserFlow(sc, conn);
                    break;
                case "3":
                    runCharityFlow(sc, conn);
                    break;
                case "4":
                    System.out.println("Bye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }

    private static void runTestQueries(Scanner sc, Connection conn) {
        System.out.println("Running test queries...");
        // TODO: Add query executions like:
        // - top donors
        // - donation summary
        // - campaign progress
    }

    private static void runUserFlow(Scanner sc, Connection conn) {
        System.out.println("Starting user flow...");
        // TODO: Simulate:
        // - user registration/login
        // - view campaigns
        // - donate
        // - get receipt
    }

    private static void runCharityFlow(Scanner sc, Connection conn) {
        System.out.println("Starting charity flow...");
        // TODO: Simulate:
        // - create/manage campaigns
        // - view reports
        // - donor stats
    }
}

