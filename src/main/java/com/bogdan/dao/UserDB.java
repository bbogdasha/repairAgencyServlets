package com.bogdan.dao;

import com.bogdan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    private final Connection connection;

    public UserDB(Connection connection) {
        this.connection = connection;
    }

    public boolean saveUser(User user) throws SQLException {

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
        connection.close();
        return isSet;
    }

    public User logUser(String email, String pass) throws SQLException {

        User user = null;
        String query = "SELECT * FROM users WHERE email=? AND pass=?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("pass"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return user;
    }
}
