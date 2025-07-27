package org.example.model;

import java.time.LocalDateTime;

public class FundraiserTransaction {
    private int id;
    private int donorId;
    private int fundraiserId;
    private double amount;
    private String receiptStatus;
    private String receiptUrl;
    private String ackUrl;
    private LocalDateTime timestamp;

    public FundraiserTransaction(int id, int donorId, int fundraiserId, double amount,
                                 String receiptStatus, String receiptUrl, String ackUrl,
                                 LocalDateTime timestamp) {
        this.id = id;
        this.donorId = donorId;
        this.fundraiserId = fundraiserId;
        this.amount = amount;
        this.receiptStatus = receiptStatus;
        this.receiptUrl = receiptUrl;
        this.ackUrl = ackUrl;
        this.timestamp = timestamp;
    }

    // Getters
    public int getId() { return id; }
    public int getDonorId() { return donorId; }
    public int getFundraiserId() { return fundraiserId; }
    public double getAmount() { return amount; }
    public String getReceiptStatus() { return receiptStatus; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
