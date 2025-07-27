package org.example.service;

import org.example.dao.*;
import org.example.model.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserFlowService {

    public static void runUserFlow(Scanner sc, Connection conn) {
        System.out.println("Welcome to the User Flow!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");

        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                handleRegistration(sc, conn);
                break;
            case "2":
                handleLogin(sc, conn);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void handleRegistration(Scanner sc, Connection conn) {
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();
        System.out.println("Enter your city: ");
        String city = sc.nextLine();
        System.out.println("Enter your state: ");
        String state = sc.nextLine();
        System.out.println("Enter your country: ");
        String country = sc.nextLine();
        System.out.println("Enter your age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.println("Enter your gender: ");
        String gender = sc.nextLine();
        System.out.println("Enter your income range: ");
        String incomeRange = sc.nextLine();
        System.out.println("Enter your referrer source: ");
        String referrerSource = sc.nextLine();

        DonorDao donorDao = new DonorDao(conn);
        boolean success = donorDao.registerDonor(name, email, password, city, state, country, age, gender, incomeRange, referrerSource);

        if (success) {
            System.out.println("Registration successful!");
            Donor donor = donorDao.getDonorByEmailAndPassword(email, password);
            if (donor != null) {
                // handle contact and payment preferences
                showDonorMenu(sc, conn, donor);
            }
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void handleLogin(Scanner sc, Connection conn) {
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();

        DonorDao donorDao = new DonorDao(conn);
        Donor donor = donorDao.getDonorByEmailAndPassword(email, password);

        if (donor != null) {
            System.out.println("Login successful!");
            showDonorMenu(sc, conn, donor);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static void showDonorMenu(Scanner sc, Connection conn, Donor donor) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Donor Menu ---");
            System.out.println("1. Make a direct donation");
            System.out.println("2. See all charities");
            System.out.println("3. See all campaigns");
            System.out.println("4. See all fundraisers");
            System.out.println("5. Filter campaigns by category");
            System.out.println("6. Filter fundraisers by category");
            System.out.println("7. View past donations");
            System.out.println("8. Go back");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleDirectDonation(sc, conn, donor);
                    break;
                case "2":
                    handleSeeAllCharities(sc, conn, donor);
                    break;
                case "3":
                    handleSeeAllCampaigns(sc, conn, donor);
                    break;
                case "4":
                    handleSeeAllFundraisers(sc, conn, donor);
                    break;
                case "5":
                    handleFilterCampaigns(sc, conn, donor);
                    break;
                case "6":
                    handleFilterFundraisers(sc, conn, donor);
                    break;
                case "7":
                    handleViewPastDonations(conn, donor);
                    break;
                case "8":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleDirectDonation(Scanner sc, Connection conn, Donor donor) {
        CharityDao charityDao = new CharityDao(conn);
        List<Charity> charities = charityDao.getAllCharities();
        for (Charity charity : charities) {
            System.out.println(charity.getId() + ". " + charity.getName());
        }
        System.out.println("Enter the ID of the charity you want to donate to: ");
        int charityId = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.println("Enter the amount you want to donate: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // consume newline

        DirectDonationDao directDonationDao = new DirectDonationDao(conn);
        DirectDonation donation = new DirectDonation(donor.getId(), charityId, BigDecimal.valueOf(amount), "pending", "", "");
        try {
            directDonationDao.create(donation);
            System.out.println("Donation successful!");
        } catch (SQLException e) {
            System.out.println("Donation failed: " + e.getMessage());
        }
    }

    private static void handleSeeAllCharities(Scanner sc, Connection conn, Donor donor) {
        CharityDao charityDao = new CharityDao(conn);
        List<Charity> charities = charityDao.getAllCharities();
        for (Charity charity : charities) {
            System.out.println(charity.getId() + ". " + charity.getName());
        }
        System.out.println("Enter the ID of the charity you want to donate to (or 0 to go back): ");
        int charityId = sc.nextInt();
        sc.nextLine(); // consume newline
        if (charityId != 0) {
            System.out.println("Enter the amount you want to donate: ");
            double amount = sc.nextDouble();
            sc.nextLine(); // consume newline

            DirectDonationDao directDonationDao = new DirectDonationDao(conn);
            DirectDonation donation = new DirectDonation(donor.getId(), charityId, BigDecimal.valueOf(amount), "pending", "", "");
            try {
                directDonationDao.create(donation);
                System.out.println("Donation successful!");
            } catch (SQLException e) {
                System.out.println("Donation failed: " + e.getMessage());
            }
        }
    }

    private static void handleSeeAllCampaigns(Scanner sc, Connection conn, Donor donor) {
        CampaignDao campaignDao = new CampaignDao(conn);
        List<Campaign> campaigns = campaignDao.getActiveCampaigns();
        for (Campaign campaign : campaigns) {
            System.out.println(campaign.getId() + ". " + campaign.getTitle());
        }
        System.out.println("Enter the ID of the campaign you want to donate to (or 0 to go back): ");
        int campaignId = sc.nextInt();
        sc.nextLine(); // consume newline
        if (campaignId != 0) {
            System.out.println("Enter the amount you want to donate: ");
            double amount = sc.nextDouble();
            sc.nextLine(); // consume newline

            CampaignTransactionDao campaignTransactionDao = new CampaignTransactionDao(conn);
            campaignTransactionDao.insertDonation(donor.getId(), campaignId, amount, "pending", "", "");
            System.out.println("Donation successful!");
        }
    }

    private static void handleSeeAllFundraisers(Scanner sc, Connection conn, Donor donor) {
        FundraiserDao fundraiserDao = new FundraiserDao(conn);
        List<Fundraiser> fundraisers = fundraiserDao.getAllFundraisers();
        for (Fundraiser fundraiser : fundraisers) {
            System.out.println(fundraiser.getId() + ". " + fundraiser.getTitle());
        }
        System.out.println("Enter the ID of the fundraiser you want to donate to (or 0 to go back): ");
        int fundraiserId = sc.nextInt();
        sc.nextLine(); // consume newline
        if (fundraiserId != 0) {
            System.out.println("Enter the amount you want to donate: ");
            double amount = sc.nextDouble();
            sc.nextLine(); // consume newline

            FundraiserTransactionDao fundraiserTransactionDao = new FundraiserTransactionDao(conn);
            fundraiserTransactionDao.insertDonation(donor.getId(), fundraiserId, amount, "pending", "", "");
            System.out.println("Donation successful!");
        }
    }

    private static void handleFilterCampaigns(Scanner sc, Connection conn, Donor donor) {
        CategoryDao categoryDao = new CategoryDao(conn);
        List<Category> categories = categoryDao.getAllCategories();
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }
        System.out.println("Enter the ID of the category to filter campaigns: ");
        int categoryId = sc.nextInt();
        sc.nextLine();
        CampaignDao campaignDao = new CampaignDao(conn);
        List<Campaign> campaigns = campaignDao.getCampaignsByCategory(categoryId);
        for (Campaign campaign : campaigns) {
            System.out.println(campaign.getId() + ". " + campaign.getTitle());
        }
        System.out.println("Enter the ID of the campaign you want to donate to (or 0 to go back): ");
        int campaignId = sc.nextInt();
        sc.nextLine();
        if (campaignId != 0) {
            System.out.println("Enter the amount you want to donate: ");
            double amount = sc.nextDouble();
            sc.nextLine();
            CampaignTransactionDao campaignTransactionDao = new CampaignTransactionDao(conn);
            campaignTransactionDao.insertDonation(donor.getId(), campaignId, amount, "pending", "", "");
            System.out.println("Donation successful!");
        }
    }

    private static void handleFilterFundraisers(Scanner sc, Connection conn, Donor donor) {
        CategoryDao categoryDao = new CategoryDao(conn);
        List<Category> categories = categoryDao.getAllCategories();
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }
        System.out.println("Enter the ID of the category to filter fundraisers: ");
        int categoryId = sc.nextInt();
        sc.nextLine();
        FundraiserDao fundraiserDao = new FundraiserDao(conn);
        List<Fundraiser> fundraisers = fundraiserDao.getFundraisersByCategory(categoryId);
        for (Fundraiser fundraiser : fundraisers) {
            System.out.println(fundraiser.getId() + ". " + fundraiser.getTitle());
        }
        System.out.println("Enter the ID of the fundraiser you want to donate to (or 0 to go back): ");
        int fundraiserId = sc.nextInt();
        sc.nextLine();
        if (fundraiserId != 0) {
            System.out.println("Enter the amount you want to donate: ");
            double amount = sc.nextDouble();
            sc.nextLine();
            FundraiserTransactionDao fundraiserTransactionDao = new FundraiserTransactionDao(conn);
            fundraiserTransactionDao.insertDonation(donor.getId(), fundraiserId, amount, "pending", "", "");
            System.out.println("Donation successful!");
        }
    }

    private static void handleViewPastDonations(Connection conn, Donor donor) {
        System.out.println("--- Past Donations ---");
        DirectDonationDao directDonationDao = new DirectDonationDao(conn);
        CampaignTransactionDao campaignTransactionDao = new CampaignTransactionDao(conn);
        FundraiserTransactionDao fundraiserTransactionDao = new FundraiserTransactionDao(conn);
        try {
            List<DirectDonation> directDonations = directDonationDao.findByDonorId(donor.getId());
            for (DirectDonation d : directDonations) {
                System.out.println("Direct: Charity ID " + d.getCharityId() + ", Amount: " + d.getAmount());
            }
        } catch (Exception ignored) {}
        try {
            List<CampaignTransaction> campaignDonations = campaignTransactionDao.getDonationsByDonor(donor.getId());
            for (CampaignTransaction c : campaignDonations) {
                System.out.println("Campaign: Campaign ID " + c.getCampaignId() + ", Amount: " + c.getAmount());
            }
        } catch (Exception ignored) {}
        try {
            List<FundraiserTransaction> fundraiserDonations = fundraiserTransactionDao.getDonationsByDonor(donor.getId());
            for (FundraiserTransaction f : fundraiserDonations) {
                System.out.println("Fundraiser: Fundraiser ID " + f.getFundraiserId() + ", Amount: " + f.getAmount());
            }
        } catch (Exception ignored) {}
    }
}
