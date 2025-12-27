package com.bank.dao;

import com.bank.util.DatabaseConfig;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDao implements IAccountDao {

    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM users WHERE id = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public boolean userExists(int userId) {
        String sql = "SELECT 1 FROM users WHERE id = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deposit(int userId, BigDecimal amount) throws SQLException {
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, amount);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("Error depositing funds", e);
        }
    }

    // Atomic Transfer using JDBC Transaction
    public void performTransfer(int fromId, int toId, BigDecimal amount) throws SQLException {
        Connection conn = null;
        PreparedStatement updateSender = null;
        PreparedStatement updateReceiver = null;
        PreparedStatement recordTx = null;

        String sqlDebit = "UPDATE users SET balance = balance - ? WHERE id = ?";
        String sqlCredit = "UPDATE users SET balance = balance + ? WHERE id = ?";
        String sqlRecord = "INSERT INTO transactions (sender_id, receiver_id, amount) VALUES (?, ?, ?)";

        try {
            conn = DatabaseConfig.getDataSource().getConnection();
            conn.setAutoCommit(false); // Start Transaction

            // Debit Sender
            updateSender = conn.prepareStatement(sqlDebit);
            updateSender.setBigDecimal(1, amount);
            updateSender.setInt(2, fromId);
            updateSender.executeUpdate();

            // Credit Receiver
            updateReceiver = conn.prepareStatement(sqlCredit);
            updateReceiver.setBigDecimal(1, amount);
            updateReceiver.setInt(2, toId);
            updateReceiver.executeUpdate();
            
            // Record Transaction
            recordTx = conn.prepareStatement(sqlRecord);
            recordTx.setInt(1, fromId);
            recordTx.setInt(2, toId);
            recordTx.setBigDecimal(3, amount);
            recordTx.executeUpdate();

            conn.commit(); // Commit

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw new SQLException("Transfer failed", e);
        } finally {
            if (updateSender != null) updateSender.close();
            if (updateReceiver != null) updateReceiver.close();
            if (recordTx != null) recordTx.close();
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        }
    }
    
    // NEW: Fetch Transaction History
    public List<Map<String, Object>> getRecentTransactions(int userId) {
        List<Map<String, Object>> history = new ArrayList<>();
        // Select last 10 transactions involving this user
        String sql = "SELECT t.id, t.amount, t.created_at, " +
                     "s.username as sender_name, r.username as receiver_name " +
                     "FROM transactions t " +
                     "JOIN users s ON t.sender_id = s.id " +
                     "JOIN users r ON t.receiver_id = r.id " +
                     "WHERE t.sender_id = ? OR t.receiver_id = ? " +
                     "ORDER BY t.created_at DESC LIMIT 10";
                     
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> tx = new HashMap<>();
                    tx.put("id", rs.getInt("id"));
                    tx.put("amount", rs.getBigDecimal("amount"));
                    tx.put("date", rs.getTimestamp("created_at"));
                    
                    String sender = rs.getString("sender_name");
                    String receiver = rs.getString("receiver_name");
                    
                    // Logic to determine type
                    // If current user is sender -> DEBIT. Else -> CREDIT
                    /* Note: This logic belongs in Service or View, but simple map here helps */
                    tx.put("sender", sender);
                    tx.put("receiver", receiver);
                    
                    history.add(tx);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return history;
    }
}
