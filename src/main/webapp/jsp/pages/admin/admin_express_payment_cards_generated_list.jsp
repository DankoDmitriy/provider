<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <table class="table table-sm">
        <thead>
        <tr>
            <th scope="col">#</th>
            <td colspan="1"><fmt:message key="label.admin.generate.number"/></td>
            <td colspan="1"><fmt:message key="label.admin.generate.pin"/></td>
            <td colspan="1"><fmt:message key="label.admin.generate.dateExpired"/></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cards}" var="card" varStatus="count">
            <tr>
                <th scope="row">${count.count}</th>
                <td> ${card.key} </td>
                <td> ${card.value} </td>
                <td>
                    <fmt:parseDate value="${expiredDate}" pattern="yyyy-MM-dd"
                                   var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy" value="${parsedDateTime}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>