package org.example.model;

import java.sql.Connection;

public class DatabaseConnectionResult {
    public final Connection connection;
    public final boolean isNewDatabase;

    public DatabaseConnectionResult(Connection connection, boolean isNewDatabase) {
        this.connection = connection;
        this.isNewDatabase = isNewDatabase;
    }
}
