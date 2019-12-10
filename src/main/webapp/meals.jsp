<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<c:url value="/css/main.css" />" rel="stylesheet">
<meta charset="utf-8">
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
    <form id="createMeal" method="post">
        <td><input type="hidden" name="id" value=""></td>
        <td><input type="datetime-local" name="dateTime" value=""></td>
        <td><input type="text" name="description" value=""></td>
        <td><input type="number" name="calories" value=""></td>
        <td><input type="hidden" name="method" value="insert">
            <button><input type="submit" value="Добавить"/></button>
        </td>
    </form>

    <c:forEach items="${meals}" var="item">
        <form id="mealsTable" method="post">
            <tr bgcolor="${item.isExcess() ? 'INDIANRED' : 'LIME'}">
                <td><input type="hidden" name="id" value="${item.getId()}"></td>
                <td><input type="datetime-local" name="dateTime" value="${item.getDateTime()}"></td>
                <td><input type="text" name="description" value="${item.getDescription()}"></td>
                <td><input type="number" name="calories" value="${item.getCalories()}"></td>
                <td>
                <td><input type="hidden" name="method" value="change">
                    <button><input type="submit" value="Редактировать"/></button>
                </td>
                <td>
                    <button><a href="meals?action=delete&id=<c:out value="${item.getId()}"/>">Delete</a></button>
                </td>
        </form>

        </tr>

    </c:forEach>

</table>


</body>

</html>


