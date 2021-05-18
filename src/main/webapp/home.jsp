<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home page</title>
    </head>
    <body>
        <h1>Hi, ${user.name}!</h1>
        <b>Email: ${user.email}, password: ${user.password}</b>
        <br>
        <a href="/repair-agency/logout">Logout</a>
    </body>
</html>