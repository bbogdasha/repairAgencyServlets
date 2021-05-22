<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Home page</title>
    </head>
    <body>
        <h1>Hi, ${user.name}!</h1>
        <b>Email: ${user.email}, password: ${user.password}, role: ${user.role}</b>
        <br>
        <a href="/repair-agency/logout">Logout</a>
        <br>
        <a href="/repair-agency/new_order">New Order</a>
        <br><br><br>
        <div align="center">
                <table border="1" cellpadding="5">
                    <caption><h2>List of Orders</h2></caption>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Message</th>
                        <th>Price</th>
                        <th>Worker</th>
                        <th>State</th>
                        <th>Actions</th>
                    </tr>
                    <c:forEach var="order" items="${listOrders}">
                        <tr>
                            <td><c:out value="${order.id}" /></td>
                            <td><c:out value="${order.title}" /></td>
                            <td><c:out value="${order.message}" /></td>
                            <td><c:out value="${order.price}" /></td>
                            <td><c:out value="${order.worker}" /></td>
                            <td><c:out value="${order.state}" /></td>
                            <td>
                                <a href="/edit?id=<c:out value='${order.id}' />">Edit</a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="/delete?id=<c:out value='${order.id}' />">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
    </body>
</html>