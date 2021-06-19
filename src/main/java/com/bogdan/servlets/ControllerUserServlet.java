package com.bogdan.servlets;

import com.bogdan.dao.ConnectionFactory;
import com.bogdan.dao.OrderDBImpl;
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

@WebServlet({"/list", "/list/new", "/list/insert", "/list/delete", "/list/edit", "/list/update", "/list/view"})
public class ControllerUserServlet extends HttpServlet {

    private OrderDBImpl orderDBImpl;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            orderDBImpl = new OrderDBImpl(ConnectionFactory.getInstance().getConnection());
            switch (action) {
                case "/list/view":
                    viewOrder(request, response);
                    break;
                case "/list/new":
                    showNewForm(request, response);
                    break;
                case "/list/insert":
                    addOrder(request, response);
                    break;
                case "/list/delete":
                    deleteOrder(request, response);
                    break;
                case "/list/edit":
                    showEditForm(request, response);
                    break;
                case "/list/update":
                    updateOrder(request, response);
                    break;
                default:
                    listOrders(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        List<Order> listOrders = orderDBImpl.listAllUserOrders(user);
        request.setAttribute("listOrders", listOrders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
        dispatcher.forward(request, response);
    }

    private void viewOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        User session = (User) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDBImpl.getOrder(id);
        request.setAttribute("order", order);
        request.setAttribute("session", session);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/order.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDBImpl.getOrder(id);
        request.setAttribute("order", order);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderForm.jsp");
        dispatcher.forward(request, response);
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String title = request.getParameter("title");
        String orderMessage = request.getParameter("message");
        User user = (User) request.getSession().getAttribute("user");
        Order newOrder = new Order(title, orderMessage, user);

        boolean isCreate = orderDBImpl.addNewOrder(newOrder);
        String message;
        if (isCreate) {
            message = "Order successfully created!";
        } else {
            message = "Order was not created!";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
        dispatcher.forward(request, response);
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String orderMessage = request.getParameter("message");
        double price = Double.parseDouble(request.getParameter("price"));
        String worker = request.getParameter("worker");
        State state = State.valueOf(request.getParameter("state"));

        Order upOrder = new Order(id, title, orderMessage, price, worker, state);
        boolean isUpdated = orderDBImpl.updateOrder(upOrder);
        String message;
        if (isUpdated) {
            message = "Order by ID: " + id + " updated!";
        } else {
            message = "Order by ID: " + id + " not updated!";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
        dispatcher.forward(request, response);
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isDeleted = orderDBImpl.deleteOrder(id);
        String message;
        if (isDeleted) {
            message = "Order by ID: " + id + " deleted!";
        } else {
            message = "Order by ID: " + id + " not found!";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list");
        dispatcher.forward(request, response);
    }
}
