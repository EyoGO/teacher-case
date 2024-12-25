<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.eyogo.http.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div>
    <div>
        <img src="${pageContext.request.contextPath}/photo.jpg" class="headerImg">
    </div>
    <c:if test="${not empty sessionScope.user}"> <%--TODO maybe add everywhere till normal solution added --%>
        <ul class="menu">
            <c:if test="${sessionScope.user.role == (Role.ADMIN)}">
                <li>
                    <form action="${pageContext.request.contextPath}/registration" method="get">
                        <button type="submit">Створити нового користувача</button>
                    </form>
                </li>
            </c:if>

            <li>
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit">Вийти</button>
                </form>
            </li>
        </ul>
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
