package org.example.model;

public class ContactMethodType {
    private int id;
    private String method;
    private String description;

    public ContactMethodType(int id, String method, String description) {
        this.id = id;
        this.method = method;
        this.description = description;
    }

    public int getId() { return id; }
    public String getMethod() { return method; }
    public String getDescription() { return description; }
}
