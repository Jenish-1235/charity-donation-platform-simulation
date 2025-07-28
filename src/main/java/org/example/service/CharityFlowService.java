package org.example.service;

import org.example.dao.*;
import org.example.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CharityFlowService {

    public static void runCharityFlow(Scanner sc, Connection conn) {
        System.out.println("=== Charity Management System ===");
        System.out.println("1. Register Charity");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");

        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                handleCharityRegistration(sc, conn);
                break;
            case "2":
                handleCharityLogin(sc, conn);
                break;
            default:
                System.out.println("❌ Invalid choice.");
        }
    }

    private static void handleCharityRegistration(Scanner sc, Connection conn) {
        System.out.println("\n=== Charity Registration ===");
        
        System.out.println("Enter charity name: ");
        String name = sc.nextLine();
        System.out.println("Enter email: ");
        String email = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        System.out.println("Enter description: ");
        String description = sc.nextLine();
        System.out.println("Enter website URL: ");
        String websiteUrl = sc.nextLine();
        System.out.println("Enter acknowledgment URL: ");
        String ackUrl = sc.nextLine();
        System.out.println("Enter receipt URL: ");
        String receiptUrl = sc.nextLine();
        
        // Show available charity categories
        CharityCategoryDao categoryDao = new CharityCategoryDao(conn);
        List<CharityCategory> categories = categoryDao.getAllCharityCategories();
        
        System.out.println("\nAvailable charity categories:");
        for (CharityCategory category : categories) {
            System.out.println(category.getId() + ". " + category.getName() + " - " + category.getDescription());
        }
        
        System.out.println("Enter category ID: ");
        int categoryId = sc.nextInt();
        sc.nextLine(); // consume newline

        CharityDao charityDao = new CharityDao(conn);
        boolean success = charityDao.registerCharity(name, email, password, description, websiteUrl, ackUrl, receiptUrl, categoryId);

        if (success) {
            System.out.println("✅ Charity registration successful!");
            Charity charity = charityDao.getCharityByEmailAndPassword(email, password);
            if (charity != null) {
                showCharityDashboard(sc, conn, charity);
            }
        } else {
            System.out.println("❌ Charity registration failed.");
        }
    }

    private static void handleCharityLogin(Scanner sc, Connection conn) {
        System.out.println("\n=== Charity Login ===");
        System.out.println("Enter email: ");
        String email = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();

        CharityDao charityDao = new CharityDao(conn);
        Charity charity = charityDao.getCharityByEmailAndPassword(email, password);

        if (charity != null) {
            System.out.println("✅ Login successful!");
            showCharityDashboard(sc, conn, charity);
        } else {
            System.out.println("❌ Invalid email or password.");
        }
    }

    private static void showCharityDashboard(Scanner sc, Connection conn, Charity charity) {
        boolean running = true;
        while (running) {
            displayDashboardOverview(conn, charity);
            
            System.out.println("\n=== Charity Dashboard ===");
            System.out.println("1. View Profile & Settings");
            System.out.println("2. Campaign Management");
            System.out.println("3. Fundraiser Management");
            System.out.println("4. Donation Analytics");
            System.out.println("5. Financial Reports");
            System.out.println("6. Receipt Management");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleViewProfile(sc, conn, charity);
                    break;
                case "2":
                    CampaignManagementService.runCampaignManagement(sc, conn, charity);
                    break;
                case "3":
                    FundraiserManagementService.runFundraiserManagement(sc, conn, charity);
                    break;
                case "4":
                    DonationAnalyticsService.runDonationAnalytics(sc, conn, charity);
                    break;
                case "5":
                    FinancialReportsService.runFinancialReports(sc, conn, charity);
                    break;
                case "6":
                    ReceiptManagementService.runReceiptManagement(sc, conn, charity);
                    break;
                case "7":
                    System.out.println("✅ Logged out successfully!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private static void displayDashboardOverview(Connection conn, Charity charity) {
        System.out.println("\n=== Dashboard Overview ===");
        System.out.println("Welcome, " + charity.getName() + "!");
        
        try {
            // Get quick stats
            CampaignDao campaignDao = new CampaignDao(conn);
            FundraiserDao fundraiserDao = new FundraiserDao(conn);
            
            List<Campaign> activeCampaigns = campaignDao.getCampaignsByCharity(charity.getId());
            List<Fundraiser> allFundraisers = fundraiserDao.getFundraisersByCharity(charity.getId());
            
            long activeFundraisers = allFundraisers.stream().filter(Fundraiser::isActive).count();
            
            System.out.println("\n📊 Quick Stats:");
            System.out.println("• Active Campaigns: " + activeCampaigns.size());
            System.out.println("• Active Fundraisers: " + activeFundraisers);
            
            // Calculate total donations (simplified for now)
            double totalDonations = calculateTotalDonations(conn, charity.getId());
            System.out.println("• Total Donations: $" + String.format("%.2f", totalDonations));
            
            // Show recent activity
            System.out.println("\n🔄 Recent Activity:");
            showRecentActivity(conn, charity.getId());
            
        } catch (Exception e) {
            System.err.println("⚠️ Error loading dashboard data: " + e.getMessage());
        }
    }

    private static double calculateTotalDonations(Connection conn, int charityId) {
        double total = 0.0;
        try {
            // Direct donations
            DirectDonationDao directDao = new DirectDonationDao(conn);
            List<DirectDonation> directDonations = directDao.findByCharityId(charityId);
            for (DirectDonation donation : directDonations) {
                total += donation.getAmount().doubleValue();
            }
            
            // Campaign donations
            CampaignDao campaignDao = new CampaignDao(conn);
            List<Campaign> campaigns = campaignDao.getCampaignsByCharity(charityId);
            CampaignTransactionDao campaignTransactionDao = new CampaignTransactionDao(conn);
            
            for (Campaign campaign : campaigns) {
                List<CampaignTransaction> transactions = campaignTransactionDao.getDonationsByCampaign(campaign.getId());
                for (CampaignTransaction transaction : transactions) {
                    total += transaction.getAmount();
                }
            }
            
            // Fundraiser donations
            FundraiserDao fundraiserDao = new FundraiserDao(conn);
            List<Fundraiser> fundraisers = fundraiserDao.getFundraisersByCharity(charityId);
            FundraiserTransactionDao fundraiserTransactionDao = new FundraiserTransactionDao(conn);
            
            for (Fundraiser fundraiser : fundraisers) {
                List<FundraiserTransaction> transactions = fundraiserTransactionDao.getDonationsByFundraiser(fundraiser.getId());
                for (FundraiserTransaction transaction : transactions) {
                    total += transaction.getAmount();
                }
            }
            
        } catch (Exception e) {
            System.err.println("⚠️ Error calculating total donations: " + e.getMessage());
        }
        return total;
    }

    private static void showRecentActivity(Connection conn, int charityId) {
        try {
            // Show recent direct donations
            DirectDonationDao directDao = new DirectDonationDao(conn);
            List<DirectDonation> recentDirectDonations = directDao.findRecentByCharityId(charityId, 3);
            
            for (DirectDonation donation : recentDirectDonations) {
                DonorDao donorDao = new DonorDao(conn);
                Donor donor = donorDao.getDonorById(donation.getDonorId());
                String donorName = donor != null ? donor.getName() : "Anonymous";
                System.out.println("• Direct donation of $" + donation.getAmount() + " from " + donorName);
            }
            
            // Show recent campaign donations
            CampaignDao campaignDao = new CampaignDao(conn);
            List<Campaign> campaigns = campaignDao.getCampaignsByCharity(charityId);
            CampaignTransactionDao campaignTransactionDao = new CampaignTransactionDao(conn);
            
            for (Campaign campaign : campaigns) {
                List<CampaignTransaction> recentTransactions = campaignTransactionDao.getRecentDonationsByCampaign(campaign.getId(), 2);
                for (CampaignTransaction transaction : recentTransactions) {
                    DonorDao donorDao = new DonorDao(conn);
                    Donor donor = donorDao.getDonorById(transaction.getDonorId());
                    String donorName = donor != null ? donor.getName() : "Anonymous";
                    System.out.println("• Campaign donation of $" + transaction.getAmount() + " to '" + campaign.getTitle() + "' from " + donorName);
                }
            }
            
            // Show fundraiser progress
            FundraiserDao fundraiserDao = new FundraiserDao(conn);
            List<Fundraiser> fundraisers = fundraiserDao.getFundraisersByCharity(charityId);
            
            for (Fundraiser fundraiser : fundraisers) {
                if (fundraiser.isActive()) {
                    double progress = (fundraiser.getCurrentAmount() / fundraiser.getGoalAmount()) * 100;
                    if (progress >= 80) {
                        System.out.println("• Fundraiser '" + fundraiser.getTitle() + "' reached " + String.format("%.1f", progress) + "% of goal");
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("⚠️ Error loading recent activity: " + e.getMessage());
        }
    }

    private static void handleViewProfile(Scanner sc, Connection conn, Charity charity) {
        System.out.println("\n=== Charity Profile ===");
        System.out.println("Name: " + charity.getName());
        System.out.println("Email: " + charity.getEmail());
        System.out.println("Description: " + charity.getDescription());
        System.out.println("Website: " + charity.getWebsiteUrl());
        System.out.println("Acknowledgment URL: " + charity.getAckUrl());
        System.out.println("Receipt URL: " + charity.getReceiptUrl());
        System.out.println("Status: " + (charity.isActive() ? "Active" : "Inactive"));
        System.out.println("Created: " + charity.getCreatedAt());
        
        // Show category
        try {
            CharityCategoryDao categoryDao = new CharityCategoryDao(conn);
            List<CharityCategory> categories = categoryDao.getAllCharityCategories();
            for (CharityCategory category : categories) {
                if (category.getId() == charity.getCategoryId()) {
                    System.out.println("Category: " + category.getName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error loading category: " + e.getMessage());
        }
        
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }
}
