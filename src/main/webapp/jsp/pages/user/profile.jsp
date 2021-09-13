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

    <div class="col-md-8">

        <%--START User profile information--%>
        <div class="card mb-3">
            <div class="card-body">
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message key="label.user.profile.personal.information.fullName"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.lastName} ${sessionScope.user.firstName} ${sessionScope.user.patronymic}
                    </div>
                </div>
                <hr>

                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message key="label.user.profile.personal.information.email"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.email}
                    </div>
                </div>
                <hr>

                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message
                                key="label.user.profile.personal.information.contractNumber"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.contractNumber}
                    </div>
                </div>
                <hr>

                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">
                            <fmt:message key="label.user.profile.personal.information.contractDate"/>
                        </h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <fmt:parseDate value="${sessionScope.user.contractDate}" pattern="yyyy-MM-dd"
                                       var="parsedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${parsedDateTime}"/>
                    </div>
                </div>
                <hr>

                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message key="label.user.profile.personal.information.traffic"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        <c:out value="${sessionScope.user.traffic/1024}"/> GB
                    </div>
                </div>
                <hr>

                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0"><fmt:message key="label.user.profile.personal.information.userName"/></h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.name}
                    </div>
                </div>
                <hr>

                <div class="row">
                    <div class="col-sm-12">
                        <a class="btn btn-info "
                           href="${pageContext.request.contextPath}/controller?command=USER_CHANGE_PASSWORD">
                            <fmt:message key="label.user.profile.personal.information.changePassword"/></a>
                    </div>
                </div>
            </div>
        </div>
        <%--END User profile information--%>

        <%--START Tariff information--%>
        <div class="row gutters-sm">
            <div class="col-sm-6 mb-3">
                <div class="card h-100">
                    <div class="card-body">
                        <h6 class="d-flex align-items-center mb-3"><i class="material-icons text-info mr-2">
                            <fmt:message key="label.user.profile.tariff.information.inf"/></i>
                            ${sessionScope.user.tariff.description}</h6>

                        <small><fmt:message key="label.user.profile.tariff.information.maxSpeed"/>
                            ${sessionScope.user.tariff.maxSpeed} Mbit\sec</small>
                        <div class="progress mb-3" style="height: 5px">
                            <div class="progress-bar bg-primary" role="progressbar" style="width: 100%"
                                 aria-valuenow="72" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>

                        <small><fmt:message key="label.user.profile.tariff.information.minSpeed"/>
                            ${sessionScope.user.tariff.minSpeed} Mbit\sec</small>
                        <div class="progress mb-3" style="height: 5px">
                            <div class="progress-bar bg-primary" role="progressbar" style="width: 100%"
                                 aria-valuenow="72" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>

                        <small><fmt:message key="label.user.profile.tariff.information.traffic"/>
                            <c:out value="${sessionScope.user.tariff.traffic/1024}"/> GB</small>
                        <div class="progress mb-3" style="height: 5px">
                            <div class="progress-bar bg-primary" role="progressbar" style="width: 100%"
                                 aria-valuenow="72" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>

                        <small><fmt:message key="label.user.profile.tariff.information.price"/>
                            ${sessionScope.user.tariff.price} $</small>
                        <div class="progress mb-3" style="height: 5px">
                            <div class="progress-bar bg-primary" role="progressbar" style="width: 100%"
                                 aria-valuenow="72" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>

                        <small><fmt:message key="label.user.profile.tariff.information.status"/>
                            ${sessionScope.user.tariff.status} </small>
                        <div class="progress mb-3" style="height: 5px">
                            <div class="progress-bar bg-primary" role="progressbar" style="width: 100%"
                                 aria-valuenow="72" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>

                        <small><fmt:message key="label.user.profile.tariff.information.writeOf"/>
                            ${sessionScope.user.tariff.period} </small>
                        <div class="progress mb-3" style="height: 5px">
                            <div class="progress-bar bg-primary" role="progressbar" style="width: 100%"
                                 aria-valuenow="72" aria-valuemin="0" aria-valuemax="100"></div>
                        </div>
                    </div>
                </div>
            </div>
            <%--END Tariff information--%>

            <%-- START last 5 transactions--%>
            <div class="col-sm-6 mb-3">
                <div class="card h-100">
                    <div class="card-body">
                        <h6 class="d-flex align-items-center mb-3"><i class="material-icons text-info mr-2">
                            <fmt:message key="label.user.profile.last.transactions.title"/> </i></h6>

                        <c:forEach items="${transactions}" var="transaction">
                            <small>
                                    ${transaction.type}:
                                <b>${transaction.sum}$</b>
                                <fmt:parseDate value="${transaction.date}" pattern="yyyy-MM-dd'T'HH:mm"
                                               var="parsedDateTime" type="both"/>
                                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
                            </small>
                            <div class="progress mb-3" style="height: 5px">
                                <div class="progress-bar bg-primary" role="progressbar" style="width: 0%"
                                     aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </c:forEach>
                        <div class="row">
                            <div class="col-sm-12">
                                <a class="btn btn-info "
                                   href="${pageContext.request.contextPath}/controller?command=PERSONAL_FINANCE_OPERATIONS">
                                    <fmt:message key="label.user.profile.last.transactions.button"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%-- END last 5 transactions--%>
        </div>

    </div>
</div>
<%--FOOTER--%>
<%@include file="../../../WEB-INF/parts/user/footer.jsp" %>