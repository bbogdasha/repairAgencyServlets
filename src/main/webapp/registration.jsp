<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Registration</title>
        <link href="static/styles/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="container">
        <div class="logo"><a href="welcome.jsp">Repair Agency</a></div>
            <div class="content less">
                <h1>Registration form:</h1>
                <div class="form">
                    <form action="register" method="post">
                        <table>
                            <tr><td>User Name: </td><td><input type="text" name="uname"></td></tr>
                            <tr><td>Email: </td><td><input type="text" name="email"></td></tr>
                            <tr><td>Password: </td><td><input type="password" name="password"></td></tr>
                            <tr><td></td><td><input type="submit" value="Registration" class="button"></td></tr>
                        </table>
                    </form>
                </div>
                Already have an account? <a href="login.jsp">Login</a>
            </div>
        </div>
    </body>
</html>