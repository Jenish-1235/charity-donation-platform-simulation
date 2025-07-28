package org.example.dao;

import org.example.model.Charity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CharityDao {
    private final Connection conn;

    public CharityDao(Connection conn) {
        this.conn = conn;
    }



    public Charity getCharityByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM charity WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToCharity(rs);
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error in getCharityByEmailAndPassword: " + e.getMessage());
        }
        return null;
    }

    public Charity getCharityById(int id) {
        String sql = "SELECT * FROM charity WHERE charity_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToCharity(rs);
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error in getCharityById: " + e.getMessage());
        }
        return null;
    }

    public List<Charity> getAllCharities() {
        List<Charity> list = new ArrayList<>();
        String sql = "SELECT * FROM charity";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToCharity(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching all charities: " + e.getMessage());
        }
        return list;
    }

    public List<Charity> getCharitiesByCategory(int categoryId) {
        List<Charity> list = new ArrayList<>();
        String sql = "SELECT * FROM charity WHERE category_id = ? AND is_active = TRUE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToCharity(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching charities by category: " + e.getMessage());
        }
        return list;
    }

    public boolean registerCharity(String name, String email, String password, String description,
                                   String websiteUrl, String ackUrl, String receiptUrl, int categoryId) {
        String sql = "INSERT INTO charity (name, email, password, description, website_url, ack_url, receipt_url, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, description);
            stmt.setString(5, websiteUrl);
            stmt.setString(6, ackUrl);
            stmt.setString(7, receiptUrl);
            stmt.setInt(8, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("⚠️ Charity registration failed: " + e.getMessage());
            return false;
        }
    }

    private Charity mapRowToCharity(ResultSet rs) throws SQLException {
        return new Charity(
                rs.getInt("charity_id"),
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("description"),
                rs.getString("website_url"),
                rs.getString("ack_url"),
                rs.getString("receipt_url"),
                rs.getBoolean("is_active"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
