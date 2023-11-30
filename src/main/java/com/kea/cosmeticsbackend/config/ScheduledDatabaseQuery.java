package com.kea.cosmeticsbackend.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class ScheduledDatabaseQuery {

    private final DataSource dataSource;

    public ScheduledDatabaseQuery(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Scheduled(fixedRate = 30000) // Run every 30 seconds
    public void executeQuery() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {

            while (resultSet.next()) {
                // Process each row of the result set as needed
            }

            // Print a message every time the query is executed
            System.out.println("MAINTAIN DB CONNECTION QUERY RUNNED");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}