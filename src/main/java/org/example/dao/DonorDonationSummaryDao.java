package org.example.dao;

import org.example.model.DonorDonationSummary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDonationSummaryDao {
    private final Connection conn;

    public DonorDonationSummaryDao(Connection conn) {
        this.conn = conn;
    }

    public boolean upsertSummary(int donorId, String donationType, double amount) {
        String sql = "INSERT INTO donor_donation_summary (donor_id, donation_type, total_amount, donation_count) " +
                "VALUES (?, ?, ?, 1) " +
                "ON DUPLICATE KEY UPDATE " +
                "total_amount = total_amount + VALUES(total_amount), " +
                "donation_count = donation_count + 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            stmt.setString(2, donationType);
            stmt.setDouble(3, amount);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating donor donation summary: " + e.getMessage());
            return false;
        }
    }

    public List<DonorDonationSummary> getByType(String donationType) {
        List<DonorDonationSummary> list = new ArrayList<>();
        String sql = "SELECT * FROM donor_donation_summary WHERE donation_type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, donationType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new DonorDonationSummary(
                        rs.getInt("donor_id"),
                        rs.getString("donation_type"),
                        rs.getDouble("total_amount"),
                        rs.getInt("donation_count"),
                        rs.getTimestamp("last_updated").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching summaries: " + e.getMessage());
        }
        return list;
    }

    public List<DonorDonationSummary> getAll() {
        List<DonorDonationSummary> list = new ArrayList<>();
        String sql = "SELECT * FROM donor_donation_summary";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new DonorDonationSummary(
                        rs.getInt("donor_id"),
                        rs.getString("donation_type"),
                        rs.getDouble("total_amount"),
                        rs.getInt("donation_count"),
                        rs.getTimestamp("last_updated").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching all summaries: " + e.getMessage());
        }
        return list;
    }
}
