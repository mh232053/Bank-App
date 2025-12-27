/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bank.service;


import com.bank.model.TransferResult;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BankServiceTest {

    private final BankService service;

    public BankServiceTest() {
        this.service = new BankService(new MockAccountDao());
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    void transfer_fails_on_negative_amount() {
        var result = service.transfer(1, 2, new BigDecimal("-10"));
        assertFalse(result.isSuccess());
    }

    @Test
    void transfer_fails_on_insufficient_balance() {
        var result = service.transfer(1, 2, new BigDecimal("999999"));
        assertFalse(result.isSuccess());
    }

    @Test
    void transfer_succeeds_and_reduces_sender_balance() {
        var before = service.getBalance(1);
        var result = service.transfer(1, 2, new BigDecimal("1000"));
        assertTrue(result.isSuccess());
        assertEquals(before.subtract(new BigDecimal("1000")), result.getNewSenderBalance());
    }

    // Mock Implementation for testing
    private static class MockAccountDao implements com.bank.dao.IAccountDao {
        private final java.util.Map<Integer, BigDecimal> accounts = new java.util.HashMap<>();

        public MockAccountDao() {
            accounts.put(1, new BigDecimal("5000"));
            accounts.put(2, new BigDecimal("1000"));
        }

        @Override
        public BigDecimal getBalance(int userId) {
            return accounts.getOrDefault(userId, BigDecimal.ZERO);
        }

        @Override
        public boolean userExists(int userId) {
            return accounts.containsKey(userId);
        }

        @Override
        public void performTransfer(int fromId, int toId, BigDecimal amount) throws java.sql.SQLException {
             if (!accounts.containsKey(fromId) || !accounts.containsKey(toId)) {
                 throw new java.sql.SQLException("Account not found");
             }
             accounts.put(fromId, accounts.get(fromId).subtract(amount));
             accounts.put(toId, accounts.get(toId).add(amount));
        }

        @Override
        public void deposit(int userId, BigDecimal amount) throws java.sql.SQLException {
            accounts.put(userId, accounts.getOrDefault(userId, BigDecimal.ZERO).add(amount));
        }

        @Override
        public List<Map<String, Object>> getRecentTransactions(int userId) {
            return java.util.Collections.emptyList();
        }
    }
}
