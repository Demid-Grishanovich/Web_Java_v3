package com.example.v3.service.impl;

import com.example.v3.dao.ContactDao;
import com.example.v3.model.Contact;
import com.example.v3.service.ContactService;
import java.util.List;

public class ContactServiceImpl implements ContactService {

    private ContactDao contactDao;

    public ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public Contact getContactById(int id) {
        return contactDao.findById(id);
    }

    @Override
    public List<Contact> getAllContactsByUserId(int userId) {
        return contactDao.findAllByUserId(userId);
    }

    @Override
    public void createContact(Contact contact) {
        contactDao.create(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        contactDao.update(contact);
    }

    @Override
    public void deleteContact(int id) {
        contactDao.delete(id);
    }
}
