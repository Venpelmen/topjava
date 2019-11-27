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
        <th>Превышение</th>
    </tr>

    <c:forEach items="${meals}" var="item">

        <tr bgcolor="${item.isExcess() ? 'INDIANRED' : 'LIME'}">
            <td>${item.getId()}</td>
            <td><input type="datetime-local" value="${item.getDateTime()}"/></td>
            <td>${item.getDescription()}</td>
            <td>${item.getCalories()}</td>
        </tr>
    </c:forEach>

</table>


</body>

</html>


