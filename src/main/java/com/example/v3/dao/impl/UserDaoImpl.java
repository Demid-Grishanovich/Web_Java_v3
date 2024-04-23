package com.example.v3.dao.impl;

import com.example.v3.dao.UserDao;
import com.example.v3.model.User;
import com.example.v3.config.DatabaseConfig;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private DataSource dataSource = DatabaseConfig.getDataSource();

    @Override
    public User findById(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            // Log and handle exception
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException ex) {
            // Log and handle exception
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); // Нужно добавить хэширование
            ps.executeUpdate();
        } catch (SQLException ex) {
            // Логирование или обработка исключения
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(int id) {

    }

    // Implement other methods using similar patterns
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
        if (rs.getTimestamp("last_login") != null) {
            user.setLastLogin(rs.getTimestamp("last_login").toLocalDateTime());
        }
        return user;
    }
}
