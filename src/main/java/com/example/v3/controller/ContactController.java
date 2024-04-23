package com.example.v3.controller;

import com.example.v3.model.Contact;
import com.example.v3.model.User;
import com.example.v3.service.ContactService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ContactController", urlPatterns = {"/contacts"})
public class ContactController extends HttpServlet {

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/login");
            return;
        }
        List<Contact> contacts = contactService.getAllContactsByUserId(userId);
        request.setAttribute("contacts", contacts);
        request.getRequestDispatcher("/WEB-INF/views/contacts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            Contact newContact = new Contact();
            newContact.setUserId(((User) request.getSession().getAttribute("currentUser")).getId());
            newContact.setName(request.getParameter("name"));
            newContact.setPhone(request.getParameter("phone"));
            newContact.setPhotoUrl(request.getParameter("photoUrl")); // URL или путь к файлу фотографии
            contactService.createContact(newContact);
            response.sendRedirect("/contacts");
        } else if ("update".equals(action)) {
            handleUpdateContact(request, response);
        } else if ("delete".equals(action)) {
            handleDeleteContact(request, response);
        }
    }
    private void handleUpdateContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int contactId = Integer.parseInt(request.getParameter("contactId"));
        Contact contact = contactService.getContactById(contactId);
        if (contact != null) {
            contact.setName(request.getParameter("name"));
            contact.setPhone(request.getParameter("phone"));
            contact.setPhotoUrl(request.getParameter("photoUrl"));
            contactService.updateContact(contact);
            response.sendRedirect("/contacts");
        } else {
            request.setAttribute("error", "Contact not found");
            request.getRequestDispatcher("/WEB-INF/views/contacts.jsp").forward(request, response);
        }
    }
    private void handleDeleteContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int contactId = Integer.parseInt(request.getParameter("contactId"));
        contactService.deleteContact(contactId);
        response.sendRedirect("/contacts");
    }
}