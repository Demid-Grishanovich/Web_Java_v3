package com.example.v3.service;

import com.example.v3.model.User;
import java.util.List;

public interface UserService {
    User getUserById(int id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    boolean checkPassword(String providedPassword, String storedPassword);
    String encryptPassword(String password);
}
