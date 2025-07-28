package org.example.dao;

import org.example.model.CharityCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharityCategoryDao {
    private final Connection conn;

    public CharityCategoryDao(Connection conn) {
        this.conn = conn;
    }

    public List<CharityCategory> getAll() {
        List<CharityCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM charity_category";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new CharityCategory(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching charity categories: " + e.getMessage());
        }
        return list;
    }

    public List<CharityCategory> getAllCharityCategories() {
        return getAll();
    }
}
