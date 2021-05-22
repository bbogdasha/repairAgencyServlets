package com.bogdan.servlets;

import com.bogdan.dao.ConnectionDB;
import com.bogdan.dao.UserDB;
import com.bogdan.model.Role;
import com.bogdan.model.User;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uname = request.getParameter("uname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(Role.USER, uname, email, password);
        UserDB userDB = new UserDB(ConnectionDB.getConnection());

        try {
            if (userDB.saveUser(user)) {
                response.getWriter().print("Data entered successfully");
            } else {
                response.getWriter().print("Data not entered");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}