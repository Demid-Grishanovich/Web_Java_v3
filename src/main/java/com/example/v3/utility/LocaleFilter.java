package com.example.v3.utility;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация, если необходима
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String lang = request.getParameter("lang");
        if (lang != null) {
            Locale locale = new Locale(lang);
            session.setAttribute("lang", lang);
            session.setAttribute("locale", locale);
        } else if (session.getAttribute("locale") == null) {
            session.setAttribute("locale", req.getLocale());
        }

        Locale locale = (Locale) session.getAttribute("locale");
        if (locale != null) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages/messages", locale);
            request.setAttribute("bundle", bundle);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Уничтожение, если необходимо
    }
}
