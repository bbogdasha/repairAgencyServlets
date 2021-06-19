<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Order page</title>
        <link href="${pageContext.request.contextPath}/static/styles/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="container">
            <div class="logo">
                <c:if test="${session.getRole() == 'USER'}"><a href="/repair-agency/welcome.jsp">Repair Agency</a></c:if>
                <c:if test="${session.getRole() != 'USER'}"><a href="../../welcome.jsp">Repair Agency</a></c:if>
            </div>
            <div class="content">
                <h1>Order page</h1>
                <div class="buttons home-buttons">
                    <c:if test="${session.getRole() == 'USER'}">
                        <a href="../list" class="button">Back</a>
                    </c:if>
                    <c:if test="${session.getRole() != 'USER'}">
                        <input type="button" value="Back" onClick="history.back()" class="button">
                    </c:if>
                </div>
                <table class="order-table" border="1" cellpadding="5">
                    <tr>
                        <th>ID: </th>
                        <td>
                            <p>${order.id}</p>
                        </td>
                    </tr>
                    <tr>
                        <th>Title: </th>
                        <td>
                            <p>${order.title}</p>
                        </td>
                    </tr>
                    <tr>
                        <th>Message: </th>
                        <td class="order-table-message">
                            <p>${order.message}</p>
                        </td>
                    </tr>
                    <tr>
                        <th>Customer: </th>
                        <td>
                            <p>${order.user.getName()}</p>
                        </td>
                    </tr>
                    <tr>
                        <th>Price: </th>
                        <td>
                            <p>${order.price}</p>
                        </td>
                    </tr>
                    <tr>
                        <th>Worker: </th>
                        <td>
                            <p>${order.workerName}</p>
                        </td>
                    </tr>
                    <tr>
                        <th>Status: </th>
                        <td>
                            <p>${order.state}</p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>