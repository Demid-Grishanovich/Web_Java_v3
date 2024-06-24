package com.example.v3.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class ActivityLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String user = (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "Guest";

        System.out.println("User " + user + " accessed " + uri + " via " + method);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}
