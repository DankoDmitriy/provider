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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<div class="container mt-5">
    <center>
        <img src="${pageContext.request.contextPath}/image/not_fount_page.png" alt="Admin"
             class="rounded-circle" width="350">
        <h1>Page not found.</h1>
        <c:set var="admin_role" value="ADMIN" scope="page"/>
        <c:set var="user_role" value="USER" scope="page"/>
        <c:set var="guest_role" value="GUEST" scope="page"/>

        <c:if test="${sessionScope.user.role eq admin_role or sessionScope.user.role eq user_role}">
            To return to the HOME page click <a
                href="${pageContext.request.contextPath}/controller?command=HOME">here</a>
        </c:if>
        <c:if test="${sessionScope.user.role eq guest_role}">
            To return to the login page click
            <a href="../../../index.jsp">here</a>
        </c:if>
    </center>
</div>
</body>
</html>
