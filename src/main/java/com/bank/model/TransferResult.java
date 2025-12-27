/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bank.model;

import java.math.BigDecimal;

public class TransferResult {
    private final boolean success;
    private final String message;
    private final BigDecimal newSenderBalance;

    public TransferResult(boolean success, String message, BigDecimal newSenderBalance) {
        this.success = success;
        this.message = message;
        this.newSenderBalance = newSenderBalance;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public BigDecimal getNewSenderBalance() {
        return newSenderBalance;
    }
}
