package org.example.dao;

import org.example.model.Campaign;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CampaignDao {
    private final Connection conn;

    public CampaignDao(Connection conn) {
        this.conn = conn;
    }

    public boolean createCampaign(int charityId, int categoryId, String title, String description,
                                  String recUrl, String ackUrl, LocalDate startDate, LocalDate endDate) {
        String sql = "INSERT INTO campaign (charity_id, category_id, title, description, rec_url, ack_url, " +
                "is_active, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, charityId);
            stmt.setInt(2, categoryId);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setString(5, recUrl);
            stmt.setString(6, ackUrl);
            stmt.setBoolean(7, true); // default active
            stmt.setDate(8, Date.valueOf(startDate));
            stmt.setDate(9, Date.valueOf(endDate));
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error in createCampaign: " + e.getMessage());
            return false;
        }
    }

    public List<Campaign> getActiveCampaigns() {
        List<Campaign> list = new ArrayList<>();
        String sql = "SELECT * FROM campaign WHERE is_active = TRUE ORDER BY start_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToCampaign(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching active campaigns: " + e.getMessage());
        }
        return list;
    }

    public List<Campaign> getCampaignsByCharity(int charityId) {
        List<Campaign> list = new ArrayList<>();
        String sql = "SELECT * FROM campaign WHERE charity_id = ? ORDER BY start_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, charityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToCampaign(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching campaigns for charity: " + e.getMessage());
        }
        return list;
    }

    public List<Campaign> getCampaignsByCategory(int categoryId) {
        List<Campaign> list = new ArrayList<>();
        String sql = "SELECT * FROM campaign WHERE category_id = ? AND is_active = TRUE ORDER BY start_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToCampaign(rs));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching campaigns by category: " + e.getMessage());
        }
        return list;
    }

    public Campaign getCampaignById(int id) {
        String sql = "SELECT * FROM campaign WHERE campaign_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToCampaign(rs);
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching campaign by ID: " + e.getMessage());
        }
        return null;
    }

    public boolean updateCampaign(Campaign campaign) {
        String sql = "UPDATE campaign SET title = ?, description = ?, rec_url = ?, ack_url = ? WHERE campaign_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, campaign.getTitle());
            stmt.setString(2, campaign.getDescription());
            stmt.setString(3, campaign.getRecUrl());
            stmt.setString(4, campaign.getAckUrl());
            stmt.setInt(5, campaign.getId());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating campaign: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCampaignStatus(int campaignId, boolean isActive) {
        String sql = "UPDATE campaign SET is_active = ? WHERE campaign_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, isActive);
            stmt.setInt(2, campaignId);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating campaign status: " + e.getMessage());
            return false;
        }
    }

    private Campaign mapRowToCampaign(ResultSet rs) throws SQLException {
        return new Campaign(
                rs.getInt("campaign_id"),
                rs.getInt("charity_id"),
                rs.getInt("category_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("rec_url"),
                rs.getString("ack_url"),
                rs.getBoolean("is_active"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate()
        );
    }
}
