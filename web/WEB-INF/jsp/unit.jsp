<%--
  Created by IntelliJ IDEA.
  User: Yurii Hentash
  Date: 30.09.2023
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ page import="com.eyogo.http.entity.Role" %>
<html>
<head>
    <title>Units</title>
    <link rel="stylesheet" type="text/css" href="../../styles/unit.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const collapsibles = document.querySelectorAll('.collapsible');

            collapsibles.forEach(collapsible => {
                collapsible.addEventListener('click', function() {
                    this.classList.toggle('active');
                    const content = this.nextElementSibling;
                    if (content.style.display === 'block') {
                        content.style.display = 'none';
                    } else {
                        content.style.display = 'block';
                    }
                });
            });
        });
    </script>
</head>
<body>
    <%@include file="header.jsp"%>
    <form method="post">
        <c:if test="${sessionScope.user.role == (Role.ADMIN)}">
            <label for="userSelector">Виберіть користувача:</label>
            <select name="userToWatch" id="userSelector">
                <c:forEach var="user" items="${requestScope.get(\"usersSelection\")}">
                    <option value="${user.id}" ${user.id.equals(sessionScope.selectedUserId) ? "selected=\"selected\"" : ""}>${user.firstName} ${user.lastName}</option>
                </c:forEach>
            </select>
        </c:if>
        <fieldset>
            <legend>Використовувати каскадний пошук:</legend>
            <input type="radio" id="yes" name="useCascade" value="true" ${sessionScope.useCascade ? "checked" : ""}>
            <label for="yes">Так</label>
            <input type="radio" id="no" name="useCascade" value="false" ${sessionScope.useCascade ? "" : "checked"}>
            <label for="no">Ні</label><br>
        </fieldset>
        <input type="submit" value="Підтвердити" />
    </form>

<h1>Розділи:</h1>
<myTags:units units="${requestScope.units}"/>
</body>
</html>