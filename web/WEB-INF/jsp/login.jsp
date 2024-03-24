<%--
  Created by IntelliJ IDEA.
  User: Yurii Hentash
  Date: 01.10.2023
  Time: 1:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>--%>
<html>
<head>
  <title>Login</title>
  <link rel="stylesheet" type="text/css" href="../../styles/unit.css">
</head>
<body>
<%@include file="header.jsp"%>
<form action="${pageContext.request.contextPath}/login" method="post">
  <label for="email"><%--<fmt:message key="page.login.email"/>--%>Email:
    <input type="text" name="email" value="${param.email}" id="email">
  </label><br>
  <label for="password"><%--<fmt:message key="page.login.password"/>--%>Пароль:
    <input type="password" name="password" id="password">
  </label><br>
  <button type="submit"><%--<fmt:message key="page.login.submit.button"/>--%>Увійти</button>
  <div>
    <c:if test="${param.error != null}">
      <div style="color: red">
        <span><%--<fmt:message key="page.login.error"/>--%>Сталась помилка</span>
      </div>
    </c:if>
  </div>
</form>
</body>
</html>
