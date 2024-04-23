package com.example.v3.controller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ErrorHandlingServlet", urlPatterns = "/error")
public class ErrorHandlingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        request.setAttribute("statusCode", statusCode);
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("exception", throwable);
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}


