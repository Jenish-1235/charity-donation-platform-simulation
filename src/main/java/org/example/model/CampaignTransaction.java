package org.example.model;

import java.time.LocalDateTime;

public class CampaignTransaction {
    private int id;
    private int donorId;
    private int campaignId;
    private double amount;
    private String receiptStatus;
    private String receiptUrl;
    private String ackUrl;
    private LocalDateTime timestamp;

    public CampaignTransaction(int id, int donorId, int campaignId, double amount,
                               String receiptStatus, String receiptUrl, String ackUrl,
                               LocalDateTime timestamp) {
        this.id = id;
        this.donorId = donorId;
        this.campaignId = campaignId;
        this.amount = amount;
        this.receiptStatus = receiptStatus;
        this.receiptUrl = receiptUrl;
        this.ackUrl = ackUrl;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(String receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getAckUrl() {
        return ackUrl;
    }

    public void setAckUrl(String ackUrl) {
        this.ackUrl = ackUrl;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
