package com.example.v3.controller;

import com.example.v3.model.Contact;
import com.example.v3.model.User;
import com.example.v3.service.ContactService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@WebServlet(name = "ContactController", urlPatterns = {"/contacts", "/addContact", "/editContact", "/dashboard", "/contactPhoto"})
@MultipartConfig
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
        System.out.println("Request path: " + path); // Логируем путь запроса
        switch (path) {
            case "/addContact":
                System.out.println("Navigating to addContact page");
                request.getRequestDispatcher("/WEB-INF/views/addContact.jsp").forward(request, response);
                break;
            case "/editContact":
                int contactId = Integer.parseInt(request.getParameter("contactId"));
                Contact contact = contactService.getContactById(contactId);
                request.setAttribute("contact", contact);
                System.out.println("Navigating to editContact page for contactId: " + contactId);
                request.getRequestDispatcher("/WEB-INF/views/editContact.jsp").forward(request, response);
                break;
            case "/contactPhoto":
                System.out.println("Handling contact photo request");
                handleContactPhoto(request, response);
                break;
            case "/dashboard":
            case "/contacts":
            default:
                System.out.println("Navigating to dashboard");
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
        if (action == null || action.isEmpty()) {
            throw new ServletException("Action parameter is missing");
        }
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
            default:
                throw new ServletException("Unknown action: " + action);
        }
        response.sendRedirect("/v3_war_exploded/contacts");
    }

    private void createContact(HttpServletRequest request, User currentUser) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Part filePart = request.getPart("photo");
        byte[] photo = null;

        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                photo = inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServletException("Error reading photo upload", e);
            }
        }

        if (name == null || phone == null || name.isEmpty() || phone.isEmpty()) {
            throw new ServletException("Name and phone are required");
        }

        Contact newContact = new Contact(
                currentUser.getId(),
                name,
                phone,
                photo
        );
        contactService.createContact(newContact);
    }

    private void updateContact(HttpServletRequest request) throws ServletException, IOException {
        int contactId = Integer.parseInt(request.getParameter("contactId"));
        Contact contact = contactService.getContactById(contactId);
        if (contact != null) {
            contact.setName(request.getParameter("name"));
            contact.setPhone(request.getParameter("phone"));

            Part filePart = request.getPart("photo");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    contact.setPhoto(inputStream.readAllBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ServletException("Error reading photo upload", e);
                }
            }

            contactService.updateContact(contact);
        } else {
            throw new ServletException("Contact not found");
        }
    }

    private void deleteContact(HttpServletRequest request) throws ServletException, IOException {
        int contactId = Integer.parseInt(request.getParameter("contactId"));
        contactService.deleteContact(contactId);
    }

    private void handleContactPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int contactId = Integer.parseInt(request.getParameter("id"));
        System.out.println("Handling photo request for contact ID: " + contactId);
        Contact contact = contactService.getContactById(contactId);
        if (contact != null) {
            System.out.println("Contact found: " + contact.getName());
            if (contact.getPhoto() != null) {
                System.out.println("Photo found for contact ID: " + contactId + " with size: " + contact.getPhoto().length + " bytes");
                response.setContentType("image/jpeg");
                response.setContentLength(contact.getPhoto().length);
                try (OutputStream out = response.getOutputStream()) {
                    out.write(contact.getPhoto());
                }
            } else {
                System.out.println("No photo found for contact ID: " + contactId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            System.out.println("No contact found with ID: " + contactId);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }



}
