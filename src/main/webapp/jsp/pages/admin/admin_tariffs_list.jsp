<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <c:if test="${resultWork == true}">
        <p><fmt:message key="label.admin.tariffsList.message.changeTariffDataTrue"/></p>
    </c:if>
    <c:if test="${resultWork == false}">
        <p><fmt:message key="label.admin.tariffsList.message.changeTariffDataFalse"/></p>
    </c:if>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">-</th>
            <th scope="col"><fmt:message key="label.admin.tariffsList.title"/></th>
            <th scope="col"><fmt:message key="label.admin.tariffsList.speed"/></th>
            <th scope="col"><fmt:message key="label.admin.tariffsList.traffic"/></th>
            <th scope="col"><fmt:message key="label.admin.tariffsList.price"/></th>
            <th scope="col"><fmt:message key="label.admin.tariffsList.status"/></th>
            <th scope="col">
                <fmt:message key="label.admin.tariffsList.writeOfPeriod"/>
            </th>
            <th scope="col"><fmt:message key="label.admin.tariffsList.edit"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultList}" var="tariff" varStatus="count">
            <tr>
                <th scope="row">${count.count}</th>
                <td>
                        ${tariff.description}
                </td>
                <td>
                    Мах <fmt:message key="label.admin.tariffsList.speed"/>: ${tariff.maxSpeed}
                    <fmt:message key="label.admin.tariffsList.mBitS"/>
                    </br>
                    Мin <fmt:message key="label.admin.tariffsList.speed"/>: ${tariff.minSpeed}
                    <fmt:message key="label.admin.tariffsList.mBitS"/>

                </td>
                <td>
                    <c:out value="${tariff.traffic/1024}"/> <fmt:message key="label.admin.tariffsList.gb"/>
                </td>
                <td>${tariff.price}</td>
                <td>${tariff.status}</td>
                <td>${tariff.period}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=TARIFF_EDIT&tariff_id=${tariff.tariffId}">
                        <fmt:message key="label.admin.tariffsList.edit"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <center>
        <br>
        <c:if test="${previewPage >=0}">
            <a class="btn btn-info "
               href="${pageContext.request.contextPath}/controller?command=TARIFF_LIST&nextPage=${previewPage}">
                <fmt:message key="label.admin.userActions.button.previousPage"/></a>
        </c:if>
        <c:if test="${nextPage >=0}">
            <a class="btn btn-info "
               href="${pageContext.request.contextPath}/controller?command=TARIFF_LIST&nextPage=${nextPage}">
                <fmt:message key="label.admin.userActions.button.nextPage"/></a>
        </c:if>
    </center>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>

