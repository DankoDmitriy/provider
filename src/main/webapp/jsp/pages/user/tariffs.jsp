<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%--HEADER--%>
<%@include file="../../../WEB-INF/parts/user/header.jsp" %>
<%--NAVIGATION MENU--%>
<%@include file="../../../WEB-INF/parts/user/navbar.jsp" %>

<%--Left inform box--%>
<div class="row gutters-sm">
    <div class="col-md-4 mb-3">

        <%--Logo Box--%>
        <%@include file="../../../WEB-INF/parts/user/logo_box.jsp" %>
        <%--Account status and other information--%>
        <%@include file="../../../WEB-INF/parts/user/account_status_information.jsp" %>
    </div>

    <%--STRART All Tariffs--%>
    <div class="col-md-8">
        <div class="card mb-3">
            <div class="card-body">

                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">${user.tariff.description}:</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <fmt:message key="label.user.tariffs.information.maxSpeed"/> ${user.tariff.maxSpeed} Mbit\s:</br>
                        <fmt:message key="label.user.tariffs.information.minSpeed"/> ${user.tariff.minSpeed} Mbit\s:</br>
                        <fmt:message key="label.user.tariffs.information.traffic"/> - <c:out value="${user.tariff.traffic/1024}"/> GB</br>
                        <fmt:message key="label.user.tariffs.information.price"/> - ${user.tariff.price} $</br>
                        <fmt:message key="label.user.tariffs.information.writeOf"/> ${user.tariff.period}</br>
                        <div align="right">
                                <b> <fmt:message key="label.user.tariffs.information.activeTariff"/></b>
                        </div>
                    </div>
                </div>
                <hr>

                <c:forEach items="${tariffs}" var="tariff">
                    <c:if test="${tariff.tariffId != sessionScope.user.tariff.tariffId}">
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">${tariff.description}:</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <fmt:message key="label.user.tariffs.information.maxSpeed"/> ${tariff.maxSpeed} Mbit\s:</br>
                            <fmt:message key="label.user.tariffs.information.minSpeed"/> ${tariff.minSpeed} Mbit\s:</br>
                            <fmt:message key="label.user.tariffs.information.traffic"/> - <c:out value="${tariff.traffic/1024}"/> GB</br>
                            <fmt:message key="label.user.tariffs.information.price"/> - ${tariff.price} $</br>
                            <fmt:message key="label.user.tariffs.information.writeOf"/> ${tariff.period}</br>
                            <div align="right">
                                    <a class="btn btn-info "
                                       href="/provider_war_exploded/controller?command=USER_CHANGE_TARIFF&tariffId=${tariff.tariffId}">
                                        <fmt:message key="label.user.tariffs.information.connect"/></a>
                            </div>
                        </div>
                    </div>
                    <hr>
                    </c:if>
                </c:forEach>

            </div>
        </div>
    </div>
    <%--END All Tariffs--%>
</div>
<%--FOOTER--%>
<%@include file="../../../WEB-INF/parts/user/footer.jsp" %>