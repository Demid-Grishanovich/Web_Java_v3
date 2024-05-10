package com.example.v3.controller;


import com.example.v3.model.User;
import com.example.v3.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AuthenticationController", urlPatterns = {"/login", "/register" , "/dashboard", "/logout"})
public class AuthenticationController extends HttpServlet {

    private UserService userService;

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
        } else if ("/dashboard".equals(path)) {
            // Проверка, что пользователь в сессии существует
            if (request.getSession().getAttribute("currentUser") != null) {
                request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
            } else {
                response.sendRedirect("login"); // Перенаправление на страницу входа, если пользователь не залогинен
            }
        } else if ("/logout".equals(path)) {
            request.getSession().invalidate(); // Удаление всех атрибутов сессии
            response.sendRedirect("/v3_war_exploded/"); // Перенаправление на страницу входа после выхода
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

            User user = userService.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) { // Здесь должно быть безопасное сравнение хэшированных паролей
                request.getSession().setAttribute("currentUser", user);
                System.out.println("Redirecting to dashboard...");
                System.out.println("Current user: " + request.getSession().getAttribute("currentUser"));
                response.sendRedirect(request.getContextPath() + "/dashboard"); // Перенаправление на страницу dashboard
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
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