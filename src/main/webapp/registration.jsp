<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <h1>Registration page:</h1>
        <form action="register" method="post">
            <table>
                <tr><td>User Name: </td><td><input type="text" name="uname"></td></tr>
                <tr><td>Email: </td><td><input type="text" name="email"></td></tr>
                <tr><td>Password: </td><td><input type="password" name="password"></td></tr>
                <tr><td></td><td><input type="submit" value="register"></td></tr>
            </table>
        </form>
    </body>
</html>