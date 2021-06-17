package com.bogdan.servlets;


import com.bogdan.dao.ConnectionDB;
import com.bogdan.dao.OrderDB;
import com.bogdan.dao.UserDB;
import com.bogdan.model.Role;
import com.bogdan.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/manager/filter")
public class FilterServlet extends HttpServlet {

    private UserDB userDB;
    private OrderDB orderDB;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            userDB = new UserDB(ConnectionDB.getConnection());
            orderDB = new OrderDB(ConnectionDB.getConnection());
            switch (action) {
                case "/manager/filter":
                    filterUsers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void filterUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        User session = (User) request.getSession().getAttribute("user");
        String filterType = request.getParameter("type");
        String filterText = request.getParameter("filter");
        List<User> listUsers = new ArrayList<>();
        if (filterText != null && !filterText.isEmpty()) {
            switch (filterType) {
                case "byUsername":
                    listUsers = userDB.getUsersByName(filterText);
                    break;
                case "byUserEmail":
                    User user = userDB.getUserByEmail(filterText);
                    listUsers.add(user);
                    break;
                case "byRole":
                    listUsers = userDB.getUsersByRole(Role.valueOf(filterText));
                    break;
            }
        } else {
            listUsers = userDB.getListUsers();
            request.setAttribute("listUsers", listUsers);
        }
        if (listUsers.isEmpty()) {
            request.setAttribute("message", "No one with this name was found:(");
        }
        request.setAttribute("listUsers", listUsers);
        request.setAttribute("roles", Role.values());
        request.setAttribute("session", session);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager.jsp");
        dispatcher.forward(request, response);
    }
}
