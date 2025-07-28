package org.example.dao;

import org.example.model.DirectDonation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DirectDonationDao {
    private final Connection conn;

    public DirectDonationDao(Connection conn) {
        this.conn = conn;
    }

    public boolean insertDonation(int donorId, int charityId, double amount,
                                  String receiptStatus, String receiptUrl, String ackUrl) {
        String sql = "INSERT INTO direct_donations " +
                "(donor_id, charity_id, amount, receipt_status, receipt_url, ack_url) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            stmt.setInt(2, charityId);
            stmt.setDouble(3, amount);
            stmt.setString(4, receiptStatus);
            stmt.setString(5, receiptUrl);
            stmt.setString(6, ackUrl);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error inserting direct donation: " + e.getMessage());
            return false;
        }
    }

    public List<DirectDonation> findByDonorId(int donorId) {
        List<DirectDonation> list = new ArrayList<>();
        String sql = "SELECT * FROM direct_donations WHERE donor_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDonation(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving direct donations: " + e.getMessage());
        }
        return list;
    }

    public List<DirectDonation> findByCharityId(int charityId) {
        List<DirectDonation> list = new ArrayList<>();
        String sql = "SELECT * FROM direct_donations WHERE charity_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, charityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDonation(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving direct donations by charity: " + e.getMessage());
        }
        return list;
    }

    public List<DirectDonation> findRecentByCharityId(int charityId, int limit) {
        List<DirectDonation> list = new ArrayList<>();
        String sql = "SELECT * FROM direct_donations WHERE charity_id = ? ORDER BY timestamp DESC LIMIT ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, charityId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDonation(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving recent direct donations by charity: " + e.getMessage());
        }
        return list;
    }

    private DirectDonation mapRowToDonation(ResultSet rs) throws SQLException {
        return new DirectDonation(
                rs.getInt("transaction_id"),
                rs.getInt("donor_id"),
                rs.getInt("charity_id"),
                rs.getBigDecimal("amount"),
                rs.getString("receipt_status"),
                rs.getString("receipt_url"),
                rs.getString("ack_url"),
                rs.getTimestamp("timestamp")
        );
    }
}
