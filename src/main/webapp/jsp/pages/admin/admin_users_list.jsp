<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<jsp:useBean id="users" scope="request" type="java.util.List"/>
<div class="container mt-5">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">-</th>
            <th scope="col">Ф.И.О</th>
            <th scope="col">Контракт</th>
            <th scope="col">Баланс</th>
            <th scope="col">E-mail</th>
            <th scope="col">Role</th>
            <th scope="col">Редактировать</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user" varStatus="count">
            <tr>
                <th scope="row">${count.count}</th>
                <td>
                        ${user.firstName}
                        ${user.lastName}
                    </br>
                        ${user.patronymic}
                </td>
                <td>
                        ${user.contractNumber}
                    </br>
                    <fmt:parseDate value="${user.contractDate}" pattern="yyyy-MM-dd"
                                   var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy" value="${parsedDateTime}"/>
                </td>
                <td>${user.balance}</td>
                <td>${user.email}</td>
                <td>${user.status}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=USER_EDIT&user_id=${user.userId}">
                        Редактировать
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>

