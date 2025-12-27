package com.bank.service;

import com.bank.dao.AuthDao;
import com.bank.model.User;

public class AuthService {
    private final AuthDao authDao = new AuthDao();

    public User login(String username, String password) {
        if (username == null || password == null) return null;

        username = username.trim().toLowerCase();
        if (username.isEmpty() || password.isEmpty()) return null;

        // Use new Dao method that checks hash
        if (authDao.validateCredentials(username, password)) {
            return authDao.getUserByUsername(username);
        }

        return null;
    }
    public boolean register(String username, String password, String fullName) {
        if (username == null || password == null || fullName == null) return false;
        
        username = username.trim().toLowerCase();
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) return false;

        if (authDao.isUsernameTaken(username)) {
            return false;
        }

        // Hash password
        String passwordHash = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
        
        authDao.createUser(username, passwordHash, fullName);
        return true;
    }
}
