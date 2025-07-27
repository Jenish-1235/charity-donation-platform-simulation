package org.example.model;

import java.time.LocalDate;

public class RecurringDonation {
    private int id;
    private int donorId;
    private int entityTypeId;
    private int entityId;
    private int charityId;
    private String recurringRate;
    private double amount;
    private LocalDate lastInstallment;
    private LocalDate nextInstallment;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
    private int primaryPaymentMethodId;

    public RecurringDonation(int id, int donorId, int entityTypeId, int entityId, int charityId,
                             String recurringRate, double amount, LocalDate lastInstallment,
                             LocalDate nextInstallment, LocalDate startDate, LocalDate endDate,
                             boolean isActive, int primaryPaymentMethodId) {
        this.id = id;
        this.donorId = donorId;
        this.entityTypeId = entityTypeId;
        this.entityId = entityId;
        this.charityId = charityId;
        this.recurringRate = recurringRate;
        this.amount = amount;
        this.lastInstallment = lastInstallment;
        this.nextInstallment = nextInstallment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.primaryPaymentMethodId = primaryPaymentMethodId;
    }

    // Getters (add setters if needed)
    public int getId() { return id; }
    public int getDonorId() { return donorId; }
    public double getAmount() { return amount; }
    public String getRecurringRate() { return recurringRate; }
    public boolean isActive() { return isActive; }
}
