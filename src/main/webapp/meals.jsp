<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<c:url value="/css/main.css" />" rel="stylesheet">
<html>
<head>
    <title>Title</title>
</head>
<body>


<table class="tg">
    <tr>
        <th>ID</th>
        <th>Дата</th>
        <th>Описание</th>
        <th>Каллории</th>
    </tr>

    <c:forEach items="${meals}" var="item">
    <form id="createMarkerForm" method="post">
        <tr bgcolor="${item.isExcess() ? 'INDIANRED' : 'LIME'}">
            <td> <input type = "text" name = "id" value="${item.getId()}"></td>
            <td> <input type = "datetime-local" name = "dateTime" value="${item.getDateTime()}"></td>
            <td>    <input type = "text" name ="description" value="${item.getDescription()}">  </td>
            <td>    <input type = "text" name ="calories" value="${item.getCalories()}">  </td>
            <td> <p><input type="submit"></p> </td>
    </form>

        </tr>
    </c:forEach>

</table>


</body>

</html>


