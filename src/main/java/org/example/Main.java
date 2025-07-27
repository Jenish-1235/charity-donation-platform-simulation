        package org.example;

        import org.example.model.DatabaseConnectionResult;
        import org.example.utils.ConnectionUtil;
        import org.example.utils.DummyDataInserterUtil;
        import org.example.utils.RelationshipEnforcerUtil;
        import org.example.utils.SchemaCreatorUtil;

        import java.sql.Connection;
        import java.sql.SQLException;
        import java.util.Scanner;

        //TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
        // click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
        public class Main {
            public static void main(String[] args) {

                System.out.println("Simulating Charity Donation Platform");

                Scanner sc = new Scanner(System.in);
                Connection conn = null;

                boolean isNewDatabase = false;

                try {
                    System.out.println("Choose : \n" +
                            "1. Use local Database. (Will create schema and add dummy data locally.) \n" +
                            "2. Use hostede Database. (Schema and dummy data already setup in cloud.) \n");
                    System.out.println("Enter your choice (1 or 2) : ");
                    int choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Enter Database url: ");
                            String url = sc.next();
                            System.out.println("Enter Database username: ");
                            String username = sc.next();
                            System.out.println("Enter Database password: ");
                            String password = sc.next();

                            DatabaseConnectionResult result = ConnectionUtil.getLocalDbConnection(url, username, password);
                            conn = result.connection;

                            if (result.isNewDatabase) {
                                SchemaCreatorUtil.execute(conn);
                                RelationshipEnforcerUtil.execute(conn);
                                DummyDataInserterUtil.execute(conn);
                            }
                            break;
                        case 2:
                            System.out.println("Connecting to existing database... \n" +
                                    "info : This database already consists of schema created and dummy data inserted.");
                            conn = ConnectionUtil.getHostedDbConnection();
                            break;
                        default:
                            System.out.println("Wrong choice. Exiting.");
                            return;

                    }
                    Simulator.execute(sc, conn);
                }
                catch (SQLException e){
                    System.out.println("Database connection error. Please try again.");
                    e.printStackTrace();
                }

            }
        }