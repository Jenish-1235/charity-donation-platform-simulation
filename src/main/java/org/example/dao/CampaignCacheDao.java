package org.example.dao;

import java.sql.*;

public class CampaignCacheDao {
    private final Connection conn;

    public CampaignCacheDao(Connection conn) {
        this.conn = conn;
    }

    public void incrementStats(int campaignId, double amount) {
        String sql = "INSERT INTO campaign_cache (campaign_id, total_donations, donor_count, last_donation_at) " +
                "VALUES (?, ?, 1, NOW()) " +
                "ON DUPLICATE KEY UPDATE total_donations = total_donations + VALUES(total_donations), " +
                "donor_count = donor_count + 1, last_donation_at = NOW()";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, campaignId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating campaign cache: " + e.getMessage());
        }
    }
}
