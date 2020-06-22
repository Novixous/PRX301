<%-- 
    Document   : search
    Created on : Jun 10, 2020, 2:29:43 PM
    Author     : Novixous
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <h1>Search Page</h1>
        <form action="SearchController" method="POST">
            Address: <input type="text" name="txtSearch"/><br/>
            <input type="submit" value="Search"/>
        </form>
        <c:if test="${requestScope.INFO != null}">
            <c:if test="${not empty requestScope.INFO}" var="checkList">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Id</th>
                            <th>Fullname</th>
                            <th>Address</th>
                            <th>Status</th>
                            <th>Delete</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach items="${requestScope.INFO}" var="dto" varStatus="counter">
                        <form action="DeleteController" method="POST">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.id}</td>
                                <td>${dto.firstname} ${dto.middlename} ${dto.lastname}</td>
                                <td>${dto.address}</td>
                                <td>${dto.status}</td>
                                <td>
                                    <input type="hidden" name="txtId" value="${dto.id}"/>
                                    <input type="submit" value="Delete"/>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>

            </table>

        </c:if>
        <c:if test="${!checkList}">
            No record found
        </c:if>
    </c:if>
</body>
</html>
