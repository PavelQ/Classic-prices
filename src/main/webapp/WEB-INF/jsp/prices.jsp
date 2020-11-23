<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.stream.Collectors" %>
<html>
<head>
    <meta charset="WIN-1251"/>
    <title>Data prices</title>
    <link href="css/jquerysctipttop.css" rel="stylesheet" type="text/css"/>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/script.js"></script>
</head>
<body>
<h1>${message}</h1>
<input type="hidden" value="${sources}" class="curSources">
<button type="button" class="updateDataButton" onclick="$.post('/updateData',{sources : $('.curSources').val()}); location.reload()">
    <span class="glyphicon glyphicon-refresh"></span>
</button>
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
            <c:forEach items="${factions}" var="faction">
            <c:if test="${faction == null}">
            <<
            <continue>>>
                </c:if>
                <option value="${faction}">${faction}</option>
                </c:forEach>
        </select></th>
        <th><input class="js-filter form-control" type="text" value=""></th>
        <th><select class="js-filter  form-control">
            <option value=""></option>
            <c:forEach items="${sourcesList}" var="source">
                <option value="${source}">${source}</option>
            </c:forEach>
        </select></th>
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

<script src="js/dynamitable.jquery.min.js"></script>
</body>

</html>