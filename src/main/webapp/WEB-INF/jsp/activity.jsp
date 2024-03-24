<!DOCTYPE HTML>
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
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/unit.css">
    <script src="../../libs/tinymce/js/tinymce/tinymce.min.js"
            referrerpolicy="origin"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<a href="${pageContext.request.contextPath}/unit">⇦</a>
<h1>${sessionScope.unit.name}</h1>
<h2>Список активностей:</h2>

<table>
    <th>Назва активності</th>
    <th>Опис</th>
    <c:forEach var="activity" items="${requestScope.activities}">
        <tr>
            <td>${activity.name}</td>
            <td>${activity.description}</td>
            <c:if test="${sessionScope.unit.managedByAdmin ? sessionScope.user.role == Role.ADMIN : sessionScope.selectedUserId == sessionScope.user.id}">
                <td class="activityTableDataWithButton">
                    <button type="button"
                            onclick='setUpdateFormValues(${activity.id}, `${activity.name.replaceAll("\'", "&apos;")}`)'>
                        Змінити
                    </button>
                </td>
                <td class="activityTableDataWithButton">
                    <form onSubmit="if(!confirm('Дійсно видалити запис з назвою ${activity.name}?')){return false;}"
                          action="${pageContext.request.contextPath}/activity?id=${activity.id}&action=delete"
                          method="post">
                        <input type="submit" name="delete" value="Видалити"/>
                    </form>
                </td>
                <%--                Не давати Update кнопку, якщо ти не автор і заборонити це на бекенді--%>
            </c:if>
        </tr>
    </c:forEach>
    <c:if test="${sessionScope.unit.managedByAdmin ? sessionScope.user.role == Role.ADMIN : sessionScope.selectedUserId == sessionScope.user.id}">
        <tr>
            <td>
                <button id="openPopup">Додати активність</button>

                <div id="popup" class="popup">
                    <div class="popup-content">
                        <span class="close" id="closePopup">&times;</span>
                        <h2 id="popupHeading">Додавання активності</h2>
                        <form id="popupForm"
                              action="${pageContext.request.contextPath}/activity?unit-id=${param.get("unit-id")}&action=add"
                              method="post">
                            <label for="name">Назва активності:</label>
                            <input type="text" id="name" name="name" required>
                            <br>
                            <textarea id="basic-example" name="description"></textarea>
                            <button type="submit">Submit</button>
                        </form>
                    </div>
                </div>
                <script>
                    document.getElementById("openPopup").addEventListener("click", function () {
                        tinymce.get('basic-example').setContent(``);
                        document.getElementById("popupHeading").innerText = `Додавання активності`;
                        document.getElementById("name").value = ``;
                        document.getElementById("popupForm").action = `${pageContext.request.contextPath}/activity?unit-id=${param.get("unit-id")}&action=add`;
                        document.getElementById("popup").style.display = "block";
                    });

                    document.getElementById("closePopup").addEventListener("click", function () {
                        document.getElementById("popup").style.display = "none";
                    });

                    // Initialize TinyMCE for the textarea with ID 'basic-example'tinymce.get('yourTextareaId').setContent(textFromBackend);
                    tinymce.init({
                        selector: 'textarea#basic-example',
                        images_upload_url: '${pageContext.request.contextPath}/images',
                        images_upload_base_path: '${pageContext.request.contextPath}/images',
                        skin: "oxide-dark",
                        content_css: "dark",
                        plugins: 'image link code',
                        toolbar: 'undo redo | bold italic | image link',
                        // language: 'uk'
                    });

                    function setUpdateFormValues(activityId, activityName) {
                        fetchRecordData(activityId)
                            .then(recordData => {
                                tinymce.get('basic-example').setContent(recordData.value.description);
                                document.getElementById("popupHeading").innerText = `Редагування активності`;
                                document.getElementById("name").value = activityName;
                                document.getElementById("popupForm").action = `${pageContext.request.contextPath}/activity?unit-id=${param.get("unit-id")}&action=update&id=` + activityId;
                                document.getElementById("popup").style.display = "block";
                            })
                            .catch(error => {
                                console.error('Error loading record for edit:', error);
                            });
                    }

                    function fetchRecordData(recordId) {
                        // Perform an AJAX request to fetch the record data
                        return fetch(`${pageContext.request.contextPath}/activity?id=` + recordId, {
                            method: 'GET',
                            headers: {
                                'Accept': 'application/json',
                            },
                        })
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error(`Failed to fetch record data for record ID ` + recordId);
                                }
                                return response.json();
                            });
                    }

                    // document.getElementById("popupForm").addEventListener("submit", function (e) {
                    //     e.preventDefault();
                    //     // Add your form submission logic here
                    //     // You can access form fields using e.target
                    //     // e.target.name.value and e.target.email.value
                    //     // Close the popup when the form is submitted
                    //     document.getElementById("popup").style.display = "none";
                    // });
                </script>
            </td>
        </tr>
    </c:if>
</table>
</body>
</html>