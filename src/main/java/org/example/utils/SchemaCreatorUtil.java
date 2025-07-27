package org.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

public class SchemaCreatorUtil {

    public static void execute(Connection conn) {
        String[] files = {
                "sql/core/01_donor.sql",
                "sql/core/02_charity.sql",
                "sql/core/03_campaign.sql",
                "sql/core/04_fundraiser.sql",
                "sql/core/05_campaign_transactions.sql",
                "sql/core/06_fundraiser_transactions.sql",
                "sql/core/07_entity_type.sql",
                "sql/core/08_recurring_donation.sql",
                "sql/core/09_contact_method_type.sql",
                "sql/core/10_user_contact_preference.sql",
                "sql/core/11_payment_method_type.sql",
                "sql/core/12_user_payment_preference.sql",
                "sql/core/13_fundraiser_cache.sql",
                "sql/core/14_category.sql",
                "sql/core/15_charity_category.sql",
                "sql/core/16_campaign_cache.sql"
        };

        for (String file : files) {
            runSqlFromFile(file, conn);
        }

        System.out.println("Tables created.");
    }

    private static void runSqlFromFile(String filePath, Connection conn) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(
                                SchemaCreatorUtil.class.getClassLoader().getResourceAsStream(filePath))
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
            System.out.println("Failed to execute SQL file: " + filePath);
            e.printStackTrace();
        }
    }
}
