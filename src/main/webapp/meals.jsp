<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>



<table class="tg" border="1">
    <tr>
        <th class="tg-orf0">ID</th>
        <th class="tg-og4q">Дата</th>
        <th class="tg-0lax">Описание</th>
        <th class="tg-0lax">Каллории</th>
        <th class="tg-0lax">Превышение</th>
    </tr>
    <%
        List<MealTo> posts=(List<MealTo>) request.getAttribute("posts");
        for (MealTo post: posts) {
    %>


    <tr bgcolor=  "{<%=post.isExcess()%> ? 'red' : 'green'}">
        <td><%=post.getId()%></td>
        <td><%=post.getDateTime()%></td>
        <td><%=post.getDescription()%></td>
        <td><%=post.getCalories()%></td>
        <td><%=post.isExcess()%></td>
    </tr>
    <%}%>
</table>




</body>

</html>

<style type="text/css">
    .tg  {border-collapse:collapse;border-spacing:0;}
    .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}
    .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}
    .tg .tg-orf0{font-family:"Arial Black", Gadget, sans-serif !important;;text-align:left;vertical-align:top}
    .tg .tg-og4q{text-align:left;vertical-align:top}
    .tg .tg-0lax{text-align:left;vertical-align:top}
</style>
