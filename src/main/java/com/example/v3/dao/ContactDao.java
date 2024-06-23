package com.example.v3.dao;

import com.example.v3.model.Contact;
import java.util.List;

public interface ContactDao {
    Contact findById(int id);
    List<Contact> findAllByUserId(int userId);
    void create(Contact contact);
    void update(Contact contact);
    void delete(int id);

}