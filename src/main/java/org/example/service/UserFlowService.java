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
        System.out.println("=== Donor Registration ===");
        
        // Basic registration
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
        int age;
        while (true) {
            try {
                age = sc.nextInt();
                sc.nextLine(); // consume newline
                break;
            } catch (Exception e) {
                System.out.println("❌ Please enter a valid age (number).");
                sc.nextLine(); // clear the invalid input
            }
        }
        System.out.println("Enter your gender: ");
        String gender = sc.nextLine();
        System.out.println("Enter your income range: ");
        String incomeRange = sc.nextLine();
        System.out.println("Enter your referrer source: ");
        String referrerSource = sc.nextLine();

        DonorDao donorDao = new DonorDao(conn);
        boolean success = donorDao.registerDonor(name, email, password, city, state, country, age, gender, incomeRange, referrerSource);

        if (success) {
            System.out.println("✅ Registration successful!");
            Donor donor = donorDao.getDonorByEmailAndPassword(email, password);
            if (donor != null) {
                // Setup contact preferences
                setupContactPreferences(sc, conn, donor);
                // Setup payment preferences
                setupPaymentPreferences(sc, conn, donor);
                // Show main menu
                showDonorMenu(sc, conn, donor);
            }
        } else {
            System.out.println("❌ Registration failed.");
        }
    }

    private static void setupContactPreferences(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== Contact Preferences Setup ===");
        System.out.println("You need to set up at least one contact method for receipts and acknowledgments.");
        
        ContactMethodTypeDao contactMethodDao = new ContactMethodTypeDao(conn);
        List<ContactMethodType> availableMethods = contactMethodDao.getAllContactMethods();
        
        System.out.println("Available contact methods:");
        for (ContactMethodType method : availableMethods) {
            System.out.println(method.getId() + ". " + method.getMethod() + " - " + method.getDescription());
        }
        
        // Primary contact method (required)
        int primaryMethodId;
        while (true) {
            System.out.println("\nEnter the ID for your PRIMARY contact method (required): ");
            try {
                primaryMethodId = sc.nextInt();
                sc.nextLine(); // consume newline
                break;
            } catch (Exception e) {
                System.out.println("❌ Please enter a valid number.");
                sc.nextLine(); // clear the invalid input
            }
        }
        
        UserContactPreferenceDao contactPrefDao = new UserContactPreferenceDao(conn);
        contactPrefDao.createContactPreference(donor.getId(), primaryMethodId, true, "EN");
        System.out.println("✅ Primary contact method set!");
        
        // Secondary contact methods (optional)
        for (int i = 2; i <= 3; i++) {
            int methodId;
            while (true) {
                System.out.println("\nEnter the ID for contact method " + i + " (or 99 to skip): ");
                try {
                    methodId = sc.nextInt();
                    sc.nextLine(); // consume newline
                    break;
                } catch (Exception e) {
                    System.out.println("❌ Please enter a valid number.");
                    sc.nextLine(); // clear the invalid input
                }
            }
            
            if (methodId != 99) {
                contactPrefDao.createContactPreference(donor.getId(), methodId, true, "EN");
                System.out.println("✅ Contact method " + i + " set!");
            } else {
                System.out.println("Skipped contact method " + i);
                break;
            }
        }
    }

    private static void setupPaymentPreferences(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== Payment Preferences Setup ===");
        System.out.println("You need to set up at least one payment method for donations.");
        
        PaymentMethodTypeDao paymentMethodDao = new PaymentMethodTypeDao(conn);
        List<PaymentMethodType> availableMethods = paymentMethodDao.getAllPaymentMethods();
        
        System.out.println("Available payment methods:");
        for (PaymentMethodType method : availableMethods) {
            System.out.println(method.getId() + ". " + method.getMethod() + " (" + method.getProvider() + ")");
        }
        
        // Primary payment method (required)
        int primaryMethodId;
        while (true) {
            System.out.println("\nEnter the ID for your PRIMARY payment method (required): ");
            try {
                primaryMethodId = sc.nextInt();
                sc.nextLine(); // consume newline
                break;
            } catch (Exception e) {
                System.out.println("❌ Please enter a valid number.");
                sc.nextLine(); // clear the invalid input
            }
        }
        
        System.out.println("Enter last 4 digits of your account/card: ");
        String last4 = sc.nextLine();
        
        UserPaymentPreferenceDao paymentPrefDao = new UserPaymentPreferenceDao(conn);
        String token = "token_" + donor.getId() + "_" + System.currentTimeMillis();
        paymentPrefDao.createPaymentPreference(donor.getId(), primaryMethodId, last4, token, true);
        System.out.println("✅ Primary payment method registered!");
        
        // Secondary payment methods (optional)
        for (int i = 2; i <= 3; i++) {
            int methodId;
            while (true) {
                System.out.println("\nEnter the ID for payment method " + i + " (or 99 to skip): ");
                try {
                    methodId = sc.nextInt();
                    sc.nextLine(); // consume newline
                    break;
                } catch (Exception e) {
                    System.out.println("❌ Please enter a valid number.");
                    sc.nextLine(); // clear the invalid input
                }
            }
            
            if (methodId != 99) {
                System.out.println("Enter last 4 digits of your account/card: ");
                String last4Secondary = sc.nextLine();
                
                String tokenSecondary = "token_" + donor.getId() + "_" + i + "_" + System.currentTimeMillis();
                paymentPrefDao.createPaymentPreference(donor.getId(), methodId, last4Secondary, tokenSecondary, false);
                System.out.println("✅ Payment method " + i + " registered!");
            } else {
                System.out.println("Skipped payment method " + i);
                break;
            }
        }
    }

    private static void handleLogin(Scanner sc, Connection conn) {
        System.out.println("=== Donor Login ===");
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();

        DonorDao donorDao = new DonorDao(conn);
        Donor donor = donorDao.getDonorByEmailAndPassword(email, password);

        if (donor != null) {
            System.out.println("✅ Login successful!");
            showDonorMenu(sc, conn, donor);
        } else {
            System.out.println("❌ Invalid email or password.");
        }
    }

    private static void showDonorMenu(Scanner sc, Connection conn, Donor donor) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Donor Menu ===");
            System.out.println("1. Get profile info");
            System.out.println("2. See all charities");
            System.out.println("3. See all campaigns");
            System.out.println("4. See all fundraisers");
            System.out.println("5. Filter charity by category");
            System.out.println("6. Filter campaign by category");
            System.out.println("7. Filter fundraiser by category");
            System.out.println("8. See past donations (all)");
            System.out.println("9. See payment methods");
            System.out.println("10. See contact methods");
            System.out.println("11. Update contact method");
            System.out.println("12. Update payment method");
            System.out.println("13. Back to main menu");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleGetProfileInfo(conn, donor);
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
                    handleFilterCharitiesByCategory(sc, conn, donor);
                    break;
                case "6":
                    handleFilterCampaignsByCategory(sc, conn, donor);
                    break;
                case "7":
                    handleFilterFundraisersByCategory(sc, conn, donor);
                    break;
                case "8":
                    handleViewPastDonations(conn, donor);
                    break;
                case "9":
                    handleSeePaymentMethods(conn, donor);
                    break;
                case "10":
                    handleSeeContactMethods(conn, donor);
                    break;
                case "11":
                    handleUpdateContactMethod(sc, conn, donor);
                    break;
                case "12":
                    handleUpdatePaymentMethod(sc, conn, donor);
                    break;
                case "13":
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }

    private static void handleGetProfileInfo(Connection conn, Donor donor) {
        System.out.println("\n=== Profile Information ===");
        System.out.println("Name: " + donor.getName());
        System.out.println("Email: " + donor.getEmail());
        System.out.println("Location: " + donor.getCity() + ", " + donor.getState() + ", " + donor.getCountry());
        System.out.println("Age: " + donor.getAge());
        System.out.println("Gender: " + donor.getGender());
        System.out.println("Income Range: " + donor.getIncomeRange());
        System.out.println("Referrer Source: " + donor.getReferrerSource());
        if (donor.getLastDonatedAt() != null) {
            System.out.println("Last Donated: " + donor.getLastDonatedAt());
        }
    }

    private static void handleSeeAllCharities(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== All Charities ===");
        CharityDao charityDao = new CharityDao(conn);
        List<Charity> charities = charityDao.getAllCharities();
        
        if (charities.isEmpty()) {
            System.out.println("No charities found.");
            return;
        }
        
        for (Charity charity : charities) {
            System.out.println(charity.getId() + ". " + charity.getName());
            System.out.println("   Description: " + charity.getDescription());
            System.out.println("   Website: " + charity.getWebsiteUrl());
            System.out.println();
        }
        
        handleCharityDonation(sc, conn, donor, charities);
    }

    private static void handleSeeAllCampaigns(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== All Campaigns ===");
        CampaignDao campaignDao = new CampaignDao(conn);
        List<Campaign> campaigns = campaignDao.getActiveCampaigns();
        
        if (campaigns.isEmpty()) {
            System.out.println("No active campaigns found.");
            return;
        }
        
        for (Campaign campaign : campaigns) {
            System.out.println(campaign.getId() + ". " + campaign.getTitle());
            System.out.println("   Description: " + campaign.getDescription());
            System.out.println("   Period: " + campaign.getStartDate() + " to " + campaign.getEndDate());
            System.out.println();
        }
        
        handleCampaignDonation(sc, conn, donor, campaigns);
    }

    private static void handleSeeAllFundraisers(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== All Fundraisers ===");
        FundraiserDao fundraiserDao = new FundraiserDao(conn);
        List<Fundraiser> fundraisers = fundraiserDao.getAllFundraisers();
        
        if (fundraisers.isEmpty()) {
            System.out.println("No fundraisers found.");
            return;
        }
        
        for (Fundraiser fundraiser : fundraisers) {
            System.out.println(fundraiser.getId() + ". " + fundraiser.getTitle());
            System.out.println("   Description: " + fundraiser.getDescription());
            System.out.println("   Goal: $" + fundraiser.getGoalAmount() + " | Current: $" + fundraiser.getCurrentAmount());
            System.out.println("   Progress: " + String.format("%.1f%%", (fundraiser.getCurrentAmount() / fundraiser.getGoalAmount()) * 100));
            System.out.println();
        }
        
        handleFundraiserDonation(sc, conn, donor, fundraisers);
    }

    private static void handleFilterCharitiesByCategory(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== Filter Charities by Category ===");
        CharityCategoryDao categoryDao = new CharityCategoryDao(conn);
        List<CharityCategory> categories = categoryDao.getAllCharityCategories();
        
        System.out.println("Available charity categories:");
        for (CharityCategory category : categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }
        
        System.out.println("Enter category ID to filter: ");
        int categoryId = sc.nextInt();
        sc.nextLine();
        
        CharityDao charityDao = new CharityDao(conn);
        List<Charity> charities = charityDao.getCharitiesByCategory(categoryId);
        
        if (charities.isEmpty()) {
            System.out.println("No charities found in this category.");
            return;
        }
        
        System.out.println("\nCharities in category:");
        for (Charity charity : charities) {
            System.out.println(charity.getId() + ". " + charity.getName());
            System.out.println("   Description: " + charity.getDescription());
            System.out.println();
        }
        
        handleCharityDonation(sc, conn, donor, charities);
    }

    private static void handleFilterCampaignsByCategory(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== Filter Campaigns by Category ===");
        CategoryDao categoryDao = new CategoryDao(conn);
        List<Category> categories = categoryDao.getAllCategories();
        
        System.out.println("Available campaign categories:");
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }
        
        System.out.println("Enter category ID to filter: ");
        int categoryId = sc.nextInt();
        sc.nextLine();
        
        CampaignDao campaignDao = new CampaignDao(conn);
        List<Campaign> campaigns = campaignDao.getCampaignsByCategory(categoryId);
        
        if (campaigns.isEmpty()) {
            System.out.println("No campaigns found in this category.");
            return;
        }
        
        System.out.println("\nCampaigns in category:");
        for (Campaign campaign : campaigns) {
            System.out.println(campaign.getId() + ". " + campaign.getTitle());
            System.out.println("   Description: " + campaign.getDescription());
            System.out.println();
        }
        
        handleCampaignDonation(sc, conn, donor, campaigns);
    }

    private static void handleFilterFundraisersByCategory(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== Filter Fundraisers by Category ===");
        CategoryDao categoryDao = new CategoryDao(conn);
        List<Category> categories = categoryDao.getAllCategories();
        
        System.out.println("Available fundraiser categories:");
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }
        
        System.out.println("Enter category ID to filter: ");
        int categoryId = sc.nextInt();
        sc.nextLine();
        
        FundraiserDao fundraiserDao = new FundraiserDao(conn);
        List<Fundraiser> fundraisers = fundraiserDao.getFundraisersByCategory(categoryId);
        
        if (fundraisers.isEmpty()) {
            System.out.println("No fundraisers found in this category.");
            return;
        }
        
        System.out.println("\nFundraisers in category:");
        for (Fundraiser fundraiser : fundraisers) {
            System.out.println(fundraiser.getId() + ". " + fundraiser.getTitle());
            System.out.println("   Description: " + fundraiser.getDescription());
            System.out.println("   Goal: $" + fundraiser.getGoalAmount() + " | Current: $" + fundraiser.getCurrentAmount());
            System.out.println();
        }
        
        handleFundraiserDonation(sc, conn, donor, fundraisers);
    }

    private static void handleCharityDonation(Scanner sc, Connection conn, Donor donor, List<Charity> charities) {
        System.out.println("Enter the ID of the charity you want to donate to (or 0 to go back): ");
        int charityId = sc.nextInt();
        sc.nextLine();
        
        if (charityId == 0) return;
        
        // Validate charity exists
        Charity selectedCharity = charities.stream()
                .filter(c -> c.getId() == charityId)
                .findFirst()
                .orElse(null);
        
        if (selectedCharity == null) {
            System.out.println("❌ Invalid charity ID.");
            return;
        }
        
        processDonation(sc, conn, donor, "DIRECT", charityId, selectedCharity.getName());
    }

    private static void handleCampaignDonation(Scanner sc, Connection conn, Donor donor, List<Campaign> campaigns) {
        System.out.println("Enter the ID of the campaign you want to donate to (or 0 to go back): ");
        int campaignId = sc.nextInt();
        sc.nextLine();
        
        if (campaignId == 0) return;
        
        // Validate campaign exists
        Campaign selectedCampaign = campaigns.stream()
                .filter(c -> c.getId() == campaignId)
                .findFirst()
                .orElse(null);
        
        if (selectedCampaign == null) {
            System.out.println("❌ Invalid campaign ID.");
            return;
        }
        
        processDonation(sc, conn, donor, "CAMPAIGN", campaignId, selectedCampaign.getTitle());
    }

    private static void handleFundraiserDonation(Scanner sc, Connection conn, Donor donor, List<Fundraiser> fundraisers) {
        System.out.println("Enter the ID of the fundraiser you want to donate to (or 0 to go back): ");
        int fundraiserId = sc.nextInt();
        sc.nextLine();
        
        if (fundraiserId == 0) return;
        
        // Validate fundraiser exists
        Fundraiser selectedFundraiser = fundraisers.stream()
                .filter(f -> f.getId() == fundraiserId)
                .findFirst()
                .orElse(null);
        
        if (selectedFundraiser == null) {
            System.out.println("❌ Invalid fundraiser ID.");
            return;
        }
        
        processDonation(sc, conn, donor, "FUNDRAISER", fundraiserId, selectedFundraiser.getTitle());
    }

    private static void processDonation(Scanner sc, Connection conn, Donor donor, String donationType, int entityId, String entityName) {
        System.out.println("Enter the amount you want to donate: $");
        double amount = sc.nextDouble();
        sc.nextLine();
        
        if (amount <= 0) {
            System.out.println("❌ Invalid amount.");
            return;
        }
        
        // Get primary payment method
        UserPaymentPreferenceDao paymentPrefDao = new UserPaymentPreferenceDao(conn);
        UserPaymentPreference primaryPayment = paymentPrefDao.getPrimaryPaymentMethod(donor.getId());
        
        if (primaryPayment == null) {
            System.out.println("❌ No payment method found. Please set up a payment method first.");
            return;
        }
        
        // Get primary contact method
        UserContactPreferenceDao contactPrefDao = new UserContactPreferenceDao(conn);
        UserContactPreference primaryContact = contactPrefDao.getPrimaryContactMethod(donor.getId());
        
        if (primaryContact == null) {
            System.out.println("❌ No contact method found. Please set up a contact method first.");
            return;
        }
        
        // Process donation
        boolean success = false;
        switch (donationType) {
            case "DIRECT":
                DirectDonationDao directDao = new DirectDonationDao(conn);
                success = directDao.insertDonation(donor.getId(), entityId, amount, "PENDING", "", "");
                break;
            case "CAMPAIGN":
                CampaignTransactionDao campaignDao = new CampaignTransactionDao(conn);
                success = campaignDao.insertDonation(donor.getId(), entityId, amount, "PENDING", "", "");
                break;
            case "FUNDRAISER":
                FundraiserTransactionDao fundraiserDao = new FundraiserTransactionDao(conn);
                success = fundraiserDao.insertDonation(donor.getId(), entityId, amount, "PENDING", "", "");
                break;
        }
        
        if (success) {
            System.out.println("✅ Donation successful!");
            System.out.println("Receipt and acknowledgement will be sent to: " + getContactMethodName(conn, primaryContact.getMethodId()));
            System.out.println("Payment processed via: " + getPaymentMethodName(conn, primaryPayment.getPaymentMethodId()));
            
            // Update cache tables
            updateCacheTables(conn, donationType, entityId, amount);
            
            // Update donor's last donation time
            DonorDao donorDao = new DonorDao(conn);
            donorDao.updateLastDonatedAt(donor.getId());
        } else {
            System.out.println("❌ Donation failed.");
        }
    }

    private static String getContactMethodName(Connection conn, int methodId) {
        try {
            ContactMethodTypeDao dao = new ContactMethodTypeDao(conn);
            ContactMethodType method = dao.getContactMethodById(methodId);
            return method != null ? method.getMethod() : "Unknown";
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private static String getPaymentMethodName(Connection conn, int methodId) {
        try {
            PaymentMethodTypeDao dao = new PaymentMethodTypeDao(conn);
            PaymentMethodType method = dao.getPaymentMethodById(methodId);
            return method != null ? method.getMethod() + " (" + method.getProvider() + ")" : "Unknown";
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private static void updateCacheTables(Connection conn, String donationType, int entityId, double amount) {
        try {
            switch (donationType) {
                case "CAMPAIGN":
                    CampaignCacheDao cacheDao = new CampaignCacheDao(conn);
                    cacheDao.updateCampaignCache(entityId, amount);
                    break;
                case "FUNDRAISER":
                    FundraiserCacheDao fundraiserCacheDao = new FundraiserCacheDao(conn);
                    fundraiserCacheDao.updateFundraiserCache(entityId, amount);
                    break;
            }
        } catch (Exception e) {
            System.err.println("⚠️ Cache update failed: " + e.getMessage());
        }
    }

    private static void handleViewPastDonations(Connection conn, Donor donor) {
        System.out.println("\n=== Past Donations ===");
        
        try {
            // Direct donations
            DirectDonationDao directDao = new DirectDonationDao(conn);
            List<DirectDonation> directDonations = directDao.findByDonorId(donor.getId());
            if (!directDonations.isEmpty()) {
                System.out.println("Direct Donations:");
                for (DirectDonation d : directDonations) {
                    CharityDao charityDao = new CharityDao(conn);
                    Charity charity = charityDao.getCharityById(d.getCharityId());
                    String charityName = charity != null ? charity.getName() : "Unknown Charity";
                    System.out.println("  • " + charityName + " - $" + d.getAmount() + " (" + d.getReceiptStatus() + ")");
                }
            }
            
            // Campaign donations
            CampaignTransactionDao campaignDao = new CampaignTransactionDao(conn);
            List<CampaignTransaction> campaignDonations = campaignDao.getDonationsByDonor(donor.getId());
            if (!campaignDonations.isEmpty()) {
                System.out.println("Campaign Donations:");
                for (CampaignTransaction c : campaignDonations) {
                    CampaignDao campaignDao2 = new CampaignDao(conn);
                    Campaign campaign = campaignDao2.getCampaignById(c.getCampaignId());
                    String campaignName = campaign != null ? campaign.getTitle() : "Unknown Campaign";
                    System.out.println("  • " + campaignName + " - $" + c.getAmount() + " (" + c.getReceiptStatus() + ")");
                }
            }
            
            // Fundraiser donations
            FundraiserTransactionDao fundraiserDao = new FundraiserTransactionDao(conn);
            List<FundraiserTransaction> fundraiserDonations = fundraiserDao.getDonationsByDonor(donor.getId());
            if (!fundraiserDonations.isEmpty()) {
                System.out.println("Fundraiser Donations:");
                for (FundraiserTransaction f : fundraiserDonations) {
                    FundraiserDao fundraiserDao2 = new FundraiserDao(conn);
                    Fundraiser fundraiser = fundraiserDao2.getFundraiserById(f.getFundraiserId());
                    String fundraiserName = fundraiser != null ? fundraiser.getTitle() : "Unknown Fundraiser";
                    System.out.println("  • " + fundraiserName + " - $" + f.getAmount() + " (" + f.getReceiptStatus() + ")");
                }
            }
            
            if (directDonations.isEmpty() && campaignDonations.isEmpty() && fundraiserDonations.isEmpty()) {
                System.out.println("No past donations found.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error retrieving past donations: " + e.getMessage());
        }
    }

    private static void handleSeePaymentMethods(Connection conn, Donor donor) {
        System.out.println("\n=== Your Payment Methods ===");
        UserPaymentPreferenceDao paymentPrefDao = new UserPaymentPreferenceDao(conn);
        List<UserPaymentPreference> paymentMethods = paymentPrefDao.getPaymentMethodsByDonor(donor.getId());
        
        if (paymentMethods.isEmpty()) {
            System.out.println("No payment methods found.");
            return;
        }
        
        for (UserPaymentPreference payment : paymentMethods) {
            PaymentMethodTypeDao methodDao = new PaymentMethodTypeDao(conn);
            PaymentMethodType method = methodDao.getPaymentMethodById(payment.getPaymentMethodId());
            String methodName = method != null ? method.getMethod() + " (" + method.getProvider() + ")" : "Unknown";
            
            System.out.println("• " + methodName);
            System.out.println("  Account: ****" + payment.getAccountLast4());
            System.out.println("  Primary: " + (payment.isPrimary() ? "Yes" : "No"));
            System.out.println();
        }
    }

    private static void handleSeeContactMethods(Connection conn, Donor donor) {
        System.out.println("\n=== Your Contact Methods ===");
        UserContactPreferenceDao contactPrefDao = new UserContactPreferenceDao(conn);
        List<UserContactPreference> contactMethods = contactPrefDao.getContactMethodsByDonor(donor.getId());
        
        if (contactMethods.isEmpty()) {
            System.out.println("No contact methods found.");
            return;
        }
        
        for (UserContactPreference contact : contactMethods) {
            ContactMethodTypeDao methodDao = new ContactMethodTypeDao(conn);
            ContactMethodType method = methodDao.getContactMethodById(contact.getMethodId());
            String methodName = method != null ? method.getMethod() : "Unknown";
            
            System.out.println("• " + methodName);
            System.out.println("  Enabled: " + (contact.isEnabled() ? "Yes" : "No"));
            System.out.println("  Language: " + contact.getPreferredLanguage());
            System.out.println();
        }
    }

    private static void handleUpdateContactMethod(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== Update Contact Methods ===");
        System.out.println("This feature will be implemented in the next version.");
        System.out.println("For now, please contact support to update your contact preferences.");
    }

    private static void handleUpdatePaymentMethod(Scanner sc, Connection conn, Donor donor) {
        System.out.println("\n=== Update Payment Methods ===");
        System.out.println("This feature will be implemented in the next version.");
        System.out.println("For now, please contact support to update your payment preferences.");
    }
}
