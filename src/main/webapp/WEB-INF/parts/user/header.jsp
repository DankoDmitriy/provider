<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>DankoBill</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
<%--    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>--%>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="${pageContext.request.contextPath}/css/user/bootstrap.min.css" rel="stylesheet">
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.bundle.min.js"></script>--%>
</head>
<body>
<div class="container">
    <div class="main-body">
        <div align="right">
            <a href="${pageContext.request.contextPath}/controller?command=LOCAL&newLocal=en_EN">En</a>
            <a href="${pageContext.request.contextPath}/controller?command=LOCAL&newLocal=ru_RU">Rus</a>
        </div>