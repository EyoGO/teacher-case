<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.eyogo.http.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div>
    <div>
        <img src="../../photo.jpg">
    </div>
    <c:if test="${not empty sessionScope.user}">
        <c:if test="${sessionScope.user.role == (Role.ADMIN)}">
            <a href="${pageContext.request.contextPath}/registration">
                <button type="button"><%--<fmt:message key="page.login.register.button"/>--%>Створити нового користувача</button>
            </a>
        </c:if>
        <div id="logout">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <button type="submit">Вийти</button>
            </form>
        </div>
    </c:if>
<%--    <div id="locale">--%>
<%--        <form action="${pageContext.request.contextPath}/locale" method="post">--%>
<%--            <button type="submit" name="lang" value="uk_UA">UK</button>--%>
<%--            <button type="submit" name="lang" value="en_US">EN</button>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--    <fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null ? param.lang : 'en_US')}"/>--%>
<%--    <fmt:setBundle basename="translations"/>--%>
</div>
