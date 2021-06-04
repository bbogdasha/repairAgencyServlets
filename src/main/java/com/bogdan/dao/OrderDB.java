package com.bogdan.dao;

import com.bogdan.model.Order;
import com.bogdan.model.State;
import com.bogdan.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDB {

    private final Connection connection;

    public OrderDB(Connection connection) {
        this.connection = connection;
    }

    public void addNewOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders(title, message, user_id, state_id) VALUES(?,?,?, (SELECT id FROM states " +
                "WHERE state_name=?))";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, order.getTitle());
            preparedStatement.setString(2, order.getMessage());
            preparedStatement.setInt(3, order.getUser().getId());
            preparedStatement.setString(4, State.PROCESSING.toString().toLowerCase());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDB.disconnect();
    }

    public List<Order> listAllUserOrders(User user) throws SQLException {
        List<Order> listOrders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id=?";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");

                    Order order = new Order(id, title);
                    listOrders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDB.disconnect();
        return listOrders;
    }

    public Order getOrder(int id) {
        String query = "SELECT title, message, " +
                "(SELECT username FROM users INNER JOIN orders ON orders.worker_id=users.id where orders.id=?) FROM orders WHERE id=?";
        Order order = null;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String message = resultSet.getString("message");
                    String username = resultSet.getString("username");
                    double price = resultSet.getDouble("price");
                    int workerId = resultSet.getInt("worker_id");
                    int stateId = resultSet.getInt("state_id");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteOrder(int idOrder) throws SQLException {
        String query = "DELETE FROM orders WHERE id=?";
        boolean rowDeleted = false;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idOrder);

            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionDB.disconnect();
        return rowDeleted;
    }
}
