package com.example.v3.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/secured/*")
public class AuthFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
        // Здесь можно добавить код инициализации, если это необходимо
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Проверяем, есть ли атрибут currentUser в сессии
        if (request.getSession().getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        // Здесь можно добавить код очистки, если это необходимо
    }
}
