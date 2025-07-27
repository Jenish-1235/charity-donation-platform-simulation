package org.example.dao;

import org.example.model.Donor;

import java.sql.*;
import java.time.LocalDateTime;

public class DonorDao {
    private final Connection conn;

    public DonorDao(Connection conn) {
        this.conn = conn;
    }

    public boolean registerDonor(String name, String email, String password, String city, String state,
                                 String country, int age, String gender, String incomeRange, String referrerSource) {
        String sql = "INSERT INTO donor (name, email, password, city, state, country, age, gender, income_range, referrer_source) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, city);
            stmt.setString(5, state);
            stmt.setString(6, country);
            stmt.setInt(7, age);
            stmt.setString(8, gender);
            stmt.setString(9, incomeRange);
            stmt.setString(10, referrerSource);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Donor registration failed: " + e.getMessage());
            return false;
        }
    }

    public Donor getDonorByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM donor WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToDonor(rs);
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving donor: " + e.getMessage());
        }
        return null;
    }

    public Donor getDonorById(int id) {
        String sql = "SELECT * FROM donor WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToDonor(rs);
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving donor by ID: " + e.getMessage());
        }
        return null;
    }

    private Donor mapRowToDonor(ResultSet rs) throws SQLException {
        return new Donor(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("country"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("income_range"),
                rs.getString("referrer_source"),
                rs.getTimestamp("last_donated_at") != null
                        ? rs.getTimestamp("last_donated_at").toLocalDateTime()
                        : null
        );
    }
}
