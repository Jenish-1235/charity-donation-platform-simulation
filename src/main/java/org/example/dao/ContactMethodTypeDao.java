package org.example.dao;

import org.example.model.ContactMethodType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactMethodTypeDao {
    private final Connection conn;

    public ContactMethodTypeDao(Connection conn) {
        this.conn = conn;
    }

    public List<ContactMethodType> getAllMethods() {
        List<ContactMethodType> methods = new ArrayList<>();
        String sql = "SELECT * FROM contact_method_type";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                methods.add(new ContactMethodType(
                        rs.getInt("id"),
                        rs.getString("method"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching contact methods: " + e.getMessage());
        }
        return methods;
    }

    public List<ContactMethodType> getAllContactMethods() {
        return getAllMethods();
    }

    public ContactMethodType getContactMethodById(int id) {
        String sql = "SELECT * FROM contact_method_type WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ContactMethodType(
                        rs.getInt("id"),
                        rs.getString("method"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching contact method by ID: " + e.getMessage());
        }
        return null;
    }
}
