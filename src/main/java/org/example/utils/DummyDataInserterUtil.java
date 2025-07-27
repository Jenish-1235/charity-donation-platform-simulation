package org.example.utils;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DummyDataInserterUtil {

    public static void execute(Connection conn) throws SQLException {
        System.out.println("Inserting data...");
        populate(conn);
    }

    private static final int NUM_DONORS = 1000;
    private static final int NUM_CHARITIES = 100;
    private static final int NUM_CAMPAIGNS = 300;
    private static final int NUM_FUNDRAISERS = 300;
    private static final int NUM_TRANSACTIONS = 3000;
    private static final Random random = new Random();

    public static void populate(Connection conn) throws SQLException {
        insertCategories(conn);
        insertCharityCategories(conn);
        insertContactMethodTypes(conn);
        insertPaymentMethodTypes(conn);
        insertEntityTypes(conn);

        insertDonors(conn);
        insertCharities(conn);
        insertCampaigns(conn);
        insertFundraisers(conn);
        insertCampaignTransactions(conn);
        insertFundraiserTransactions(conn);
        insertDirectDonations(conn);
        insertPaymentPreferences(conn);
        insertRecurringDonations(conn);
        insertContactPreferences(conn);
        insertDonorDonationSummary(conn);
        insertCampaignCache(conn);
        insertFundraiserCache(conn);

        System.out.println("✅ Dummy data population completed for all tables.");
    }

    private static void insertCategories(Connection conn) throws SQLException {
        String sql = "INSERT INTO category (name, description) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 5; i++) {
                stmt.setString(1, "Category " + i);
                stmt.setString(2, "General cause " + i);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertCharityCategories(Connection conn) throws SQLException {
        String sql = "INSERT INTO charity_category (name, description) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 5; i++) {
                stmt.setString(1, "Charity Category " + i);
                stmt.setString(2, "Charity cause " + i);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertContactMethodTypes(Connection conn) throws SQLException {
        String sql = "INSERT INTO contact_method_type (method, description) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String[] methods = {"Email", "SMS", "Push"};
            for (String method : methods) {
                stmt.setString(1, method);
                stmt.setString(2, "Used for " + method);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertPaymentMethodTypes(Connection conn) throws SQLException {
        String sql = "INSERT INTO payment_method_type (method, provider, requires_token, callback_url) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "CreditCard");
            stmt.setString(2, "Visa");
            stmt.setBoolean(3, true);
            stmt.setString(4, "http://callback.com/visa");
            stmt.addBatch();

            stmt.setString(1, "UPI");
            stmt.setString(2, "GooglePay");
            stmt.setBoolean(3, false);
            stmt.setString(4, "http://callback.com/gpay");
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    private static void insertEntityTypes(Connection conn) throws SQLException {
        String sql = "INSERT INTO entity_type (id, type) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 0);
            stmt.setString(2, "DIRECT");
            stmt.addBatch();
            stmt.setInt(1, 1);
            stmt.setString(2, "CAMPAIGN");
            stmt.addBatch();
            stmt.setInt(1, 2);
            stmt.setString(2, "FUNDRAISER");
            stmt.addBatch();
            stmt.executeBatch();
        }
    }

    private static void insertDonors(Connection conn) throws SQLException {
        String sql = "INSERT INTO donor (name, email, password, city, state, country, age, gender, income_range, referrer_source, last_donated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_DONORS; i++) {
                stmt.setString(1, "Donor" + i);
                stmt.setString(2, "donor" + i + "@example.com");
                stmt.setString(3, "password" + i);
                stmt.setString(4, "City" + (i % 50));
                stmt.setString(5, "State" + (i % 10));
                stmt.setString(6, "CountryX");
                stmt.setInt(7, 18 + random.nextInt(50));
                stmt.setString(8, i % 2 == 0 ? "Male" : "Female");
                stmt.setString(9, (i % 3 == 0 ? "<10k" : i % 3 == 1 ? "10k-50k" : ">50k"));
                stmt.setString(10, "Referral" + (i % 4));
                stmt.setTimestamp(11, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertCharities(Connection conn) throws SQLException {
        String sql = "INSERT INTO charity (category_id, name, email, password, description, website_url, ack_url, receipt_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_CHARITIES; i++) {
                stmt.setInt(1, (i % 5) + 1);
                stmt.setString(2, "Charity" + i);
                stmt.setString(3, "charity" + i + "@org.com");
                stmt.setString(4, "charpass" + i);
                stmt.setString(5, "Helping cause " + i);
                stmt.setString(6, "http://charity" + i + ".org");
                stmt.setString(7, "http://ack.org/" + i);
                stmt.setString(8, "http://receipt.org/" + i);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertCampaigns(Connection conn) throws SQLException {
        String sql = "INSERT INTO campaign (charity_id, category_id, title, description, rec_url, ack_url, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_CAMPAIGNS; i++) {
                stmt.setInt(1, (i % NUM_CHARITIES) + 1);
                stmt.setInt(2, (i % 5) + 1);
                stmt.setString(3, "Campaign Title " + i);
                stmt.setString(4, "Campaign description " + i);
                stmt.setString(5, "http://rec.com/" + i);
                stmt.setString(6, "http://ack.com/" + i);
                stmt.setDate(7, Date.valueOf(LocalDate.now().minusDays(30)));
                stmt.setDate(8, Date.valueOf(LocalDate.now().plusDays(60)));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertFundraisers(Connection conn) throws SQLException {
        String sql = "INSERT INTO fundraiser (charity_id, category_id, title, description, goal_amount, current_amount, rec_url, ack_url, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_FUNDRAISERS; i++) {
                stmt.setInt(1, (i % NUM_CHARITIES) + 1);
                stmt.setInt(2, (i % 5) + 1);
                stmt.setString(3, "Fundraiser Title " + i);
                stmt.setString(4, "Fundraiser description " + i);
                stmt.setDouble(5, 10000 + random.nextInt(10000));
                stmt.setDouble(6, 1000 + random.nextInt(9000));
                stmt.setString(7, "http://rec.com/" + i);
                stmt.setString(8, "http://ack.com/" + i);
                stmt.setDate(9, Date.valueOf(LocalDate.now().minusDays(15)));
                stmt.setDate(10, Date.valueOf(LocalDate.now().plusDays(90)));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertCampaignTransactions(Connection conn) throws SQLException {
        String sql = "INSERT INTO campaign_transactions (donor_id, campaign_id, amount, receipt_status, receipt_url, ack_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_TRANSACTIONS; i++) {
                stmt.setInt(1, (i % NUM_DONORS) + 1);
                stmt.setInt(2, (i % NUM_CAMPAIGNS) + 1);
                stmt.setDouble(3, 100 + random.nextInt(900));
                stmt.setString(4, i % 2 == 0 ? "ISSUED" : "PENDING");
                stmt.setString(5, "http://receipt.com/camp/" + i);
                stmt.setString(6, "http://acknowledge.com/camp/" + i);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertFundraiserTransactions(Connection conn) throws SQLException {
        String sql = "INSERT INTO fundraiser_transactions (donor_id, fundraiser_id, amount, receipt_status, receipt_url, ack_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_TRANSACTIONS; i++) {
                stmt.setInt(1, (i % NUM_DONORS) + 1);
                stmt.setInt(2, (i % NUM_FUNDRAISERS) + 1);
                stmt.setDouble(3, 50 + random.nextInt(1000));
                stmt.setString(4, i % 3 == 0 ? "ISSUED" : "PENDING");
                stmt.setString(5, "http://receipt.com/fund/" + i);
                stmt.setString(6, "http://acknowledge.com/fund/" + i);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertDirectDonations(Connection conn) throws SQLException {
        String sql = "INSERT INTO direct_donations (donor_id, charity_id, amount, receipt_status, receipt_url, ack_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_TRANSACTIONS; i++) {
                stmt.setInt(1, (i % NUM_DONORS) + 1);
                stmt.setInt(2, (i % NUM_CHARITIES) + 1);
                stmt.setDouble(3, 100 + random.nextInt(1000));
                stmt.setString(4, i % 2 == 0 ? "ISSUED" : "PENDING");
                stmt.setString(5, "http://receipt.com/direct/" + i);
                stmt.setString(6, "http://ack.com/direct/" + i);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertRecurringDonations(Connection conn) throws SQLException {
        String sql = "INSERT INTO recurring_donation (donor_id, entity_type_id, entity_id, charity_id, recurring_rate, amount, last_installment, next_installment, start_date, end_date, is_active, primary_payment_method_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 500; i++) {
                int donorId = i;
                int paymentId = donorPaymentMap.getOrDefault(donorId, 1); // fallback to 1 if missing

                stmt.setInt(1, donorId);
                stmt.setInt(2, (i % 3)); // 0: DIRECT, 1: CAMPAIGN, 2: FUNDRAISER
                stmt.setInt(3, (i % NUM_CAMPAIGNS) + 1); // entity_id
                stmt.setInt(4, (i % NUM_CHARITIES) + 1); // charity_id
                stmt.setString(5, "MONTHLY");
                stmt.setDouble(6, 100 + random.nextInt(400));
                stmt.setDate(7, Date.valueOf(LocalDate.now().minusMonths(1)));
                stmt.setDate(8, Date.valueOf(LocalDate.now().plusMonths(1)));
                stmt.setDate(9, Date.valueOf(LocalDate.now().minusMonths(6)));
                stmt.setDate(10, Date.valueOf(LocalDate.now().plusMonths(6)));
                stmt.setBoolean(11, true);
                stmt.setInt(12, paymentId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }


    private static void insertContactPreferences(Connection conn) throws SQLException {
        String sql = "INSERT INTO user_contact_preference (donor_id, method_id, is_enabled, preferred_language) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_DONORS; i++) {
                stmt.setInt(1, i);
                stmt.setInt(2, 1);
                stmt.setBoolean(3, i % 2 == 0);
                stmt.setString(4, "EN");
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    private static final Map<Integer, Integer> donorPaymentMap = new HashMap<>();

    private static void insertPaymentPreferences(Connection conn) throws SQLException {
        String sql = "INSERT INTO user_payment_preference (donor_id, payment_method_id, account_last4, provider_token, is_primary) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 1; i <= NUM_DONORS; i++) {
                stmt.setInt(1, i);
                stmt.setInt(2, 1); // e.g., CreditCard
                stmt.setString(3, "1234");
                stmt.setString(4, "token_" + i);
                stmt.setBoolean(5, true);
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int paymentId = generatedKeys.getInt(1);
                        donorPaymentMap.put(i, paymentId);
                    } else {
                        throw new SQLException("⚠️ Failed to retrieve payment_id for donor " + i);
                    }
                }
            }
        }
    }


    private static void insertDonorDonationSummary(Connection conn) throws SQLException {
        String sql = "INSERT INTO donor_donation_summary (donor_id, donation_type, total_amount, donation_count) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_DONORS; i++) {
                stmt.setInt(1, i);
                stmt.setString(2, "CAMPAIGN");
                stmt.setDouble(3, 500 + random.nextInt(1000));
                stmt.setInt(4, 5 + random.nextInt(10));
                stmt.addBatch();

                stmt.setInt(1, i);
                stmt.setString(2, "FUNDRAISER");
                stmt.setDouble(3, 400 + random.nextInt(900));
                stmt.setInt(4, 3 + random.nextInt(7));
                stmt.addBatch();

                stmt.setInt(1, i);
                stmt.setString(2, "DIRECT");
                stmt.setDouble(3, 300 + random.nextInt(700));
                stmt.setInt(4, 2 + random.nextInt(6));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertCampaignCache(Connection conn) throws SQLException {
        String sql = "INSERT INTO campaign_cache (campaign_id, total_donations, donor_count, last_donation_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_CAMPAIGNS; i++) {
                stmt.setInt(1, i);
                stmt.setDouble(2, 1000 + random.nextInt(5000));
                stmt.setInt(3, 5 + random.nextInt(100));
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void insertFundraiserCache(Connection conn) throws SQLException {
        String sql = "INSERT INTO fundraiser_cache (fundraiser_id, total_amount, donor_count) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= NUM_FUNDRAISERS; i++) {
                stmt.setInt(1, i);
                stmt.setDouble(2, 500 + random.nextInt(5000));
                stmt.setInt(3, 3 + random.nextInt(50));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

}
