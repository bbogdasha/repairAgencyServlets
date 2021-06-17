package com.bogdan.servlets;

import com.bogdan.dao.ConnectionDB;
import com.bogdan.dao.OrderDB;
import com.bogdan.dao.UserDB;
import com.bogdan.model.Order;
import com.bogdan.model.Role;
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

@WebServlet({"/manager", "/manager/user", "/manager/user/edit", "/manager/user/update", "/manager/user/delete", "/manager/delete"})
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
                case "/manager/user/delete":
                    deleteUsersOrder(request, response);
                    break;
                case "/manager/delete":
                    deleteUser(request, response);
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
        User user = (User) request.getSession().getAttribute("user");
        List<User> listUsers = userDB.getListUsers();
        request.setAttribute("listUsers", listUsers);
        request.setAttribute("session", user);
        request.setAttribute("roles", Role.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager.jsp");
        dispatcher.forward(request, response);
    }

    private void showUsersOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        User session = (User) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDB.getUserById(id);
        List<Order> listOrders = orderDB.listAllUserOrders(user);
        request.setAttribute("listOrders", listOrders);
        request.setAttribute("user", user);
        request.setAttribute("session", session);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/userOrders.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditFormUsersOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        User user = (User) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDB.getOrder(id);
        List<User> workers = userDB.getUsersByRole(Role.WORKER);
        request.setAttribute("states", State.values());
        request.setAttribute("workers", workers);
        request.setAttribute("order", order);
        request.setAttribute("session", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderForm.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUsersOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String orderMessage = request.getParameter("message");
        String user = request.getParameter("user");
        double price = Double.parseDouble(request.getParameter("price"));
        String worker = request.getParameter("worker");
        State state = State.valueOf(request.getParameter("state"));

        Order upOrder = new Order(id, title, orderMessage, price, worker, state);
        boolean isUpdated = orderDB.updateOrder(upOrder);
        String message;
        if (isUpdated) {
            message = "Customer " + user + " ,order by ID: " + id + " updated!";
        } else {
            message = "Customer " + user + " ,order by ID: " + id + " not updated!";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager");
        dispatcher.forward(request, response);
    }

    private void deleteUsersOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isDeleted = orderDB.deleteOrder(id);
        String message;
        if (isDeleted) {
            message = "Order by ID: " + id + " deleted!";
        } else {
            message = "Order by ID: " + id + " not found!";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager");
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isDeleted = userDB.deleteUser(id);
        String message;
        if (isDeleted) {
            message = "User by ID: " + id + " deleted!";
        } else {
            message = "User by ID: " + id + " not found!";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/manager");
        dispatcher.forward(request, response);
    }
}
