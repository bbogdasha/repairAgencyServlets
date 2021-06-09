package com.bogdan.servlets;

import com.bogdan.dao.ConnectionDB;
import com.bogdan.dao.OrderDB;
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

@WebServlet({"/list", "/list/new", "/list/insert", "/list/delete", "/list/edit", "/list/update"})
public class ControllerOrderServlet extends HttpServlet {

    private OrderDB orderDB;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            orderDB = new OrderDB(ConnectionDB.getConnection());
            switch (action) {
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
        List<Order> listOrders = orderDB.listAllUserOrders(user);
        request.setAttribute("listOrders", listOrders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = orderDB.getOrder(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderForm.jsp");
        request.setAttribute("order", order);
        dispatcher.forward(request, response);
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String title = request.getParameter("title");
        String message = request.getParameter("message");
        User user = (User) request.getSession().getAttribute("user");
        Order newOrder = new Order(title, message, user);
        orderDB.addNewOrder(newOrder);
        response.sendRedirect("/repair-agency/list");
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String message = request.getParameter("message");
        double price = Double.parseDouble(request.getParameter("price"));
        String worker = request.getParameter("worker");
        State state = State.valueOf(request.getParameter("state"));

        Order upOrder = new Order(id, title, message, price, worker, state);
        orderDB.updateOrder(upOrder);
        response.sendRedirect("/repair-agency/list");
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isDeleted = orderDB.deleteOrder(id);
        String message;
        if (isDeleted) {
            message = "Order by id: " + id + " deleted!";
        } else {
            message = "Order by id: " + id + " not found!";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/delete.jsp");
        dispatcher.forward(request, response);
    }
}
