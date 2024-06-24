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
    private DataSource dataSource;

    public ContactDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Contact> getContactsByPage(int userId, int page, int pageSize) {
        List<Contact> contacts = new ArrayList<>();
        int start = (page - 1) * pageSize;
        String sql = "SELECT * FROM contacts WHERE user_id = ? LIMIT ? OFFSET ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, pageSize);
            ps.setInt(3, start);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact();
                    contact.setId(rs.getInt("id"));
                    contact.setUserId(rs.getInt("user_id"));
                    contact.setName(rs.getString("name"));
                    contact.setPhone(rs.getString("phone"));
                    contact.setPhotoUrl(rs.getString("photo_url"));
                    contacts.add(contact);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contacts;
    }

    @Override
    public int getTotalContactsByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM contacts WHERE user_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Contact getContactById(int contactId) {
        String sql = "SELECT * FROM contacts WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, contactId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Contact contact = new Contact();
                    contact.setId(rs.getInt("id"));
                    contact.setUserId(rs.getInt("user_id"));
                    contact.setName(rs.getString("name"));
                    contact.setPhone(rs.getString("phone"));
                    contact.setPhotoUrl(rs.getString("photo_url"));
                    return contact;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void createContact(Contact contact) {
        String sql = "INSERT INTO contacts (user_id, name, phone, photo_url) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, contact.getUserId());
            ps.setString(2, contact.getName());
            ps.setString(3, contact.getPhone());
            ps.setString(4, contact.getPhotoUrl());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContact(Contact contact) {
        String sql = "UPDATE contacts SET name = ?, phone = ?, photo_url = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getPhotoUrl());
            ps.setInt(4, contact.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContact(int contactId) {
        String sql = "DELETE FROM contacts WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, contactId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


