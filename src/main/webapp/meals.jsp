<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <th>Дата</th>
        <th>Описание</th>
        <th>Каллории</th>
    </tr>
    <form id="createMeal" method="post">
        <td>
            <input type="datetime-local" placeholder="Дата и время" name="dateTime" value="">
        </td>
        <td>
            <input type="text" placeholder="Описание" name="description" value="">
        </td>
        <td>
            <input type="number" placeholder="Каллории" name="calories" value="">
        </td>
        <td>
            <input type="hidden" name="method" value="create">
            <button>
                <input type="submit" value="Добавить"/>
            </button>
        </td>
    </form>

    <c:forEach items="${meals}" var="item">
        <form id="mealsTable" method="post">
            <tr bgcolor="${item.isExcess() ? 'INDIANRED' : 'LIME'}">
                <input type="hidden" name="id" value="${item.getId()}">
                <td>${item.getDateTime()}</td>
                <td>${item.getDescription()}</td>
                <td>${item.getCalories()}</td>
                <td>
                    <button><a href="meals?action=forwardToUpdate&id=${item.getId()}">Редактировать</a></button>
                </td>
                <td>
                    <button><a href="meals?action=delete&id=${item.getId()}">Delete</a></button>
                </td>
        </form>

        </tr>

    </c:forEach>

</table>

</body>

</html>