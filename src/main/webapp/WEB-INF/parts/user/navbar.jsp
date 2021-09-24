<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="main-breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=HOME">
                <fmt:message key="label.user.navbar.home"/>
            </a></li>

        <%--                Menu for actions--%>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=PERSONAL_FINANCE_OPERATIONS&user_id=${sessionScope.user.userId}&nextPage=0&previewPage=-1">
                <fmt:message key="label.user.navbar.finance"/>
            </a></li>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=USER_TARIFF_LIST">
                <fmt:message key="label.user.navbar.tariffs"/>
            </a></li>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=USER_ACTION_LIST&user_id=${sessionScope.user.userId}&nextPage=0&previewPage=-1">
                <fmt:message key="label.user.navbar.actions"/>
            </a></li>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=pay">
                <fmt:message key="label.user.navbar.pay"/>
            </a></li>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=logout">
                <fmt:message key="label.user.navbar.logout"/>
            </a></li>
    </ol>
</nav>
<!-- /Breadcrumb -->