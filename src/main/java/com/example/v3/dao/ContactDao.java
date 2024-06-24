package com.example.v3.dao;

import com.example.v3.model.Contact;
import java.util.List;

public interface ContactDao {
    List<Contact> getContactsByPage(int userId, int page, int pageSize);
    int getTotalContactsByUserId(int userId);
    Contact getContactById(int contactId);
    void createContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(int contactId);
}
