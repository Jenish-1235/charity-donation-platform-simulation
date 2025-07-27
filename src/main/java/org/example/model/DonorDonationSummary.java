package org.example.model;

import java.time.LocalDateTime;

public class DonorDonationSummary {
    private int donorId;
    private String donationType;
    private double totalAmount;
    private int donationCount;
    private LocalDateTime lastUpdated;

    public DonorDonationSummary(int donorId, String donationType, double totalAmount,
                                int donationCount, LocalDateTime lastUpdated) {
        this.donorId = donorId;
        this.donationType = donationType;
        this.totalAmount = totalAmount;
        this.donationCount = donationCount;
        this.lastUpdated = lastUpdated;
    }

    public int getDonorId() { return donorId; }
    public String getDonationType() { return donationType; }
    public double getTotalAmount() { return totalAmount; }
    public int getDonationCount() { return donationCount; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
}
