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

    public List<PaymentMethodType> getAll() {
        List<PaymentMethodType> list = new ArrayList<>();
        String sql = "SELECT * FROM payment_method_type";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new PaymentMethodType(
                        rs.getInt("id"),
                        rs.getString("method"),
                        rs.getString("provider"),
                        rs.getBoolean("requires_token"),
                        rs.getString("callback_url")
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error loading payment methods: " + e.getMessage());
        }
        return list;
    }
}
