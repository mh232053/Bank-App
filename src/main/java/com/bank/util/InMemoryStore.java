/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bank.util;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStore {
    // username -> userId
    public static final Map<String, Integer> USERS = new ConcurrentHashMap<>();

    // userId -> fullName
    public static final Map<Integer, String> NAMES = new ConcurrentHashMap<>();

    // username -> bcrypt password hash
    public static final Map<String, String> PASSWORDS = new ConcurrentHashMap<>();

    // userId -> balance
    public static final Map<Integer, BigDecimal> BALANCES = new ConcurrentHashMap<>();

    static {
        // Seed demo users (password: 1234)
        // We'll hash them in AuthDao static init.
        USERS.put("ali", 1);
        NAMES.put(1, "Ali Khan");
        BALANCES.put(1, new BigDecimal("50000.00"));

        USERS.put("sara", 2);
        NAMES.put(2, "Sara Ahmed");
        BALANCES.put(2, new BigDecimal("120000.00"));
    }

    private InMemoryStore() {}
}
