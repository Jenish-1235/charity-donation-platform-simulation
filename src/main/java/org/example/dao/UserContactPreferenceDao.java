package org.example.dao;

import org.example.model.UserContactPreference;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserContactPreferenceDao {
    private final Connection conn;

    public UserContactPreferenceDao(Connection conn) {
        this.conn = conn;
    }

    public boolean setPreference(int donorId, int methodId, boolean isEnabled, String language) {
        String sql = "INSERT INTO user_contact_preference (donor_id, method_id, is_enabled, preferred_language) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE is_enabled = VALUES(is_enabled), preferred_language = VALUES(preferred_language)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            stmt.setInt(2, methodId);
            stmt.setBoolean(3, isEnabled);
            stmt.setString(4, language);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("⚠️ Error saving contact preference: " + e.getMessage());
            return false;
        }
    }

    public boolean createContactPreference(int donorId, int methodId, boolean isEnabled, String language) {
        return setPreference(donorId, methodId, isEnabled, language);
    }

    public List<UserContactPreference> getPreferencesByDonor(int donorId) {
        List<UserContactPreference> list = new ArrayList<>();
        String sql = "SELECT * FROM user_contact_preference WHERE donor_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new UserContactPreference(
                        rs.getInt("preference_id"),
                        donorId,
                        rs.getInt("method_id"),
                        rs.getBoolean("is_enabled"),
                        rs.getString("preferred_language")
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching contact preferences: " + e.getMessage());
        }
        return list;
    }

    public List<UserContactPreference> getContactMethodsByDonor(int donorId) {
        return getPreferencesByDonor(donorId);
    }

    public UserContactPreference getPrimaryContactMethod(int donorId) {
        String sql = "SELECT * FROM user_contact_preference WHERE donor_id = ? ORDER BY preference_id ASC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserContactPreference(
                        rs.getInt("preference_id"),
                        donorId,
                        rs.getInt("method_id"),
                        rs.getBoolean("is_enabled"),
                        rs.getString("preferred_language")
                );
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching primary contact method: " + e.getMessage());
        }
        return null;
    }
}
