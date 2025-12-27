/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bank.controller;

import com.bank.model.TransferResult;
import com.bank.model.User;
import com.bank.service.BankService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {

    private final BankService bankService = new BankService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = requireLogin(req, resp);
        if (user == null) return;

        req.setAttribute("balance", bankService.getBalance(user.getId()));
        req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = requireLogin(req, resp);
        if (user == null) return;

        String toUserIdStr = req.getParameter("toUserId");
        String amountStr = req.getParameter("amount");

        int toUserId;
        BigDecimal amount;

        try {
            toUserId = Integer.parseInt(toUserIdStr);
        } catch (Exception e) {
            req.setAttribute("error", "Receiver User ID must be a number.");
            req.setAttribute("balance", bankService.getBalance(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
            return;
        }

        try {
            amount = new BigDecimal(amountStr);
        } catch (Exception e) {
            req.setAttribute("error", "Amount must be a valid number.");
            req.setAttribute("balance", bankService.getBalance(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
            return;
        }

        TransferResult result = bankService.transfer(user.getId(), toUserId, amount);

        req.setAttribute(result.isSuccess() ? "success" : "error", result.getMessage());
        req.setAttribute("balance", result.getNewSenderBalance());

        req.getRequestDispatcher("/WEB-INF/views/transfer.jsp").forward(req, resp);
    }

    private User requireLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return null;
        }
        return (User) session.getAttribute("user");
    }
}
