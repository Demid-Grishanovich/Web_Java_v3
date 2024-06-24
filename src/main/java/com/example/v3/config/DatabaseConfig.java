package com.example.v3.config;

import net.ttddyy.dsproxy.support.ProxyDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;


import javax.sql.DataSource;

public class DatabaseConfig {
    private static final ProxyDataSource dataSource;




    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        // Настройки HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/Jakarta");  // Измените URL на ваш
        config.setUsername("postgres");  // Измените имя пользователя
        config.setPassword("root");  // Измените пароль
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        // Интеграция Proxy DataSource для логирования SQL запросов
        dataSource = ProxyDataSourceBuilder.create(new HikariDataSource(config))
                .name("HikariCP-Proxy")
                .listener(new SLF4JQueryLoggingListener())
                .build();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
