package com.bogdan.servlets;

import com.bogdan.dao.ConnectionFactory;
import com.bogdan.dao.UserDBImpl;
import com.bogdan.model.Role;
import com.bogdan.model.User;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
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

        String page = "/login.jsp";
        String message;

        User user = new User(Role.USER, uname, email, password);

        try {
            UserDBImpl userDBImpl = new UserDBImpl(ConnectionFactory.getInstance().getConnection());

            if (userDBImpl.saveUser(user)) {
                message = "You have successfully registered!";
            } else {
                message = "Registration failed!";
                page = "/registration.jsp";
            }
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}