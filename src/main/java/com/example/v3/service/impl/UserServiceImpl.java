package com.example.v3.service.impl;

import com.example.v3.dao.UserDao;
import com.example.v3.model.User;
import com.example.v3.service.UserService;
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
        // Валидация данных пользователя
        userDao.create(user);
    }

    @Override
    public void updateUser(User user) {
        // Валидация изменений данных пользователя
        userDao.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.delete(id);
    }
}