package com.example.v3.dao.impl;

import com.example.v3.dao.ContactDao;
import com.example.v3.model.Contact;
import com.example.v3.config.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements ContactDao {

    private DataSource dataSource = DatabaseConfig.getDataSource();

    @Override
    public Contact findById(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM contacts WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractContactFromResultSet(rs);
            }
        } catch (SQLException ex) {
            // Log and handle exception
        }
        return null;
    }

    @Override
    public List<Contact> findAllByUserId(int userId) {
        List<Contact> contacts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM contacts WHERE user_id = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contacts.add(extractContactFromResultSet(rs));
            }
        } catch (SQLException ex) {
            // Log and handle exception
        }
        return contacts;
    }

    @Override
    public void create(Contact contact) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO contacts (user_id, name, phone, photo_url) VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, contact.getUserId());
            ps.setString(2, contact.getName());
            ps.setString(3, contact.getPhone());
            ps.setString(4, contact.getPhotoUrl());
            ps.executeUpdate();
        } catch (SQLException ex) {
            // Log and handle exception
        }
    }

    @Override
    public void update(Contact contact) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE contacts SET name = ?, phone = ?, photo_url = ? WHERE id = ?")) {
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getPhotoUrl());
            ps.setInt(4, contact.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            // Log and handle exception
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM contacts WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            // Log and handle exception
        }
    }

    private Contact extractContactFromResultSet(ResultSet rs) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("id"));
        contact.setUserId(rs.getInt("user_id"));
        contact.setName(rs.getString("name"));
        contact.setPhone(rs.getString("phone"));
        contact.setPhotoUrl(rs.getString("photo_url"));
        return contact;
    }
}

