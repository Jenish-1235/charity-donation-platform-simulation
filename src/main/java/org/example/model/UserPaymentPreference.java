package org.example.model;

import java.time.LocalDateTime;

public class UserPaymentPreference {
    private int id;
    private int donorId;
    private int methodId;
    private String accountLast4;
    private String providerToken;
    private boolean isPrimary;
    private LocalDateTime createdAt;

    public UserPaymentPreference(int id, int donorId, int methodId, String accountLast4,
                                 String providerToken, boolean isPrimary, LocalDateTime createdAt) {
        this.id = id;
        this.donorId = donorId;
        this.methodId = methodId;
        this.accountLast4 = accountLast4;
        this.providerToken = providerToken;
        this.isPrimary = isPrimary;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public boolean isPrimary() { return isPrimary; }
    public String getAccountLast4() { return accountLast4; }
}
