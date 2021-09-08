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
                    Клиенты
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=USERS_LIST">Все клиенты</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=USER_ADD">Добавить
                        клиента</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=USER_SEARCH">Поиск пользователя</a>
                </div>
            </li>


            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Тарифы
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=TARIFF_LIST">
                        Все тарифы</a>

                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=TARIFF_ADD">
                        Добавить тариф</a>
                </div>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Карты экспрес оплаты.
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=CARD_SEARCH">
                        Поиск карты</a>

                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=CARD_ADD">
                        Добавить серию карт</a>

                </div>
            </li>


            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Сотрудники
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=EMPLOYEE_LIST">
                        Все струдники</a>
                </div>
            </li>


            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=STATISTICS">Статистика</a>
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
                <b>${user.firstName} ${user.lastName}</b>
                    {Account: ${user.name},Role: ${user.role}}
            </span>
        </div>

        <a class="btn btn-info "
           href="${pageContext.request.contextPath}/controller?command=logout">
            <fmt:message key="label.user.navbar.logout"/></a>

    </div>
</nav>