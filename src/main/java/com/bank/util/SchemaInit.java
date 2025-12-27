package com.bank.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@WebListener
public class SchemaInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing Database Schema...");
        
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             Statement stmt = conn.createStatement()) {

            // Create Users Table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password_hash VARCHAR(255) NOT NULL, " +
                    "full_name VARCHAR(100) NOT NULL, " +
                    "balance DECIMAL(15, 2) DEFAULT 0.00" +
                    ")");

            // Create Transactions Table
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "sender_id INT, " +
                    "receiver_id INT, " +
                    "amount DECIMAL(15, 2) NOT NULL, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (sender_id) REFERENCES users(id), " +
                    "FOREIGN KEY (receiver_id) REFERENCES users(id)" +
                    ")");

            // Seed Initial Data if empty
            // Check if users exist
            boolean hasUsers = stmt.executeQuery("SELECT COUNT(*) FROM users").next() 
                    && stmt.getResultSet().getInt(1) > 0;
            
            if (!hasUsers) {
                System.out.println("Seeding initial users...");
                String pwhash = BCrypt.hashpw("1234", BCrypt.gensalt());
                
                String sql = "INSERT INTO users (username, password_hash, full_name, balance) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    // Ali
                    ps.setString(1, "ali");
                    ps.setString(2, pwhash);
                    ps.setString(3, "Ali Khan");
                    ps.setBigDecimal(4, new java.math.BigDecimal("50000.00"));
                    ps.addBatch();
                    
                    // Sara
                    ps.setString(1, "sara");
                    ps.setString(2, pwhash);
                    ps.setString(3, "Sara Ahmed");
                    ps.setBigDecimal(4, new java.math.BigDecimal("30000.00"));
                    ps.addBatch();
                    
                    ps.executeBatch();
                }
            }

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Database initialization failed.");
            e.printStackTrace();
            // Do NOT throw RuntimeException here. Let the app start so we can see the logs/error page.
            // In a real prod app, you might want to stop, but for debugging deployment, this is better.
        }
    }
}
