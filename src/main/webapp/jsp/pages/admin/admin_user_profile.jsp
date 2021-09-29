<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>
<%@ taglib prefix="ctg" uri="customtag" %>
<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/profile.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/bootstrap.min.css">

<div class="container mt-2">

    <div class="container">
        <div class="container" style="background-color: #bef6fa">
            <div class="col-12">
                <%--START User profile information--%>
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0"><fmt:message
                                        key="label.admin.userProfile.fullName"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${requestScope.userProfile.lastName} ${requestScope.userProfile.firstName} ${requestScope.userProfile.patronymic}
                            </div>
                        </div>
                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0"><fmt:message key="label.admin.userProfile.email"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${requestScope.userProfile.email}
                            </div>
                        </div>
                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0"><fmt:message
                                        key="label.admin.userProfile.contractNumber"/><br>
                                    <fmt:message
                                            key="label.admin.userProfile.contractDate"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${requestScope.userProfile.contractNumber}<br>
                                <ctg:datetag localDateTime="${requestScope.userProfile.contractDate}"
                                             fullFormat="false"/>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">
                                    <fmt:message key="label.admin.userProfile.tariff"/>
                                </h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${requestScope.userProfile.tariff.description}
                            </div>
                        </div>
                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0"><fmt:message
                                        key="label.admin.userProfile.traffic"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                <c:out value="${requestScope.userProfile.traffic/1024}"/> GB
                            </div>
                        </div>
                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0"><fmt:message
                                        key="label.admin.userProfile.accessData"/></h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                Login: ${requestScope.userProfile.name}<br>
                                Status: ${requestScope.userProfile.status}<br>
                                Role: ${requestScope.userProfile.role}:
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12">

                                <c:set var="block" value="BLOCK" scope="page"/>
                                <c:set var="wait_activate" value="WAIT_ACTIVATE" scope="page"/>
                                <c:set var="active" value="ACTIVE" scope="page"/>

                                <c:if test="${requestScope.userProfile.status eq block}">
                                    <a class="btn btn-info "
                                       href="${pageContext.request.contextPath}/controller?command=USER_BAN&user_id=${requestScope.userProfile.userId}">
                                        <fmt:message key="label.admin.userProfile.button.unBan"/></a>
                                </c:if>
                                <c:if test="${requestScope.userProfile.status eq wait_activate || active}">
                                    <a class="btn btn-info "
                                       href="${pageContext.request.contextPath}/controller?command=USER_BAN&user_id=${requestScope.userProfile.userId}">
                                        <fmt:message key="label.admin.userProfile.button.ban"/></a>
                                </c:if>

                                <c:if test="${requestScope.userProfile.status eq active}">
                                    <a class="btn btn-info "
                                       href="${pageContext.request.contextPath}/controller?command=USER_BAN&user_id=${requestScope.userProfile.userId}">
                                        <fmt:message key="label.admin.userProfile.button.ban"/></a>
                                </c:if>

                                <a class="btn btn-info "
                                   href="${pageContext.request.contextPath}/controller?command=CHANGE_ROLE&user_id=${requestScope.userProfile.userId}">
                                    <fmt:message key="label.admin.userProfile.button.changeRole"/></a>

                                <a class="btn btn-info "
                                   href="${pageContext.request.contextPath}/controller?command=USER_CHANGE_PASSWORD">
                                    <fmt:message key="label.admin.userProfile.button.changePassword"/></a>

                                <a class="btn btn-info "
                                   href="${pageContext.request.contextPath}/controller?command=USER_EDIT&user_id=${requestScope.userProfile.userId}">
                                    <fmt:message key="label.admin.userProfile.button.edit"/></a>

                            </div>
                        </div>
                    </div>
                </div>
                <%--END User profile information--%>

                <div class="row gutters-sm">

                    <%-- START last 5 transactions--%>
                    <div class="col-sm-6 mb-3">
                        <div class="card h-100">
                            <div class="card-body">
                                <h6 class="d-flex align-items-center mb-3"><i class="material-icons text-info mr-2">
                                    <fmt:message key="label.admin.userProfile.transactions.title"/> </i></h6>

                                <c:forEach items="${transactionList}" var="transaction">
                                    <small>
                                            ${transaction.type}:
                                        <b>${transaction.sum}$</b>
                                        <ctg:datetag localDateTime="${transaction.date}"
                                                     fullFormat="true"/>
                                    </small>
                                    <div class="progress mb-3" style="height: 5px">
                                        <div class="progress-bar bg-primary" role="progressbar" style="width: 0%"
                                             aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </c:forEach>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <a class="btn btn-info "
                                           href="${pageContext.request.contextPath}/controller?command=USER_FINANCE&user_id=${requestScope.userProfile.userId}&nextPage=0&previewPage=-1">
                                            <fmt:message key="label.admin.userProfile.button.transactions"/></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%-- END last 5 transactions--%>


                    <%-- START last 5 actions--%>
                    <div class="col-sm-6 mb-3">
                        <div class="card h-100">
                            <div class="card-body">
                                <h6 class="d-flex align-items-center mb-3"><i class="material-icons text-info mr-2">
                                    <fmt:message key="label.admin.userProfile.actions.title"/> </i></h6>

                                <c:forEach items="${userActionList}" var="action">
                                    <small>
                                            ${action.actionType}:
                                        <b>${action.tariffName}</b>
                                        <ctg:datetag localDateTime="${action.dateTime}" fullFormat="true"/>
                                    </small>
                                    <div class="progress mb-3" style="height: 5px">
                                        <div class="progress-bar bg-primary" role="progressbar" style="width: 0%"
                                             aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </c:forEach>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <a class="btn btn-info "
                                           href="${pageContext.request.contextPath}/controller?command=USER_ACTION&user_id=${requestScope.userProfile.userId}&nextPage=0&previewPage=-1">
                                            <fmt:message key="label.admin.userProfile.button.actions"/></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%-- END last 5 actions--%>
                </div>
            </div>
        </div>
    </div>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>