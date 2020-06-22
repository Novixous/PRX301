<%-- 
    Document   : index
    Created on : Jun 15, 2020, 2:43:27 PM
    Author     : Novixous
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login with SAX</h1>
        <form action="LoginSAXController" method="POST">
            Username: <input type="text" name="txtUsername"/>
            </br>
            Password: <input type="password" name="txtPassword"/>
            </br>
            <input type="submit" value="Login with SAX"/>
        </form>
         <h1>Login with StAX</h1>
        <form action="LoginStAXController" method="POST">
            Username: <input type="text" name="txtUsername"/>
            </br>
            Password: <input type="password" name="txtPassword"/>
            </br>
            <input type="submit" value="Login with StAX"/>
        </form>

    </body>
</html>
