package org.example.dao;

import org.example.model.UserPaymentPreference;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserPaymentPreferenceDao {
    private final Connection conn;

    public UserPaymentPreferenceDao(Connection conn) {
        this.conn = conn;
    }

    public boolean addPaymentMethod(int donorId, int methodId, String accountLast4,
                                    String providerToken, boolean isPrimary) {
        String sql = "INSERT INTO user_payment_preference " +
                "(donor_id, payment_method_id, account_last4, provider_token, is_primary) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            stmt.setInt(2, methodId);
            stmt.setString(3, accountLast4);
            stmt.setString(4, providerToken);
            stmt.setBoolean(5, isPrimary);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error adding payment method: " + e.getMessage());
            return false;
        }
    }

    public List<UserPaymentPreference> getMethodsByDonor(int donorId) {
        List<UserPaymentPreference> list = new ArrayList<>();
        String sql = "SELECT * FROM user_payment_preference WHERE donor_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new UserPaymentPreference(
                        rs.getInt("payment_id"),
                        donorId,
                        rs.getInt("payment_method_id"),
                        rs.getString("account_last4"),
                        rs.getString("provider_token"),
                        rs.getBoolean("is_primary"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching user payment methods: " + e.getMessage());
        }
        return list;
    }

    public UserPaymentPreference getPrimaryMethod(int donorId) {
        String sql = "SELECT * FROM user_payment_preference WHERE donor_id = ? AND is_primary = TRUE LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserPaymentPreference(
                        rs.getInt("payment_id"),
                        donorId,
                        rs.getInt("payment_method_id"),
                        rs.getString("account_last4"),
                        rs.getString("provider_token"),
                        rs.getBoolean("is_primary"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching primary method: " + e.getMessage());
        }
        return null;
    }
}
