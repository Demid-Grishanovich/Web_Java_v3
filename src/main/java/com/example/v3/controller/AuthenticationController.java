package com.example.v3.controller;

import com.example.v3.model.User;
import com.example.v3.model.Contact;
import com.example.v3.service.UserService;
import com.example.v3.service.ContactService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AuthenticationController", urlPatterns = {"/login", "/register", "/dashboard", "/logout"})
public class AuthenticationController extends HttpServlet {

    private UserService userService;
    private ContactService contactService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        userService = (UserService) context.getAttribute("userService");
        contactService = (ContactService) context.getAttribute("contactService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/login":
                forwardToLogin(request, response);
                break;
            case "/register":
                forwardToRegister(request, response);
                break;
            case "/dashboard":
                handleDashboardAccess(request, response);
                break;
            case "/logout":
                logoutUser(request, response);
                break;
            default:
                response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/login".equals(path)) {
            processLogin(request, response);
        } else if ("/register".equals(path)) {
            processRegistration(request, response);
        }
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    private void forwardToRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    private void handleDashboardAccess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser != null) {
            List<Contact> contacts = contactService.getAllContactsByUserId(currentUser.getId());
            request.setAttribute("contacts", contacts);
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("login");
    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Username and password must not be empty");
            forwardToLogin(request, response);
        } else {
            User user = userService.getUserByUsername(username);
            if (user != null && userService.checkPassword(password, user.getPassword())) {
                request.getSession().setAttribute("currentUser", user);
                response.sendRedirect("dashboard");
            } else {
                request.setAttribute("error", "Invalid username or password");
                forwardToLogin(request, response);
            }
        }
    }

    private void processRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "All fields are required");
            forwardToRegister(request, response);
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("error", "Invalid email format");
            forwardToRegister(request, response);
        } else {
            handleRegistration(request, response, username, email, password);
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response, String username, String email, String password) throws IOException, ServletException {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            request.setAttribute("error", "Username already exists");
            forwardToRegister(request, response);
        } else {
            User newUser = new User(username, email, userService.encryptPassword(password));
            userService.createUser(newUser);
            request.getSession().setAttribute("currentUser", newUser);
            response.sendRedirect("dashboard");
        }
    }
}
