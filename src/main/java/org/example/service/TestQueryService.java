package org.example.service;

import java.sql.*;

public class TestQueryService {

    public static void avgDonationByAge(Connection conn) throws SQLException {
        String sql = """
            SELECT d.age, s.donation_type, AVG(s.total_amount) AS avg_donation,
                   SUM(s.total_amount) AS total_donation, COUNT(*) AS donor_count
            FROM donor_donation_summary s
            JOIN donor d ON d.id = s.donor_id
            GROUP BY d.age, s.donation_type
            ORDER BY d.age, s.donation_type;
        """;
        runQuery(conn, sql);
    }

    public static void avgDonationByGender(Connection conn) throws SQLException {
        String sql = """
            SELECT d.gender, s.donation_type, AVG(s.total_amount) AS avg_donation,
                   SUM(s.total_amount) AS total_donation, COUNT(*) AS donor_count
            FROM donor_donation_summary s
            JOIN donor d ON d.id = s.donor_id
            GROUP BY d.gender, s.donation_type
            ORDER BY d.gender, s.donation_type;
        """;
        runQuery(conn, sql);
    }

    public static void avgDonationByIncomeRange(Connection conn) throws SQLException {
        String sql = """
            SELECT d.income_range, s.donation_type, AVG(s.total_amount) AS avg_donation,
                   SUM(s.total_amount) AS total_donation, COUNT(*) AS donor_count
            FROM donor_donation_summary s
            JOIN donor d ON d.id = s.donor_id
            GROUP BY d.income_range, s.donation_type
            ORDER BY d.income_range, s.donation_type;
        """;
        runQuery(conn, sql);
    }

    public static void campaignReceiptStatus(Connection conn) throws SQLException {
        String sql = """
            SELECT c.title, ct.receipt_status, COUNT(*) AS count
            FROM campaign_transactions ct
            JOIN campaign c ON ct.campaign_id = c.campaign_id
            GROUP BY c.title, ct.receipt_status
            ORDER BY c.title;
        """;
        runQuery(conn, sql);
    }

    public static void directDonationReceiptStatus(Connection conn) throws SQLException {
        String sql = """
            SELECT dd.charity_id, c.name AS charity_name, dd.receipt_status, COUNT(*) AS count
            FROM direct_donations dd
            JOIN charity c ON dd.charity_id = c.charity_id
            GROUP BY dd.charity_id, dd.receipt_status
            ORDER BY dd.charity_id, dd.receipt_status;
        """;
        runQuery(conn, sql);
    }

    public static void donorSourceBreakdown(Connection conn) throws SQLException {
        String sql = """
            SELECT referrer_source, COUNT(*) AS donor_count
            FROM donor
            GROUP BY referrer_source
            ORDER BY donor_count DESC;
        """;
        runQuery(conn, sql);
    }

    public static void donorAcquisitionByDay(Connection conn) throws SQLException {
        String sql = """
            SELECT DATE(created_at) AS signup_day, COUNT(*) AS new_donors
            FROM donor
            GROUP BY signup_day
            ORDER BY signup_day ASC;
        """;
        runQuery(conn, sql);
    }

    public static void donorAcquisitionByMonth(Connection conn) throws SQLException {
        String sql = """
            SELECT DATE_FORMAT(created_at, '%Y-%m') AS month, COUNT(*) AS new_donors
            FROM donor
            GROUP BY month
            ORDER BY month ASC;
        """;
        runQuery(conn, sql);
    }

    public static void fundraiserReceiptStatus(Connection conn) throws SQLException {
        String sql = """
            SELECT f.title, ft.receipt_status, COUNT(*) AS count
            FROM fundraiser_transactions ft
            JOIN fundraiser f ON ft.fundraiser_id = f.fundraiser_id
            GROUP BY f.title, ft.receipt_status
            ORDER BY f.title;
        """;
        runQuery(conn, sql);
    }

    public static void fundraisersNearGoal(Connection conn) throws SQLException {
        String sql = """
            SELECT f.fundraiser_id, f.title, f.start_date, f.end_date, f.goal_amount,
                   fc.total_amount AS raised_amount,
                   ROUND(fc.total_amount / f.goal_amount * 100, 2) AS progress_percent
            FROM fundraiser f
            JOIN fundraiser_cache fc ON f.fundraiser_id = fc.fundraiser_id
            WHERE f.is_active = TRUE
              AND f.goal_amount IS NOT NULL
              AND fc.total_amount >= 0.8 * f.goal_amount
            ORDER BY progress_percent DESC;
        """;
        runQuery(conn, sql);
    }

    public static void topCampaignsByTotalDonation(Connection conn) throws SQLException {
        String sql = """
            SELECT c.campaign_id, c.title, cc.total_donations, cc.donor_count
            FROM campaign c
            JOIN campaign_cache cc ON c.campaign_id = cc.campaign_id
            ORDER BY cc.total_donations DESC;
        """;
        runQuery(conn, sql);
    }

    private static void runQuery(Connection conn, String sql) throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();

            for (int i = 1; i <= columns; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println("\n" + "-".repeat(100));

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
            System.out.println("\n✅ Query executed.");
        }
    }
}
