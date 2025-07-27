package org.example.dao;

import org.example.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private final Connection conn;

    public CategoryDao(Connection conn) {
        this.conn = conn;
    }

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching campaign/fundraiser categories: " + e.getMessage());
        }
        return list;
    }

    public java.util.List<org.example.model.Category> getAllCategories() {
        java.util.List<org.example.model.Category> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM category";
        try (java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new org.example.model.Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
                ));
            }
        } catch (java.sql.SQLException e) {
            System.err.println("⚠️ Error fetching all categories: " + e.getMessage());
        }
        return list;
    }
}
