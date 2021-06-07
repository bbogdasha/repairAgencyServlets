<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>New Order</title>
</head>
<body>
    <center>
        <h1>Create new order</h1>
    </center>
    <div align="center">
        <c:if test="${order != null}">
            <form action="update" method="post">
        </c:if>
        <c:if test="${order == null}">
            <form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${order != null}">
                        Edit Order
                    </c:if>
                    <c:if test="${order == null}">
                        Add New Order
                    </c:if>
                </h2>
            </caption>
            <c:if test="${order != null}">
                <input type="hidden" name="id" value="<c:out value='${order.id}' />" />
            </c:if>
            <tr>
                <th>Title: </th>
                <td>
                    <input type="text" name="title" size="45"
                            value="<c:out value='${order.title}' />" required minlength="4" maxlength="30"/>
                </td>
            </tr>
            <tr>
                <th>Message: </th>
                <td>
                    <input type="text" name="message" size="45"
                            value="<c:out value='${order.message}' />" required minlength="20" maxlength="100"/>
                </td>
            </tr>
            <c:if test="${order != null}">
                <tr>
                    <th>Customer: </th>
                    <td>
                        <input type="text" name="user" size="45"
                                value="<c:out value='${order.user.getName()}' />" readonly/>
                    </td>
                </tr>
                <tr>
                    <th>Price: </th>
                    <td>
                        <input type="text" name="price" size="45"
                                value="<c:out value='${order.price}' />" readonly/>
                    </td>
                </tr>
                <tr>
                    <th>Worker: </th>
                    <td>
                        <input type="text" name="worker" size="45"
                                value="<c:out value='${order.workerName}' />" readonly/>
                    </td>
                </tr>
                <tr>
                    <th>Customer: </th>
                    <td>
                        <input type="text" name="state" size="45"
                                value="<c:out value='${order.state}' />" readonly/>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>