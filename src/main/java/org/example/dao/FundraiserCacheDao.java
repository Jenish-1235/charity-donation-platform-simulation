package org.example.dao;

import java.sql.*;

public class FundraiserCacheDao {
    private final Connection conn;

    public FundraiserCacheDao(Connection conn) {
        this.conn = conn;
    }

    public void incrementStats(int fundraiserId, double amount) {
        String sql = "INSERT INTO fundraiser_cache (fundraiser_id, total_amount, donor_count, last_updated_at) " +
                "VALUES (?, ?, 1, NOW()) " +
                "ON DUPLICATE KEY UPDATE total_amount = total_amount + VALUES(total_amount), " +
                "donor_count = donor_count + 1, last_updated_at = NOW()";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fundraiserId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating fundraiser cache: " + e.getMessage());
        }
    }

    public void updateFundraiserCache(int fundraiserId, double amount) {
        incrementStats(fundraiserId, amount);
    }
}
