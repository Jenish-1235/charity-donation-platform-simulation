package org.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<org.example.model.Fundraiser> getFundraisersByCharity(int charityId) {
        List<org.example.model.Fundraiser> list = new ArrayList<>();
        String sql = "SELECT * FROM fundraiser WHERE charity_id = ? ORDER BY start_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, charityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToFundraiser(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching fundraisers for charity: " + e.getMessage());
        }
        return list;
    }

    public org.example.model.Fundraiser getFundraiserById(int id) {
        String sql = "SELECT * FROM fundraiser WHERE fundraiser_id = ?";
        try (java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToFundraiser(rs);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("⚠️ Error fetching fundraiser by ID: " + e.getMessage());
        }
        return null;
    }

    public boolean createFundraiser(int charityId, int categoryId, String title, String description,
                                   double goalAmount, double currentAmount, String recUrl, String ackUrl,
                                   LocalDate startDate, LocalDate endDate) {
        String sql = "INSERT INTO fundraiser (charity_id, category_id, title, description, goal_amount, " +
                "current_amount, rec_url, ack_url, is_active, start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, charityId);
            stmt.setInt(2, categoryId);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setDouble(5, goalAmount);
            stmt.setDouble(6, currentAmount);
            stmt.setString(7, recUrl);
            stmt.setString(8, ackUrl);
            stmt.setBoolean(9, true); // default active
            stmt.setDate(10, Date.valueOf(startDate));
            stmt.setDate(11, Date.valueOf(endDate));
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error creating fundraiser: " + e.getMessage());
            return false;
        }
    }

    public boolean updateFundraiser(org.example.model.Fundraiser fundraiser) {
        String sql = "UPDATE fundraiser SET title = ?, description = ?, goal_amount = ?, " +
                "rec_url = ?, ack_url = ? WHERE fundraiser_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fundraiser.getTitle());
            stmt.setString(2, fundraiser.getDescription());
            stmt.setDouble(3, fundraiser.getGoalAmount());
            stmt.setString(4, fundraiser.getRecUrl());
            stmt.setString(5, fundraiser.getAckUrl());
            stmt.setInt(6, fundraiser.getId());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating fundraiser: " + e.getMessage());
            return false;
        }
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
