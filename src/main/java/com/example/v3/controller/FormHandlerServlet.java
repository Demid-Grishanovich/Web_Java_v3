package com.example.v3.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class FormHandlerServlet
 * This servlet handles form submission and redirects to a success page
 * to prevent form resubmission on page refresh.
 */
@WebServlet("/submitForm")
public class FormHandlerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormHandlerServlet() {
        super();
    }

    /**
     * Handles the POST request by processing the form data and redirecting
     * to a success page.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Example of handling form data
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        // Process the data (you might want to save it to a database, etc.)

        // Log for demonstration
        System.out.println("Received username: " + username + ", email: " + email);

        // Redirect to prevent form resubmission
        response.sendRedirect(request.getContextPath() + "/formSuccess.jsp");
    }
}
