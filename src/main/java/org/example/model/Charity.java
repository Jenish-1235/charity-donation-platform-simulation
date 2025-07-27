package org.example.model;

import java.time.LocalDateTime;

public class Charity {
    private int id;
    private int categoryId;
    private String name;
    private String email;
    private String password;
    private String description;
    private String websiteUrl;
    private String ackUrl;
    private String receiptUrl;
    private boolean isActive;
    private LocalDateTime createdAt;

    public Charity(int id, int categoryId, String name, String email, String password,
                   String description, String websiteUrl, String ackUrl, String receiptUrl,
                   boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.ackUrl = ackUrl;
        this.receiptUrl = receiptUrl;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getAckUrl() {
        return ackUrl;
    }

    public void setAckUrl(String ackUrl) {
        this.ackUrl = ackUrl;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
