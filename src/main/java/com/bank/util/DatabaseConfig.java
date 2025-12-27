package com.bank.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    private static final DataSource dataSource;

    static {
        String env = System.getProperty("app.env", "prod");

        if ("test".equalsIgnoreCase(env)) {
            // No DB in tests
            dataSource = null;
        } else {
            try {
                HikariConfig config = new HikariConfig();
                // Restore robust URL with auto-creation
                config.setJdbcUrl("jdbc:mysql://localhost:3306/bankdb?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false");
                config.setUsername("root");
                config.setPassword("root");
                config.setDriverClassName("com.mysql.cj.jdbc.Driver");

                config.setMaximumPoolSize(10);
                config.setMinimumIdle(2);

                System.out.println("Initializing HikariCP with MySQL URL: " + config.getJdbcUrl());
                
                dataSource = new HikariDataSource(config);
                System.out.println("HikariCP initialized successfully.");
            } catch (Exception e) {
                // Log to error stream so it appears in server logs
                System.err.println("CRITICAL: Failed to initialize database pool. Check MySQL is running and credentials are correct.");
                e.printStackTrace();
                // If we throw here, class loading fails. Let's throw, but we rely on SchemaInit to handle the subsequent failure if possible?
                // Actually, if static block fails, the class is unusable. We must throw or leave dataSource null.
                // Leaving it null and throwing in getter is safer for "ignoring" this class if unused, but we use it.
                // We'll keep the throw but rely on the printed logs above to debug.
                throw new RuntimeException("Failed to initialize database pool: " + e.getMessage(), e);
            }
        }
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("Database disabled in test environment");
        }
        return dataSource;
    }

    private DatabaseConfig() {}
}
