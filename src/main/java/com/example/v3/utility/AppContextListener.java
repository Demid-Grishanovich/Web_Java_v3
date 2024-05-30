package com.example.v3.utility;

import com.example.v3.dao.ContactDao;
import com.example.v3.dao.impl.ContactDaoImpl;
import com.example.v3.service.ContactService;
import com.example.v3.service.UserService;
import com.example.v3.service.impl.ContactServiceImpl;
import com.example.v3.service.impl.UserServiceImpl;
import com.example.v3.dao.UserDao;
import com.example.v3.dao.impl.UserDaoImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // Инициализация UserService
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        context.setAttribute("userService", userService);

        // Инициализация ContactService
        ContactDao contactDao = new ContactDaoImpl();
        ContactService contactService = new ContactServiceImpl(contactDao);
        context.setAttribute("contactService", contactService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup code if needed
    }
}
