package org.example.dao;

import java.sql.Connection;
import java.util.List;

public class FundraiserDao {
    private final Connection conn;

    public FundraiserDao(Connection conn) {
        this.conn = conn;
    }

    public List<org.example.model.Fundraiser> getAllFundraisers() {
        List<org.example.model.Fundraiser> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM fundraiser";
        try (java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToFundraiser(rs));
            }
        } catch (java.sql.SQLException e) {
            System.err.println("⚠️ Error fetching all fundraisers: " + e.getMessage());
        }
        return list;
    }

    public java.util.List<org.example.model.Fundraiser> getFundraisersByCategory(int categoryId) {
        java.util.List<org.example.model.Fundraiser> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM fundraiser WHERE category_id = ? AND is_active = TRUE ORDER BY start_date DESC";
        try (java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToFundraiser(rs));
            }
        } catch (java.sql.SQLException e) {
            System.err.println("⚠️ Error fetching fundraisers by category: " + e.getMessage());
        }
        return list;
    }

    private org.example.model.Fundraiser mapRowToFundraiser(java.sql.ResultSet rs) throws java.sql.SQLException {
        return new org.example.model.Fundraiser(
            rs.getInt("fundraiser_id"),
            rs.getInt("charity_id"),
            rs.getInt("category_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getDouble("goal_amount"),
            rs.getDouble("current_amount"),
            rs.getString("rec_url"),
            rs.getString("ack_url"),
            rs.getBoolean("is_active"),
            rs.getDate("start_date").toLocalDate(),
            rs.getDate("end_date").toLocalDate(),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
