package com.bogdan.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static ConnectionFactory connection;

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/agency";
    private static final String USER = "bogdan";
    private static final String PASS = "252525";

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        if (connection == null) {
            connection = new ConnectionFactory();
        }
        return connection;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.getConnection().isClosed()) {
            connection.getConnection().close();
        }
    }
}
