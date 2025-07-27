package org.example.dao;

import org.example.model.RecurringDonation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecurringDonationDao {
    private final Connection conn;

    public RecurringDonationDao(Connection conn) {
        this.conn = conn;
    }

    public boolean createRecurringDonation(int donorId, int entityTypeId, int entityId, int charityId,
                                           String recurringRate, double amount,
                                           LocalDate startDate, LocalDate endDate,
                                           int paymentMethodId) {
        String sql = "INSERT INTO recurring_donation (donor_id, entity_type_id, entity_id, charity_id, " +
                "recurring_rate, amount, start_date, end_date, is_active, primary_payment_method_id, " +
                "last_installment, next_installment) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE, ?, NULL, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            stmt.setInt(2, entityTypeId);
            stmt.setInt(3, entityId);
            stmt.setInt(4, charityId);
            stmt.setString(5, recurringRate);
            stmt.setDouble(6, amount);
            stmt.setDate(7, Date.valueOf(startDate));
            stmt.setDate(8, Date.valueOf(endDate));
            stmt.setInt(9, paymentMethodId);
            stmt.setDate(10, Date.valueOf(startDate)); // first installment = start

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error inserting recurring donation: " + e.getMessage());
            return false;
        }
    }

    public List<RecurringDonation> getActiveRecurringByDonor(int donorId) {
        List<RecurringDonation> list = new ArrayList<>();
        String sql = "SELECT * FROM recurring_donation WHERE donor_id = ? AND is_active = TRUE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToRecurring(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error retrieving recurring donations: " + e.getMessage());
        }
        return list;
    }

    private RecurringDonation mapRowToRecurring(ResultSet rs) throws SQLException {
        return new RecurringDonation(
                rs.getInt("recurring_donation_id"),
                rs.getInt("donor_id"),
                rs.getInt("entity_type_id"),
                rs.getInt("entity_id"),
                rs.getInt("charity_id"),
                rs.getString("recurring_rate"),
                rs.getDouble("amount"),
                rs.getDate("last_installment") != null ? rs.getDate("last_installment").toLocalDate() : null,
                rs.getDate("next_installment") != null ? rs.getDate("next_installment").toLocalDate() : null,
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getBoolean("is_active"),
                rs.getInt("primary_payment_method_id")
        );
    }
}
