package com.example.v3.dao.impl;

import com.example.v3.dao.ContactDao;
import com.example.v3.model.Contact;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
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
                    contact.setPhoto(rs.getBytes("photo"));
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
    public Contact getContactById(int id) throws SQLException {
        String query = "SELECT * FROM contacts WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Contact(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("name"),
                            resultSet.getString("phone"),
                            resultSet.getBytes("photo")
                    );
                }
            }
        }
        return null;
    }


    @Override
    public void createContact(Contact contact) throws SQLException {
        String query = "INSERT INTO contacts (user_id, name, phone, photo) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, contact.getUserId());
            statement.setString(2, contact.getName());
            statement.setString(3, contact.getPhone());
            statement.setBytes(4, contact.getPhoto());
            statement.executeUpdate();
        }
    }



    @Override
    public void updateContact(Contact contact) {
        String sql = "UPDATE contacts SET name = ?, phone = ?, photo = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getPhone());
            ps.setBytes(3, contact.getPhoto());
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
