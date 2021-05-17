package com.bogdan.dao;

import com.bogdan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDB {

    private final Connection connection;

    public UserDB(Connection connection) {
        this.connection = connection;
    }

    public boolean saveUser(User user) {

        boolean isSet = false;
        String query = "INSERT INTO users(username, email, pass) VALUES(?,?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();
            isSet = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSet;
    }
}
