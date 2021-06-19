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
import java.util.Locale;

public class OrderDBImpl implements OrderDB {

    private final Connection connection;

    public OrderDBImpl(Connection connection) {
        this.connection = connection;
    }

    public boolean addNewOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders(title, message, user_id, state_id) VALUES(?,?,?, (SELECT id FROM states " +
                "WHERE state_name=?))";

        boolean addRow = false;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, order.getTitle());
            preparedStatement.setString(2, order.getMessage());
            preparedStatement.setInt(3, order.getUser().getId());
            preparedStatement.setString(4, State.PROCESSING.toString().toLowerCase());

            addRow = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.disconnect();
        return addRow;
    }

    public List<Order> listAllUserOrders(User user) throws SQLException {
        String query = "SELECT * FROM orders WHERE user_id=? ORDER BY id";

        List<Order> listOrders = new ArrayList<>();

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    double price = resultSet.getDouble("price");
                    int state_id = resultSet.getInt("state_id");
                    State state = getState(state_id);

                    Order order = new Order(id, title, price, state);
                    listOrders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.disconnect();
        return listOrders;
    }

    public Order getOrder(int orderId) throws SQLException {
        String query = "SELECT * FROM orders WHERE id=?";

        Order order = null;
        UserDBImpl userDBImpl = new UserDBImpl(this.connection);

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String message = resultSet.getString("message");
                    int userId = resultSet.getInt("user_id");
                    double price = resultSet.getDouble("price");
                    int workerId = resultSet.getInt("worker_id");
                    int state_id = resultSet.getInt("state_id");
                    State state = getState(state_id);

                    User user = userDBImpl.getUserById(userId);
                    User worker = userDBImpl.getUserById(workerId);

                    if (worker == null) {
                        order = new Order(orderId, title, message, user, price, "-", state);
                    } else {
                        order = new Order(orderId, title, message, user, price, worker.getName(), state);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public boolean updateOrder(Order order) throws SQLException {
        String query = "UPDATE orders SET title=?, message=?, price=?, " +
                "worker_id=(SELECT id FROM users WHERE username=?), " +
                "state_id=(SELECT id FROM states WHERE state_name=?) WHERE id=?";

        boolean isUpdated = false;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, order.getTitle());
            preparedStatement.setString(2, order.getMessage());
            preparedStatement.setDouble(3, order.getPrice());
            preparedStatement.setString(4, order.getWorkerName());
            preparedStatement.setString(5, order.getState().toString().toLowerCase());
            preparedStatement.setInt(6, order.getId());

            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.disconnect();
        return isUpdated;
    }

    public State getState(int stateId) throws SQLException {
        String query = "SELECT state_name FROM states WHERE id=?";

        State state_name = null;

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stateId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("state_name");
                    state_name = State.valueOf(name.toUpperCase(Locale.ROOT));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state_name;
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
        ConnectionFactory.disconnect();
        return rowDeleted;
    }
}
