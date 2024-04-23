package com.example.v3.dao;

import com.example.v3.model.User;
import java.util.List;

public interface UserDao {
    User findById(int id);
    User findByUsername(String username);
    List<User> findAll();
    void create(User user);
    void update(User user);
    void delete(int id);
}