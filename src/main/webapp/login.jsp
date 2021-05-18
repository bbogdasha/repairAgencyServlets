<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login page:</h1>
        <form action="login" method="post">
            <table>
                <tr><td>Email: </td><td><input type="text" name="email"></td></tr>
                <tr><td>Password: </td><td><input type="password" name="password"></td></tr>
                <tr><td>${message}</td></tr>
                <tr><td></td><td><input type="submit" value="Log in"></td></tr>
            </table>
        </form>
    </body>
</html>