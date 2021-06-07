package com.bogdan.dao;

import com.bogdan.model.Role;
import com.bogdan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public User getUserById(int userId) {
        String query = "SELECT id, username, email, pass, " +
                "(SELECT role_name FROM roles INNER JOIN users ON users.role_id=roles.id WHERE users.id=?) " +
                "FROM users WHERE id=?";

        User user = null;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("pass");
                    Role role = Role.valueOf(resultSet.getString("role_name").toUpperCase(Locale.ROOT));

                    user = new User(id, role, name, email, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getUsersByName(String username) {
        String query = "SELECT username, email, pass, " +
                "(SELECT role_name FROM roles INNER JOIN users ON users.role_id=roles.id WHERE users.username=?) " +
                "FROM users WHERE username=?";

        List<User> users = new ArrayList<>();

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, username);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("pass");
                    Role role = Role.valueOf(resultSet.getString("role_name").toUpperCase(Locale.ROOT));

                    User user = new User(role, name, email, password);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
