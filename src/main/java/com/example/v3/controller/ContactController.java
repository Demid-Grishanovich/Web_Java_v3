package com.example.v3.controller;

import com.example.v3.model.Contact;
import com.example.v3.model.User;
import com.example.v3.service.ContactService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ContactController", urlPatterns = {"/contacts", "/addContact", "/editContact"})
public class ContactController extends HttpServlet {

    private ContactService contactService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        contactService = (ContactService) context.getAttribute("contactService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("/v3_war_exploded/login");
            return;
        }
        String path = request.getServletPath();
        if ("/addContact".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/addContact.jsp").forward(request, response);
        } else if ("/editContact".equals(path)) {
            int contactId = Integer.parseInt(request.getParameter("contactId"));
            Contact contact = contactService.getContactById(contactId);
            request.setAttribute("contact", contact);
            request.getRequestDispatcher("/WEB-INF/views/editContact.jsp").forward(request, response);
        } else {
            List<Contact> contacts = contactService.getAllContactsByUserId(currentUser.getId());
            request.setAttribute("contacts", contacts);
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("/v3_war_exploded/login");
            return;
        }
        processContactAction(request, response, currentUser);
    }

    private void processContactAction(HttpServletRequest request, HttpServletResponse response, User currentUser) throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            createContact(request, currentUser);
        } else if ("update".equals(action)) {
            updateContact(request);
        } else if ("delete".equals(action)) {
            deleteContact(request);
        }
        response.sendRedirect("/v3_war_exploded/dashboard");
    }

    private void createContact(HttpServletRequest request, User currentUser) throws ServletException, IOException {
        Contact newContact = new Contact(
                currentUser.getId(),
                request.getParameter("name"),
                request.getParameter("phone"),
                request.getParameter("photoUrl")
        );
        contactService.createContact(newContact);
    }

    private void updateContact(HttpServletRequest request) throws ServletException, IOException {
        int contactId = Integer.parseInt(request.getParameter("contactId"));
        Contact contact = contactService.getContactById(contactId);
        if (contact != null) {
            contact.setName(request.getParameter("name"));
            contact.setPhone(request.getParameter("phone"));
            contact.setPhotoUrl(request.getParameter("photoUrl"));
            contactService.updateContact(contact);
        } else {
            throw new ServletException("Contact not found");
        }
    }

    private void deleteContact(HttpServletRequest request) throws ServletException, IOException {
        int contactId = Integer.parseInt(request.getParameter("contactId"));
        System.out.println("Attempting to delete contact with ID: " + contactId);
        contactService.deleteContact(contactId);
        System.out.println("Redirecting to dashboard after delete attempt.");
    }

}