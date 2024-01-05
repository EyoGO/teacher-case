<%@ attribute name="units" type="java.util.List" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!empty units}">
    <ul>
        <c:forEach var="unit" items="${units}">
            <li ${(unit.descendants != null && unit.descendants.size() > 0) ? "class=\"collapsible\"" : ""}><a href="${pageContext.request.contextPath}/activity?unit-id=${unit.id}"><c:out value="${unit.name}"/></a></li>
            <myTags:units units="${unit.descendants}"/>
        </c:forEach>
    </ul>
</c:if>