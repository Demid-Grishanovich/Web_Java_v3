package com.example.v3.service;

import com.example.v3.model.Contact;
import java.util.List;

public interface ContactService {
    Contact getContactById(int id);
    List<Contact> getAllContactsByUserId(int userId);
    void createContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(int id);

}