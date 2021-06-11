<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Error</title>
        <link href="static/styles/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="container">
            <div class="logo"><a href="welcome.jsp">Repair Agency</a></div>
            <div class="content">
                <h1>Error</h1><br>
                <p><%=exception.getMessage() %></p>
            </div>
        </div>
    </body>
</html>