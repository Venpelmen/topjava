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
            <td> <input type = "number" name = "id" value="${item.getId()}"></td>
            <td> <input type = "datetime-local" name = "dateTime" value="${item.getDateTime()}"></td>
            <td>    <input type = "text" name ="description" value="${item.getDescription()}">  </td>
            <td>    <input type = "number" name ="calories" value="${item.getCalories()}">  </td>
            <%--<td><a href="meals?action=edit&Id=<c:out value="${item.getId()}"/>&dateTime=<c:out value="${item.getDateTime()}"/>&description=<c:out value="${item.getDescription()}"/>&calories=<c:out value="${item.getCalories()}"/>">Update</a></td>--%>
  <%--          <td><a href="meals?action=edit&item=<c:out value="${item.toString()}"/>">Update</a></td>--%>
            <td>  <button><input type="submit"/> Редактировать</button></td>
            <td><a href="meals?action=edit&id=<c:out value="${item.getId()}"/>">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${item.getId()}"/>">Delete</a></td>

        <%--            <td><c:set var = "id" scope = "session" value = "${item.getId()}"/>
            <td> <c:set var  = "dateTime" value="${item.getDateTime()}"/></td>
            <td>   <c:set var  ="description" value="${item.getDescription()}"/>  </td>
            <td>     <c:set var  ="calories" value="${item.getCalories()}"/>  </td>--%>
    </form>

        </tr>

    </c:forEach>


</table>


</body>

</html>


