<%-- 
    Document   : index
    Created on : Jun 19, 2020, 2:39:17 PM
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
        <h1>Welcome to E-hell bank!</h1>
        <form action="processing.jsp" method="POST">
            Username: <input type="text" name="txtUsername"/><br/>
            Pin: <input type="password" name="txtPin"/><br/>
            <input type="submit" value="Login"
        </form>
    </body>
    <h1> Search with JSTl-XML</h1>
    <form action="SearchController" method="POST">
        Fullname: <input type="text" name="txtSearch"/><br/>
        <input type="submit" value="Search"/>
    </form>
</html>
