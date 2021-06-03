package com.bogdan.dao;

import com.bogdan.model.Role;
import com.bogdan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UserDB {

    private final Connection connection;

    public UserDB(Connection connection) {
        this.connection = connection;
    }

    public boolean saveUser(User user) throws SQLException {
        boolean isSet = false;
        String query = "INSERT INTO users(username, email, pass, role_id) VALUES(?,?,?, (SELECT id FROM roles " +
                "WHERE role_name=?))";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole().name().toLowerCase(Locale.ROOT));

            preparedStatement.executeUpdate();
            isSet = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectionDB.disconnect();
        return isSet;
    }

    public User logUser(String email, String pass) throws SQLException {
        User user = null;
        String query = "SELECT users.id, username, email, pass, role_name FROM users INNER JOIN roles ON roles.id=users.role_id " +
                "WHERE email=? AND pass=?";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("pass"));
                    Role role = Role.valueOf(resultSet.getString("role_name").toUpperCase(Locale.ROOT));
                    user.setRole(role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectionDB.disconnect();
        return user;
    }
}
