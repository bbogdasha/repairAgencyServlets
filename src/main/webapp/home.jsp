<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Home page</title>
        <link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="container">
        <div class="logo"><a href="/repair-agency/welcome.jsp">Repair Agency</a></div>
            <div class="content">
                <h1>Hi, ${user.name}!</h1><br><br>
                <b>Email: ${user.email}</b><br>
                <b>Role: ${user.role}</b>
                <div class="buttons home-buttons">
                    <a href="/repair-agency/logout" class="button">Logout</a>
                    <a href="/repair-agency/list/new" class="button">New Order</a>
                </div>
                <div class="list">
                    <table border="1" cellpadding="5">
                        <caption><h2>List of Orders</h2></caption>
                        <tr>
                            <th max-width="8%">№</th>
                            <th max-width="13%"><a href="/repair-agency/list?${sortedIdColumn}=id">Order ID</a></th>
                            <th width="25%"><a href="/repair-agency/list?${sortedNameColumn}=title">Title</a></th>
                            <th max-width="13%"><a href="/repair-agency/list?${sortedPriceColumn}=price">Price</a></th>
                            <th max-width="19%">State</th>
                            <th width="25%">Actions</th>
                        </tr>
                        <c:forEach var="order" items="${listOrders}" varStatus="status">
                            <tr>
                                <td><c:out value="${status.count}" /></td>
                                <td><c:out value="${order.id}" /></td>
                                <td><c:out value="${order.title}" /></td>
                                <td><c:out value="${order.price}" /></td>
                                <td><c:out value="${order.state}" /></td>
                                <td align="center">
                                    <a href="/repair-agency/list/view?id=<c:out value='${order.id}' />" class="button action">View</a>
                                    <a href="/repair-agency/list/edit?id=<c:out value='${order.id}' />" class="button action">Edit</a>
                                    <a href="/repair-agency/list/delete?id=<c:out value='${order.id}' />" class="button action">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div><p>${message}</p></div>
                </div>
            </div>
        </div>
    </body>
</html>