package com.bogdan.dao;

import com.bogdan.model.Order;
import com.bogdan.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderDB {

    private final Connection connection;

    public OrderDB(Connection connection) {
        this.connection = connection;
    }

    public boolean addOrder(Order order) throws SQLException {
        boolean isAdd = false;
        String query = "INSERT INTO orders(title, message, user_id, state_id) VALUES(?,?,?, (SELECT id FROM states " +
                "WHERE state_name=?))";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, order.getTitle());
            preparedStatement.setString(2, order.getMessage());
            preparedStatement.setInt(3, order.getUser().getId());
            preparedStatement.setString(4, order.getState().name().toLowerCase(Locale.ROOT));

            preparedStatement.executeUpdate();
            isAdd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
        return isAdd;
    }

    public List<Order> listAllUserOrders() throws SQLException {
        List<Order> listOrders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String message = resultSet.getString("message");
            double price = resultSet.getDouble("price");
//            User user = resultSet.getString("user_id");
//            double price = resultSet.getFloat("price");

            Order order = new Order(id, title, message, price);
            listOrders.add(order);
        }
        resultSet.close();
        statement.close();

        return listOrders;
    }
}
