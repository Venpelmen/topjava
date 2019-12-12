<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="createMeal" method="post">
    <c:set var="item" value="${meal}"/>
    <tr><input type="hidden" placeholder="enter input" name="id" value="${item.getId()}"></tr>
    <tr> Дата:<input type="datetime-local" name="dateTime" value="${item.getDateTime()}"></tr>
    <tr>Описание:<input type="text" name="description" value="${item.getDescription()}"></tr>
    <tr>Каллории:<input type="number" name="calories" value="${item.getCalories()}"></tr>
    <tr><input type="hidden" name="method" placeholder="enter input" value="update">
        <button><input type="submit" value="Редактировать"/></button>
    </tr>

</form>

</body>
</html>
