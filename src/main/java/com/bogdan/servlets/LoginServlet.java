package com.bogdan.servlets;

import com.bogdan.dao.ConnectionDB;
import com.bogdan.dao.UserDB;
import com.bogdan.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String page = "login.jsp";

        UserDB userDB = new UserDB(ConnectionDB.getConnection());

        try {
            User user = userDB.logUser(email, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                page = "home.jsp";
            } else {
                String message = "Invalid email or password!";
                request.setAttribute("message", message);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
