package org.example.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DirectDonation {
    private int transactionId;
    private int donorId;
    private int charityId;
    private BigDecimal amount;
    private String receiptStatus;
    private String receiptUrl;
    private String ackUrl;
    private Timestamp timestamp;

    // Constructors, getters, setters...


    public DirectDonation(int transactionId, int donorId, int charityId, BigDecimal amount, String receiptStatus, String receiptUrl, String ackUrl, Timestamp timestamp) {
        this.transactionId = transactionId;
        this.donorId = donorId;
        this.charityId = charityId;
        this.amount = amount;
        this.receiptStatus = receiptStatus;
        this.receiptUrl = receiptUrl;
        this.ackUrl = ackUrl;
        this.timestamp = timestamp;
    }

    public DirectDonation(int donorId, int charityId, BigDecimal amount, String receiptStatus, String receiptUrl, String ackUrl) {
        this.donorId = donorId;
        this.charityId = charityId;
        this.amount = amount;
        this.receiptStatus = receiptStatus;
        this.receiptUrl = receiptUrl;
        this.ackUrl = ackUrl;
    }

    public DirectDonation() {

    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
