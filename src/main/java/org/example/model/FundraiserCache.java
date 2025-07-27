package org.example.model;

import java.time.LocalDateTime;

public class FundraiserCache {
    private int fundraiserId;
    private double totalAmount;
    private int donorCount;
    private LocalDateTime lastUpdatedAt;

    public FundraiserCache(int fundraiserId, double totalAmount, int donorCount, LocalDateTime lastUpdatedAt) {
        this.fundraiserId = fundraiserId;
        this.totalAmount = totalAmount;
        this.donorCount = donorCount;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public int getFundraiserId() { return fundraiserId; }
    public double getTotalAmount() { return totalAmount; }
    public int getDonorCount() { return donorCount; }
}
