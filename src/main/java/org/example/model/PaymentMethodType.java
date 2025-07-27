package org.example.model;

public class PaymentMethodType {
    private int id;
    private String method;
    private String provider;
    private boolean requiresToken;
    private String callbackUrl;

    public PaymentMethodType(int id, String method, String provider, boolean requiresToken, String callbackUrl) {
        this.id = id;
        this.method = method;
        this.provider = provider;
        this.requiresToken = requiresToken;
        this.callbackUrl = callbackUrl;
    }

    public int getId() { return id; }
    public String getMethod() { return method; }
    public boolean isRequiresToken() { return requiresToken; }
}
