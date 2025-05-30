<%-- 
    Document   : Home
    Created on : Feb 22, 2025, 11:44:03 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            *
            {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
                font-family: sans-serif;
            }

            body
            {
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100vh;
                background: #C2E8F5;
            }
            /*tăng kích thước tổng thể*/
            .container
            {
                display: flex;
                width: 1000px;
                height: 400px;
                background: white;
                border-radius: 12px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
                overflow: hidden;
            }

            .left
            {
                flex: 1;
                padding: 40px;
            }

            .right
            {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                background: #eef2ff;
                padding: 20px;
            }

            .right img
            {
                width: 110%;
                height: 112%;
                object-fit: cover;
            }

            h2
            {
                text-align: center;
                font-weight: 600;
                color: black;
                margin-bottom: 20px;
            }

            input
            {
                width: 100%;
                padding: 12px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 16px;
                transition: 0.3s;
            }

            input:focus
            {
                border-color: #3B82F6;
                outline: none;
                box-shadow: 0 0 5px rgba(59, 130, 246, 0.5);
            }

            .login
            {
                width: 100%;
                padding: 12px;
                background: #3B82F6;
                color: white;
                border: none;
                border-radius: 6px;
                font-size: 16px;
                font-weight: 500;
                transition: 0.3s;
            }

            .forgot_password, .signup
            {
                display: block;
                text-align: center;
                margin-top: 12px;
                color: #3B82F6;
                font-size: 14px;
                text-decoration: none;
                transition: 0.3s;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="left">
                <h2>Login</h2>
                <form action="Login" method="POST">
                    <input type="text" name="username" placeholder="Uername" value="${param.username}" required/>
                    <input type="password" name="password" placeholder="Password" required/>
                    <input type="submit" name="Login" class="login"> 
                    <c:if test="${not empty error}">
                        <p style="color: red; align-content: center;">${error}</p>
                    </c:if>                 
                </form>
            </div>
            <div class="right">
                <img src="img/login.jpg" >
            </div>
        </div>
    </body>
</html>
