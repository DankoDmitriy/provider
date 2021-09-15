<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<jsp:useBean id="users" scope="request" type="java.util.List"/>
<div class="container mt-5">
    <c:if test="${resultWork == true}">
        <p><fmt:message key="label.admin.usersList.message.changeUserDataTrue"/></p>
    </c:if>
    <c:if test="${resultWork == false}">
        <p><fmt:message key="label.admin.usersList.message.changeUserDataFalse"/></p>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">-</th>
            <th scope="col"><fmt:message key="label.admin.usersList.firstAndLastName"/></th>
            <th scope="col"><fmt:message key="label.admin.usersList.contract"/></th>
            <th scope="col"><fmt:message key="label.admin.usersList.balance"/></th></th>
            <th scope="col"><fmt:message key="label.admin.usersList.email"/></th></th>
            <th scope="col"><fmt:message key="label.admin.usersList.status"/></th></th>
            <th scope="col"><fmt:message key="label.admin.usersList.edit"/></th></th>
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
                        <fmt:message key="label.admin.usersList.edit"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>

