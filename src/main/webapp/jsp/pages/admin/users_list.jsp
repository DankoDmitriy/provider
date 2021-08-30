<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="users" scope="request" type="java.util.List"/>
<%@include file="../../../WEB-INF/parts/admin/header.jsp"%>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">FN+LN+P</th>
            <th scope="col">Contract Number</th>
            <th scope="col">Contract Date</th>
            <th scope="col">E-mail</th>
            <th scope="col">Role</th>
            <th scope="col">Редактировать</th>
        </tr>
        </thead>
        <tbody>

<c:forEach items="${users}" var="user">
        <tr>
            <th scope="row">${user.userId}</th>
            <td>${user.firstName} ${user.lastName} ${user.patronymic}</td>
            <td>${user.contractNumber}</td>
            <td>${user.contractDate}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td><a href="/car/${user.userId}">edit</a></td>
        </tr>
</c:forEach>
        </tbody>
    </table>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp"%>

