package com.example.v3;

import com.example.v3.dao.UserDao;
import com.example.v3.dao.impl.UserDaoImpl;
import com.example.v3.service.UserService;
import com.example.v3.service.impl.UserServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        // Инициализация UserDao и UserService
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);

        // Сохранение экземпляра UserService в ServletContext
        ctx.setAttribute("userService", userService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Здесь можно освободить ресурсы, если это необходимо
    }
}
