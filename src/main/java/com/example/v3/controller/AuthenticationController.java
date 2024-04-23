package com.example.v3.controller;


import com.example.v3.model.User;
import com.example.v3.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AuthenticationController", urlPatterns = {"/login", "/register"})
public class AuthenticationController extends HttpServlet {

    @Inject
    private UserService userService;

    public AuthenticationController() {
        // Конструктор по умолчанию
    }
    @Override
    public void init() {
        ServletContext context = getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/login".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        } else if ("/register".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/login".equals(path)) {
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            if (username.isEmpty() || password.isEmpty()) {
                request.setAttribute("error", "Username and password must not be empty");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }

        } else if ("/register".equals(path)) {
            String username = request.getParameter("username").trim();
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                request.setAttribute("error", "All fields are required");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                request.setAttribute("error", "Invalid email format");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            } else {
                handleRegistration(request, response);
            }

        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            request.setAttribute("error", "Username already exists");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password); // В реальных приложениях следует использовать хэширование паролей
            userService.createUser(newUser);
            request.getSession().setAttribute("currentUser", newUser);
            response.sendRedirect("/home");
        }

    }
}