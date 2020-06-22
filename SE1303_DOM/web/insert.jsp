<%-- 
    Document   : inser
    Created on : Jun 10, 2020, 3:02:06 PM
    Author     : Novixous
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Page</title>
    </head>
    <body>
        <h1>Insert Page!</h1>
        <form action="RegisterController" method="POST">
            Id: <input type="text" name="txtId"/><br/>
            Class: <input type="text" name="txtClass"/><br/>
            Lastname: <input type="text" name="txtLastname"/><br/>
            Middlename: <input type="text" name="txtMiddlename:"/><br/>
            Firstname: <input type="text" name="txtFirstname"/><br/>
            Password: <input type="password" name="txtPassword"/><br/>
            Address: <input type="text" name="txtAddress"/><br/>
            Sex: <select name="cboSex">
                <option value="1">Male</option>
                <option value="0">Female</option>
            </select><br/>
            <input type="submit" value="Register"/>
        </form>
    </body>
</html>
