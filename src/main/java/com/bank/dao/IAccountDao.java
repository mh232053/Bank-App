/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bank.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IAccountDao {

    BigDecimal getBalance(int userId);

    boolean userExists(int userId);

    void performTransfer(int fromId, int toId, BigDecimal amount) throws SQLException;

    void deposit(int userId, BigDecimal amount) throws SQLException;

    List<Map<String, Object>> getRecentTransactions(int userId);
}
