package org.example.dao;

import org.example.model.DirectDonation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DirectDonationDao {

    private final Connection conn;

    public DirectDonationDao(Connection conn) {
        this.conn = conn;
    }

    public void create(DirectDonation d) throws SQLException {
        String sql = "INSERT INTO direct_donations (donor_id, charity_id, amount, receipt_status, receipt_url, ack_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, d.getDonorId());
            stmt.setInt(2, d.getCharityId());
            stmt.setBigDecimal(3, d.getAmount());
            stmt.setString(4, d.getReceiptStatus());
            stmt.setString(5, d.getReceiptUrl());
            stmt.setString(6, d.getAckUrl());
            stmt.executeUpdate();
        }
    }

    public List<DirectDonation> findByDonorId(int donorId) throws SQLException {
        // Similar logic — SELECT * WHERE donor_id = ?
        return null;
    }

    public List<DirectDonation> findByCharityId(int charityId) throws SQLException {
        // Similar logic — SELECT * WHERE charity_id = ?
        return null;
    }
}
