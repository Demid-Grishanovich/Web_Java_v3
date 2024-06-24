package com.example.v3.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("/deleteCookie")
public class DeleteCookieServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie userCookie = new Cookie("username", "");
        userCookie.setMaxAge(0);  // Устанавливаем время жизни cookie в 0, чтобы удалить его
        response.addCookie(userCookie);  // Добавляем модифицированный cookie в ответ

        response.getWriter().write("Cookie deleted successfully");
    }
}
