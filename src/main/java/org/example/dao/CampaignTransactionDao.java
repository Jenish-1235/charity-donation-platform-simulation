package org.example.dao;

import org.example.model.CampaignTransaction;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CampaignTransactionDao {
    private final Connection conn;

    public CampaignTransactionDao(Connection conn) {
        this.conn = conn;
    }

    public boolean insertDonation(int donorId, int campaignId, double amount,
                                  String receiptStatus, String receiptUrl, String ackUrl) {
        String sql = "INSERT INTO campaign_transactions " +
                "(donor_id, campaign_id, amount, receipt_status, receipt_url, ack_url) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            stmt.setInt(2, campaignId);
            stmt.setDouble(3, amount);
            stmt.setString(4, receiptStatus);
            stmt.setString(5, receiptUrl);
            stmt.setString(6, ackUrl);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error inserting campaign donation: " + e.getMessage());
            return false;
        }
    }

    public List<CampaignTransaction> getDonationsByDonor(int donorId) {
        List<CampaignTransaction> list = new ArrayList<>();
        String sql = "SELECT * FROM campaign_transactions WHERE donor_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToTransaction(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving donations: " + e.getMessage());
        }
        return list;
    }

    private CampaignTransaction mapRowToTransaction(ResultSet rs) throws SQLException {
        return new CampaignTransaction(
                rs.getInt("transaction_id"),
                rs.getInt("donor_id"),
                rs.getInt("campaign_id"),
                rs.getDouble("amount"),
                rs.getString("receipt_status"),
                rs.getString("receipt_url"),
                rs.getString("ack_url"),
                rs.getTimestamp("timestamp").toLocalDateTime()
        );
    }
}
