package com.example.v3.controller;

import com.example.v3.model.Contact;
import com.example.v3.model.User;
import com.example.v3.service.ContactService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ContactController", urlPatterns = {"/contacts", "/addContact", "/editContact", "/dashboard"})
public class ContactController extends HttpServlet {

    private ContactService contactService;
    private static final int RECORDS_PER_PAGE = 5;

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
        switch (path) {
            case "/addContact":
                request.getRequestDispatcher("/WEB-INF/views/addContact.jsp").forward(request, response);
                break;
            case "/editContact":
                int contactId = Integer.parseInt(request.getParameter("contactId"));
                Contact contact = contactService.getContactById(contactId);
                request.setAttribute("contact", contact);
                request.getRequestDispatcher("/WEB-INF/views/editContact.jsp").forward(request, response);
                break;
            case "/dashboard":
            case "/contacts":
            default:
                handleDashboard(request, response, currentUser);
                break;
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

    private void handleDashboard(HttpServletRequest request, HttpServletResponse response, User currentUser) throws ServletException, IOException {
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }
        int userId = currentUser.getId();
        List<Contact> contacts = contactService.getContactsByPage(userId, currentPage, RECORDS_PER_PAGE);
        int totalRecords = contactService.getTotalContactsByUserId(userId);
        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);

        request.setAttribute("contacts", contacts);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    private void processContactAction(HttpServletRequest request, HttpServletResponse response, User currentUser) throws IOException, ServletException {
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                createContact(request, currentUser);
                break;
            case "update":
                updateContact(request);
                break;
            case "delete":
                deleteContact(request);
                break;
        }
        response.sendRedirect("/v3_war_exploded/contacts");
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
        contactService.deleteContact(contactId);
    }
}
