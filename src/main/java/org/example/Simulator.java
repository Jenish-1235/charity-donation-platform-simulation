package org.example;

import org.example.service.TestQueryService;
import org.example.service.UserFlowService;
import org.example.service.CharityFlowService;

import java.sql.Connection;
import java.sql.SQLException;
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
                    UserFlowService.runUserFlow(sc, conn);
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
        boolean running = true;

        while (running) {
            System.out.println("""
                
                 Test Queries Menu:
                1.  Total Donations per Campaign
                2.  Average Donation by Donor Segment
                3.  Fundraisers Nearing Goals
                4.  Receipt Issuance Status
                5.  Donor Acquisition Trends
                6.  Back to Main Menu
                """);

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> TestQueryService.topCampaignsByTotalDonation(conn);

                    case "2" -> {
                        System.out.println("""
                            Segment by:
                            a. Age
                            b. Gender
                            c. Income Range
                            """);
                        System.out.print("Choose segment (a/b/c): ");
                        String segment = sc.nextLine().trim().toLowerCase();
                        switch (segment) {
                            case "a" -> TestQueryService.avgDonationByAge(conn);
                            case "b" -> TestQueryService.avgDonationByGender(conn);
                            case "c" -> TestQueryService.avgDonationByIncomeRange(conn);
                            default -> System.out.println("❌ Invalid segment choice.");
                        }
                    }

                    case "3" -> TestQueryService.fundraisersNearGoal(conn);

                    case "4" -> {
                        System.out.println("""
                            Receipt Status For:
                            a. Campaign Transactions
                            b. Fundraiser Transactions
                            c. Direct Donations
                            """);
                        System.out.print("Choose option (a/b/c): ");
                        String receiptChoice = sc.nextLine().trim().toLowerCase();
                        switch (receiptChoice) {
                            case "a" -> TestQueryService.campaignReceiptStatus(conn);
                            case "b" -> TestQueryService.fundraiserReceiptStatus(conn);
                            case "c" -> TestQueryService.directDonationReceiptStatus(conn);
                            default -> System.out.println("❌ Invalid receipt choice.");
                        }
                    }

                    case "5" -> {
                        System.out.println("""
                            Acquisition Trend Type:
                            a. By Referrer Source
                            b. Daily Signup Trend
                            c. Monthly Signup Trend
                            """);
                        System.out.print("Choose trend type (a/b/c): ");
                        String trend = sc.nextLine().trim().toLowerCase();
                        switch (trend) {
                            case "a" -> TestQueryService.donorSourceBreakdown(conn);
                            case "b" -> TestQueryService.donorAcquisitionByDay(conn);
                            case "c" -> TestQueryService.donorAcquisitionByMonth(conn);
                            default -> System.out.println("❌ Invalid trend choice.");
                        }
                    }

                    case "6" -> running = false;

                    default -> System.out.println("❌ Invalid choice. Try again.");
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Error executing query: " + e.getMessage());
            }
        }
    }



    private static void runCharityFlow(Scanner sc, Connection conn) {
        CharityFlowService.runCharityFlow(sc, conn);
    }
}

