package org.example.model;

import java.time.LocalDateTime;

public class Donor {
    private int id;
    private String name;
    private String email;
    private String city;
    private String state;
    private String country;
    private int age;
    private String gender;
    private String incomeRange;
    private String referrerSource;
    private LocalDateTime lastDonatedAt;

    public Donor(int id, String name, String email, String city, String state, String country,
                 int age, String gender, String incomeRange, String referrerSource,
                 LocalDateTime lastDonatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
        this.state = state;
        this.country = country;
        this.age = age;
        this.gender = gender;
        this.incomeRange = incomeRange;
        this.referrerSource = referrerSource;
        this.lastDonatedAt = lastDonatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIncomeRange() {
        return incomeRange;
    }

    public void setIncomeRange(String incomeRange) {
        this.incomeRange = incomeRange;
    }

    public String getReferrerSource() {
        return referrerSource;
    }

    public void setReferrerSource(String referrerSource) {
        this.referrerSource = referrerSource;
    }

    public LocalDateTime getLastDonatedAt() {
        return lastDonatedAt;
    }

    public void setLastDonatedAt(LocalDateTime lastDonatedAt) {
        this.lastDonatedAt = lastDonatedAt;
    }
}
