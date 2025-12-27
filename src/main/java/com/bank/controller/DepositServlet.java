package com.bank.controller;

import com.bank.model.User;
import com.bank.service.BankService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {

    private final BankService bankService = new BankService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = requireLogin(req, resp);
        if (user == null) return;

        req.setAttribute("balance", bankService.getBalance(user.getId()));
        req.getRequestDispatcher("/WEB-INF/views/deposit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = requireLogin(req, resp);
        if (user == null) return;

        String amountStr = req.getParameter("amount");
        BigDecimal amount;

        try {
            amount = new BigDecimal(amountStr);
        } catch (Exception e) {
            req.setAttribute("error", "Amount must be a valid number.");
            req.setAttribute("balance", bankService.getBalance(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/deposit.jsp").forward(req, resp);
            return;
        }

        if (bankService.deposit(user.getId(), amount)) {
            req.setAttribute("success", "Deposit successful.");
        } else {
            req.setAttribute("error", "Deposit failed. Ensure amount is positive.");
        }

        req.setAttribute("balance", bankService.getBalance(user.getId()));
        req.getRequestDispatcher("/WEB-INF/views/deposit.jsp").forward(req, resp);
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
