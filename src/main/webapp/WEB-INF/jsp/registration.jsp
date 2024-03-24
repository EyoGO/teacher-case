<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/unit.css">
</head>
<body>
<%--<img src="${pageContext.request.contextPath}/images/users/image.png" alt="User image"/>--%>
<form action="${pageContext.request.contextPath}/registration" method="post" enctype="multipart/form-data">
    <label for="firstName">Ім'я:
        <input type="text" name="firstName" id="firstName" required>
    </label><br>
    <label for="lastName">Прізвище:
        <input type="text" name="lastName" id="lastName" required>
    </label><br>
    <label for="birthday">Дата народження:
        <input type="date" name="birthday" id="birthday" required>
    </label><br>
    <label for="imageId">Зображення:
        <input type="file" name="image" id="imageId">
    </label><br>
    <label for="emailId">Email:
        <input type="text" name="email" id="emailId" required>
    </label><br>
    <label for="passwordId">Пароль:
        <input type="password" name="password" id="passwordId" required>
    </label><br>
    <select name="role" id="role" required>
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select>
    <br>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" value="${gender}" required> ${gender}
        <br>
    </c:forEach>
    <button type="submit">Підтвердити</button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
                <br>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>
