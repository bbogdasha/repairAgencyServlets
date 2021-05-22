<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Manager page</title>
    </head>
    <body>
        <h1>Hi, ${user.name}!</h1>
        <b>Email: ${user.email}, password: ${user.password}, role: ${user.role}</b>
        <br>
        <a href="/repair-agency/logout">Logout</a>
        <br><br>
        <p>Yes, this is manager page!</p>
    </body>
</html>