<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>${user.name} page</title>
        <link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="container">
        <div class="logo"><a href="/repair-agency/welcome.jsp">Repair Agency</a></div>
            <div class="content">
                <h1>Page of orders: ${user.role}, ${user.name}!</h1><br>
                <b>Email: ${user.email}</b>
                <div class="buttons home-buttons">
                    <a href="../manager" class="button">Back</a>
                </div>
                <div class="list">
                    <table border="1" cellpadding="5">
                        <caption><h2>List of Orders</h2></caption>
                        <tr>
                            <th width="8%">№</th>
                            <th width="15%">Order ID</th>
                            <th>Title</th>
                            <th width="20%">Actions</th>
                        </tr>
                        <c:forEach var="order" items="${listOrders}" varStatus="status">
                            <tr>
                                <td><c:out value="${status.count}" /></td>
                                <td><c:out value="${order.id}" /></td>
                                <td><c:out value="${order.title}" /></td>
                                <td align="center">
                                    <a href="/repair-agency/manager/user/edit?id=<c:out value='${order.id}' />" class="button action">Edit</a>
                                    <c:if test="${session.getRole() == 'MANAGER'}">
                                        <a href="/repair-agency/manager/user/delete?id=<c:out value='${order.id}' />" class="button action">Delete</a>
                                    </c:if>
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