package com.bank.service;

import com.bank.dao.AccountDao;
import com.bank.dao.IAccountDao;
import com.bank.model.TransferResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BankService {
    private final IAccountDao accountDao;

    public BankService() {
        this.accountDao = new AccountDao();
    }

    public BankService(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public BigDecimal getBalance(int userId) {
        return accountDao.getBalance(userId);
    }
    
    public List<Map<String, Object>> getTransactionHistory(int userId) {
        return accountDao.getRecentTransactions(userId);
    }

    public boolean deposit(int userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        try {
            // Interface now supports deposit directly
            accountDao.deposit(userId, amount); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public TransferResult transfer(int fromUserId, int toUserId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new TransferResult(false, "Amount must be positive.", getBalance(fromUserId));
        }

        if (fromUserId == toUserId) {
            return new TransferResult(false, "Cannot transfer to yourself.", getBalance(fromUserId));
        }

        if (!accountDao.userExists(toUserId)) {
            return new TransferResult(false, "Recipient account not found.", getBalance(fromUserId));
        }

        BigDecimal senderBalance = getBalance(fromUserId);
        if (senderBalance.compareTo(amount) < 0) {
            return new TransferResult(false, "Insufficient balance.", senderBalance);
        }

        // Perform the transfer via atomic JDBC transaction
        try {
            accountDao.performTransfer(fromUserId, toUserId, amount);
            return new TransferResult(true, "Transfer successful.", getBalance(fromUserId));
        } catch (Exception e) {
            e.printStackTrace();
            return new TransferResult(false, "Transfer failed due to system error.", senderBalance);
        }
    }
}
