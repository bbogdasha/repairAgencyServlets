package com.bogdan.dao;

import com.bogdan.model.Role;
import com.bogdan.model.User;
import com.bogdan.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserDBImpl implements UserDB {

    private final Connection connection;

    public UserDBImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean saveUser(User user) throws SQLException {
        String query = "INSERT INTO users(username, email, pass, role_id) VALUES(?,?,?, (SELECT id FROM roles " +
                "WHERE role_name=?))";

        boolean isSet = false;

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
        ConnectionFactory.disconnect();
        return isSet;
    }

    public User logUser(String email, String pass) throws SQLException {
        String query = "SELECT users.id, username, email, pass, role_name FROM users " +
                "INNER JOIN roles ON roles.id=users.role_id WHERE email=? AND pass=?";

        User user = null;

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
        ConnectionFactory.disconnect();
        return user;
    }

    public List<User> getListUsers() throws SQLException {
        String query = "SELECT id, username, email, role_id FROM users";

        List<User> users = new ArrayList<>();

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    int role_id = resultSet.getInt("role_id");
                    Role role = getRole(role_id);

                    User user = new User(id, role, name, email);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.disconnect();
        return users;
    }

    public User getUserById(int userId) throws SQLException {
        String query = "SELECT id, username, email, pass, role_id FROM users WHERE id=?";

        User user = null;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("pass");
                    int role_id = resultSet.getInt("role_id");
                    Role role = getRole(role_id);

                    user = new User(id, role, name, email, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByEmail(String userEmail) throws SQLException {
        String query = "SELECT id, username, email, pass, role_id FROM users WHERE email=?";

        User user = null;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, userEmail);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("pass");
                    int role_id = resultSet.getInt("role_id");
                    Role role = getRole(role_id);

                    user = new User(id, role, name, email, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getUsersByName(String username) throws SQLException {
        String query = "SELECT id, username, email, pass, role_id FROM users WHERE username=?";

        List<User> users = new ArrayList<>();

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("pass");
                    int role_id = resultSet.getInt("role_id");
                    Role role = getRole(role_id);

                    User user = new User(id, role, name, email, password);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.disconnect();
        return users;
    }

    public List<User> getUsersByRole(Role nameRole) throws SQLException {
        String query = "SELECT users.id, username, email, pass, role_id FROM users " +
                "INNER JOIN roles ON roles.id=users.role_id WHERE roles.role_name=?";

        List<User> workers = new ArrayList<>();
        User user = null;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameRole.name().toLowerCase());

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("pass");
                    int role_id = resultSet.getInt("role_id");
                    Role role = getRole(role_id);

                    user = new User(id, role, name, email, password);
                    workers.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }

    public Role getRole(int roleId) throws SQLException {
        String query = "SELECT role_name FROM roles WHERE id=?";

        Role role_name = null;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roleId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("role_name");
                    role_name = Role.valueOf(name.toUpperCase(Locale.ROOT));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role_name;
    }

    public boolean deleteUser(int idUser) throws SQLException {
        String query = "DELETE FROM users WHERE id=?;";
        boolean rowDeleted = false;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idUser);

            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.disconnect();
        return rowDeleted;
    }
}
