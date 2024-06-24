package com.example.v3.config;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/Jakarta";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "root";

    private static BasicDataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DatabaseConfig.class) {
                if (dataSource == null) {
                    dataSource = new BasicDataSource();
                    dataSource.setUrl(URL);
                    dataSource.setUsername(USERNAME);
                    dataSource.setPassword(PASSWORD);
                    dataSource.setMinIdle(5);
                    dataSource.setMaxIdle(10);
                    dataSource.setMaxOpenPreparedStatements(100);
                }
            }
        }
        return dataSource;
    }
}
