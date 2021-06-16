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
                </div>
                <div class="list">
                    <table border="1" cellpadding="6">
                        <caption><h2>List of Users</h2></caption>
                        <tr>
                            <th width="8%">№</th>
                            <th width="12%">User ID</th>
                            <th width="18%">Name</th>
                            <th>Email</th>
                            <th width="18%">Role</th>
                            <th width="18%">Action</th>
                        </tr>
                        <c:forEach var="user" items="${listUsers}" varStatus="status">
                            <tr>
                                <td><c:out value="${status.count}" /></td>
                                <td><c:out value="${user.id}" /></td>
                                <td><c:out value="${user.name}" /></td>
                                <td><c:out value="${user.email}" /></td>
                                <td><c:out value="${user.role}" /></td>
                                <td align="center">
                                    <a href="/repair-agency/manager/user?id=<c:out value='${user.id}' />" class="button action">Show orders</a>
                                    <c:if test="${session.getRole() == 'MANAGER'}">
                                        <br><br><a href="/repair-agency/manager/delete?id=<c:out value='${user.id}' />" class="button action">Delete user</a>
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