package com.bogdan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static Connection connection;

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/agency";
    private static final String USER = "bogdan";
    private static final String PASS = "252525";

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName(DRIVER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
