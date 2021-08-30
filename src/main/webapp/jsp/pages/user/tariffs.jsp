<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

                <c:forEach items="${tariffs}" var="tariff">
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">${tariff.description}:</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            Max speed - ${tariff.maxSpeed} Mbit\s:</br>
                            Min speed- ${tariff.minSpeed} Mbit\s:</br>
                            Traffic - <c:out value="${tariff.traffic/1024}"/> GB</br>
                            Price - ${tariff.price} $</br>
                            Period ${tariff.period}</br>
                            <div align="right">

                                <c:if test="${tariff.tariffId != sessionScope.user.tariff.tariffId}">
                                    <a class="btn btn-info "
                                       href="/provider_war_exploded/controller?command=USER_CHANGE_TARIFF&tariffId=${tariff.tariffId}">
                                        Connect this tariff</a>
                                </c:if>

                                <c:if test="${tariff.tariffId == sessionScope.user.tariff.tariffId}">
                                    <b> This is your tariff</b>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <hr>
                </c:forEach>

            </div>
        </div>
    </div>
    <%--END All Tariffs--%>
</div>
<%--FOOTER--%>
<%@include file="../../../WEB-INF/parts/user/footer.jsp" %>