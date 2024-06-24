package com.example.v3.dao;

import com.example.v3.model.Contact;

import java.sql.SQLException;
import java.util.List;

public interface ContactDao {
    List<Contact> getContactsByPage(int userId, int page, int pageSize);
    int getTotalContactsByUserId(int userId);
    Contact getContactById(int contactId) throws SQLException;
    void createContact(Contact contact) throws SQLException;
    void updateContact(Contact contact);
    void deleteContact(int contactId);
}
