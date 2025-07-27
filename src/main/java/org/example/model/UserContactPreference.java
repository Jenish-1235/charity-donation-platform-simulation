package org.example.model;

public class UserContactPreference {
    private int preferenceId;
    private int donorId;
    private int methodId;
    private boolean isEnabled;
    private String preferredLanguage;

    public UserContactPreference(int preferenceId, int donorId, int methodId,
                                 boolean isEnabled, String preferredLanguage) {
        this.preferenceId = preferenceId;
        this.donorId = donorId;
        this.methodId = methodId;
        this.isEnabled = isEnabled;
        this.preferredLanguage = preferredLanguage;
    }

    public int getMethodId() { return methodId; }
    public boolean isEnabled() { return isEnabled; }
    public String getPreferredLanguage() { return preferredLanguage; }
}
