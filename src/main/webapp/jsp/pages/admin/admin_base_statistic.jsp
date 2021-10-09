<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <br>
    <center><fmt:message key="label.admin.static.usersOnTariff.title"/></center>
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="label.admin.static.usersOnTariff.tariffName"/></th>
            <th scope="col"><fmt:message key="label.admin.static.usersOnTariff.usersCount"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${baseStatistic.usersOnTariffs}" var="tarifStatistic">
            <tr>
                <td>${tarifStatistic.parameterName}</td>
                <td>${tarifStatistic.count}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <center><fmt:message key="label.admin.static.usersByStatus.title"/></center>
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="label.admin.static.usersByStatus.statusName"/></th>
            <th scope="col"><fmt:message key="label.admin.static.usersByStatus.usersCount"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${baseStatistic.usersByStatus}" var="userStatuStatistic">
            <tr>
                <td>${userStatuStatistic.parameterName}</td>
                <td>${userStatuStatistic.count}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <center><fmt:message key="label.admin.static.paymentCardsGenerated.title"/></center>
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="label.admin.static.paymentCardsGenerated.amount"/></th>
            <th scope="col"><fmt:message key="label.admin.static.paymentCardsGenerated.count"/></th>
            <th scope="col"><fmt:message key="label.admin.static.paymentCardsGenerated.sum"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${baseStatistic.paymentCardsGenerated}" var="paymentCardsGeneratedStatistic">
            <tr>
                <td>${paymentCardsGeneratedStatistic.amount}</td>
                <td> ${paymentCardsGeneratedStatistic.count}</td>
                <td> ${paymentCardsGeneratedStatistic.sum}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <center><fmt:message key="label.admin.static.paymentCardsNotActivated.title"/></center>
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="label.admin.static.paymentCardsNotActivated.amount"/></th>
            <th scope="col"><fmt:message key="label.admin.static.paymentCardsNotActivated.count"/></th>
            <th scope="col"><fmt:message key="label.admin.static.paymentCardsNotActivated.sum"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${baseStatistic.paymentCardsNotActivated}" var="paymentCardsNotActivatedtatistic">
            <tr>
                <td>${paymentCardsNotActivatedtatistic.amount}</td>
                <td> ${paymentCardsNotActivatedtatistic.count}</td>
                <td> ${paymentCardsNotActivatedtatistic.sum}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>