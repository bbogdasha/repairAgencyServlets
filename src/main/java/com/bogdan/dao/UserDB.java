package com.bogdan.dao;

import com.bogdan.model.Role;
import com.bogdan.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDB {

    boolean saveUser(User user) throws SQLException;

    User logUser(String email, String pass) throws SQLException;

    List<User> getListUsers() throws SQLException;

    User getUserById(int userId) throws SQLException;

    User getUserByEmail(String userEmail) throws SQLException;

    List<User> getUsersByName(String username) throws SQLException;

    List<User> getUsersByRole(Role nameRole) throws SQLException;

    Role getRole(int roleId) throws SQLException;

    boolean deleteUser(int idUser) throws SQLException;
}
