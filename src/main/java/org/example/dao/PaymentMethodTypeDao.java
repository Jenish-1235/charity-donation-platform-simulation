package org.example.dao;

import org.example.model.PaymentMethodType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodTypeDao {
    private final Connection conn;

    public PaymentMethodTypeDao(Connection conn) {
        this.conn = conn;
    }

    public List<PaymentMethodType> getAllMethods() {
        List<PaymentMethodType> methods = new ArrayList<>();
        String sql = "SELECT * FROM payment_method_type";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                methods.add(new PaymentMethodType(
                        rs.getInt("id"),
                        rs.getString("method"),
                        rs.getString("provider"),
                        rs.getBoolean("requires_token"),
                        rs.getString("callback_url")
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching payment methods: " + e.getMessage());
        }
        return methods;
    }

    public List<PaymentMethodType> getAllPaymentMethods() {
        return getAllMethods();
    }

    public PaymentMethodType getPaymentMethodById(int id) {
        String sql = "SELECT * FROM payment_method_type WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PaymentMethodType(
                        rs.getInt("id"),
                        rs.getString("method"),
                        rs.getString("provider"),
                        rs.getBoolean("requires_token"),
                        rs.getString("callback_url")
                );
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching payment method by ID: " + e.getMessage());
        }
        return null;
    }
}
