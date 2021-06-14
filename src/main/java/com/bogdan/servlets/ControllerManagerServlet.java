package com.bogdan.servlets;

import com.bogdan.dao.ConnectionDB;
import com.bogdan.dao.OrderDB;
import com.bogdan.dao.UserDB;
import com.bogdan.model.Order;
import com.bogdan.model.State;
import com.bogdan.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/manager", "/manager/user", "/manager/user/edit", "/manager/user/update"})
public class ControllerManagerServlet extends HttpServlet {

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
                case "/manager/user":
                    showUsersOrders(request, response);
                    break;
                case "/manager/user/edit":
                    showEditFormUsersOrder(request, response);
                    break;
                case "/manager/user/update":
                    updateUsersOrder(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<User> listUsers = userDB.getListUsers();
        request.setAttribute("listUsers", listUsers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager.jsp");
        dispatcher.forward(request, response);
    }

    private void showUsersOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDB.getUserById(id);
        List<Order> listOrders = orderDB.listAllUserOrders(user);
        request.setAttribute("listOrders", listOrders);
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/userOrders.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditFormUsersOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        User user = (User) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDB.getOrder(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderForm.jsp");
        request.setAttribute("order", order);
        request.setAttribute("session", user);
        dispatcher.forward(request, response);
    }

    private void updateUsersOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String orderMessage = request.getParameter("message");
        double price = Double.parseDouble(request.getParameter("price"));
        String worker = request.getParameter("worker");
        State state = State.valueOf(request.getParameter("state"));

        Order upOrder = new Order(id, title, orderMessage, price, worker, state);
        boolean isUpdated = orderDB.updateOrder(upOrder);
        String message;
        if (isUpdated) {
            message = "Order by №: " + id + " updated!";
        } else {
            message = "Order by №: " + id + " not updated!";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager");
        dispatcher.forward(request, response);
    }
}
