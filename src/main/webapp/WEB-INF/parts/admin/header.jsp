<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>DankoBill</title>
<%--    <link rel="stylesheet" href="/static/style.css">--%>
    <!-- Bootstrap CSS -->
<%--    <#--Указание браузеру, по учету плотноси пикселей на экране устройства.  Делать крупнее шрифты и.т.д. в зависисмости от устройства-->--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<%--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <!--  End Bootstrap CSS -->
</head>
<body>