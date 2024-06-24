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
    public List<Contact> getContactsByPage(int userId, int page, int pageSize) {
        return contactDao.getContactsByPage(userId, page, pageSize);
    }

    @Override
    public int getTotalContactsByUserId(int userId) {
        return contactDao.getTotalContactsByUserId(userId);
    }

    @Override
    public Contact getContactById(int contactId) {
        return contactDao.getContactById(contactId);
    }

    @Override
    public void createContact(Contact contact) {
        contactDao.createContact(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        contactDao.updateContact(contact);
    }

    @Override
    public void deleteContact(int contactId) {
        contactDao.deleteContact(contactId);
    }
}