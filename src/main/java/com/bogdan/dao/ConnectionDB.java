package com.bogdan.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {

    private static Connection connection;

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/agency";
    private static final String USER = "bogdan";
    private static final String PASS = "252525";

    public static Connection getConnection() {

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
