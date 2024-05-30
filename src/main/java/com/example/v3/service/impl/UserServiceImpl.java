package com.example.v3.service.impl;

import com.example.v3.dao.UserDao;
import com.example.v3.model.User;
import com.example.v3.service.UserService;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void createUser(User user) {
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.create(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.delete(id);
    }

    @Override
    public boolean checkPassword(String providedPassword, String storedPassword) {
        String hashedPassword = encryptPassword(providedPassword);
        String hashedProvidedPassword = encryptPassword(hashedPassword);
        return hashedProvidedPassword.equals(storedPassword);
    }

    @Override
    public String encryptPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }
}
