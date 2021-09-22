<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light" xmlns="http://www.w3.org/1999/html">

    <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=HOME">DankoBill</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <fmt:message key="label.admin.navBar.users"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=USERS_LIST&userRole=USER&nextPage=0&previewPage=-1">
                        <fmt:message key="label.admin.navBar.user.all"/></a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=USER_ADD">
                        <fmt:message key="label.admin.navBar.user.add"/>
                    </a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=USER_SEARCH">
                        <fmt:message key="label.admin.navBar.user.search"/>
                    </a>
                </div>
            </li>


            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <fmt:message key="label.admin.navBar.tariffs"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=TARIFF_LIST&nextPage=0&previewPage=-1">
                        <fmt:message key="label.admin.navBar.tariff.all"/></a>

                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=TARIFF_ADD">
                        <fmt:message key="label.admin.navBar.tariff.add"/></a>
                </div>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <fmt:message key="label.admin.navBar.paymentCards"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=CARD_SEARCH">
                        <fmt:message key="label.admin.navBar.paymentCard.search"/></a>

                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=CARD_ADD">
                        <fmt:message key="label.admin.navBar.paymentCard.add"/></a>

                </div>
            </li>


            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <fmt:message key="label.admin.navBar.employees"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=EMPLOYEE_LIST&userRole=ADMIN&nextPage=0&previewPage=-1">
                        <fmt:message key="label.admin.navBar.employee.all"/></a>
                </div>
            </li>


            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=STATISTICS">
                    <fmt:message key="label.admin.navBar.statistics"/>
                </a>
            </li>
        </ul>

        <div class="navbar-text mr-3">
            <c:set var="rus" value="ru_RU" scope="page"/>
            <c:set var="eng" value="en_EN" scope="page"/>

            <c:if test="${sessionScope.local eq rus}">
                <a href="${pageContext.request.contextPath}/controller?command=LOCAL&newLocal=en_EN">English version</a>
            </c:if>
            <c:if test="${sessionScope.local eq eng}">
                <a href="${pageContext.request.contextPath}/controller?command=LOCAL&newLocal=ru_RU">Russian version</a>
            </c:if>
        </div>

        <div class="navbar-text mr-3">
            <span class="border">
                <b>${sessionScope.user.firstName} ${sessionScope.user.lastName}</b>
                    {Account: ${sessionScope.user.name},Role: ${sessionScope.user.role}}
            </span>
        </div>

        <a class="btn btn-info "
           href="${pageContext.request.contextPath}/controller?command=logout">
            <fmt:message key="label.user.navbar.logout"/></a>

    </div>
</nav>