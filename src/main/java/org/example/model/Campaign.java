package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Campaign {
    private int id;
    private int charityId;
    private int categoryId;
    private String title;
    private String description;
    private String recUrl;
    private String ackUrl;
    private boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;

    public Campaign(int id, int charityId, int categoryId, String title, String description,
                    String recUrl, String ackUrl, boolean isActive, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.charityId = charityId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.recUrl = recUrl;
        this.ackUrl = ackUrl;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecUrl() {
        return recUrl;
    }

    public void setRecUrl(String recUrl) {
        this.recUrl = recUrl;
    }

    public String getAckUrl() {
        return ackUrl;
    }

    public void setAckUrl(String ackUrl) {
        this.ackUrl = ackUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
