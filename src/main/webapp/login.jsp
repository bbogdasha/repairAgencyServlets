<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Login</title>
        <link href="static/styles/style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="container">
            <div class="logo"><a href="welcome.jsp">Repair Agency</a></div>
            <div class="content less">
                <h1>Login form:</h1>
                <div class="form">
                    <form action="login" method="post">
                        <table>
                            <tr><td>Email: </td><td><input type="text" name="email"></td></tr>
                            <tr><td>Password: </td><td><input type="password" name="password"></td></tr>
                            <tr><td></td><td><input type="submit" value="Log in" class="button"></td></tr>
                        </table>
                    </form>
                    <p>${message}</p>
                </div>
                Do not have an account? <a href="registration.jsp">Registration</a>
            </div>
        </div>
    </body>
</html>