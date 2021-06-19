package com.bogdan.dao;

import com.bogdan.model.Order;
import com.bogdan.model.State;
import com.bogdan.model.User;

import java.sql.SQLException;
import java.util.List;

public interface OrderDB {

    boolean addNewOrder(Order order) throws SQLException;

    List<Order> listAllUserOrders(User user) throws SQLException;

    Order getOrder(int orderId) throws SQLException;

    boolean updateOrder(Order order) throws SQLException;

    State getState(int stateId) throws SQLException;

    boolean deleteOrder(int idOrder) throws SQLException;
}
