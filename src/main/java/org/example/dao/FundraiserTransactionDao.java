package org.example.dao;

import org.example.model.FundraiserTransaction;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FundraiserTransactionDao {
    private final Connection conn;

    public FundraiserTransactionDao(Connection conn) {
        this.conn = conn;
    }

    public boolean insertDonation(int donorId, int fundraiserId, double amount,
                                  String receiptStatus, String receiptUrl, String ackUrl) {
        String sql = "INSERT INTO fundraiser_transactions " +
                "(donor_id, fundraiser_id, amount, receipt_status, receipt_url, ack_url) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            stmt.setInt(2, fundraiserId);
            stmt.setDouble(3, amount);
            stmt.setString(4, receiptStatus);
            stmt.setString(5, receiptUrl);
            stmt.setString(6, ackUrl);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error inserting fundraiser donation: " + e.getMessage());
            return false;
        }
    }

    public List<FundraiserTransaction> getDonationsByDonor(int donorId) {
        List<FundraiserTransaction> list = new ArrayList<>();
        String sql = "SELECT * FROM fundraiser_transactions WHERE donor_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToTransaction(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving fundraiser donations: " + e.getMessage());
        }
        return list;
    }

    public List<FundraiserTransaction> getDonationsByFundraiser(int fundraiserId) {
        List<FundraiserTransaction> list = new ArrayList<>();
        String sql = "SELECT * FROM fundraiser_transactions WHERE fundraiser_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fundraiserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToTransaction(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving donations by fundraiser: " + e.getMessage());
        }
        return list;
    }

    private FundraiserTransaction mapRowToTransaction(ResultSet rs) throws SQLException {
        return new FundraiserTransaction(
                rs.getInt("transaction_id"),
                rs.getInt("donor_id"),
                rs.getInt("fundraiser_id"),
                rs.getDouble("amount"),
                rs.getString("receipt_status"),
                rs.getString("receipt_url"),
                rs.getString("ack_url"),
                rs.getTimestamp("timestamp").toLocalDateTime()
        );
    }
}
