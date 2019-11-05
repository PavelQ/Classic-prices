<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="WIN-1251"/>
    <title>Data prices</title>
    <link href="css/jquerysctipttop.css" rel="stylesheet" type="text/css"/>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>${message}</h1>
<table class="js-dynamitable     table table-bordered">
    <thead>
    <TR>
        <th>Server
            <span class="js-sorter-asc     glyphicon glyphicon-chevron-down pull-right"></span>
            <span class="js-sorter-desc     glyphicon glyphicon-chevron-up pull-right"></span>
        </th>
        <th>faction
            <span class="js-sorter-asc     glyphicon glyphicon-chevron-down pull-right"></span>
            <span class="js-sorter-desc     glyphicon glyphicon-chevron-up pull-right"></span>
        </th>
        <th>price
            <span class="js-sorter-asc     glyphicon glyphicon-chevron-down pull-right"></span>
            <span class="js-sorter-desc     glyphicon glyphicon-chevron-up pull-right"></span>
        </th>
        <th>source
            <span class="js-sorter-asc     glyphicon glyphicon-chevron-down pull-right"></span>
            <span class="js-sorter-desc     glyphicon glyphicon-chevron-up pull-right"></span>
        </th>
        <th>population
            <span class="js-sorter-asc     glyphicon glyphicon-chevron-down pull-right"></span>
            <span class="js-sorter-desc     glyphicon glyphicon-chevron-up pull-right"></span>
        </th>
        <th>type
            <span class="js-sorter-asc     glyphicon glyphicon-chevron-down pull-right"></span>
            <span class="js-sorter-desc     glyphicon glyphicon-chevron-up pull-right"></span>
        </th>
        <th>location
            <span class="js-sorter-asc     glyphicon glyphicon-chevron-down pull-right"></span>
            <span class="js-sorter-desc     glyphicon glyphicon-chevron-up pull-right"></span>
        </th>
    </TR>
    <TR>
        <th><input class="js-filter form-control" type="text" value=""></th>
        <th><select class="js-filter  form-control">
            <option value=""></option>
            <option value="HORDE">HORDE</option>
            <option value="ALIANCE">ALIANCE</option>
        </select></th>
        <th><input class="js-filter form-control" type="text" value=""></th>
        <th><input class="js-filter form-control" type="text" value=""></th>
        <th><input class="js-filter form-control" type="text" value=""></th>
        <th><input class="js-filter form-control" type="text" value=""></th>
        <th><input class="js-filter form-control" type="text" value=""></th>
    </TR>
    </thead>
    <tbody>
        <c:forEach items="${prices}" var="price">
            <tr>
                <td>${price.server.name}</td>
                <td>${price.server.faction}</td>
                <td>${price.price}</td>
                <td>${price.source}</td>
                <td>${price.serverStatus.population}</td>
                <td>${price.serverStatus.serverType}</td>
                <td>${price.serverStatus.serverLocation}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/dynamitable.jquery.min.js"></script>
<script src="js/script.js"></script>
</body>

</html>