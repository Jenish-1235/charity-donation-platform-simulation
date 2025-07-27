package org.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

public class RelationshipEnforcerUtil {

    public static void execute(Connection conn) {
        runSqlFromFile("sql/core/relationships.sql", conn);
        System.out.println("FK and Indexes enforced.");
    }

    private static void runSqlFromFile(String filePath, Connection conn) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(
                                RelationshipEnforcerUtil.class.getClassLoader().getResourceAsStream(filePath))
                ));
                Statement stmt = conn.createStatement()
        ) {
            StringBuilder query = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                query.append(line);
                if (line.trim().endsWith(";")) {
                    stmt.execute(query.toString());
                    query.setLength(0);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to execute constraints SQL.");
            e.printStackTrace();
        }
    }
}
