<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="WIN-1251"/>
    <title>Feedbacks</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/script.js"></script>
</head>
<body>

    <table class="table table-bordered">
        <tbody>
        <c:forEach items="${feedbacks}" var="feedback">
            <tr>
                <td>${feedback.id}</td>
                <td>${feedback.name}</td>
                <td>${feedback.email}</td>
                <td>${feedback.message}</td>
                <td>${feedback.date}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>