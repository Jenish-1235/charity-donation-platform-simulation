package org.example.model;

import java.time.LocalDateTime;

public class CampaignCache {
    private int campaignId;
    private double totalDonations;
    private int donorCount;
    private LocalDateTime lastDonationAt;

    public CampaignCache(int campaignId, double totalDonations, int donorCount, LocalDateTime lastDonationAt) {
        this.campaignId = campaignId;
        this.totalDonations = totalDonations;
        this.donorCount = donorCount;
        this.lastDonationAt = lastDonationAt;
    }

    public int getCampaignId() { return campaignId; }
    public double getTotalDonations() { return totalDonations; }
    public int getDonorCount() { return donorCount; }
}
