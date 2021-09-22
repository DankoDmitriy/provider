<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/profile.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/bootstrap.min.css">

<div class="container mt-2">
    <div class="container">
        <center>
            <fmt:message key="label.admin.userActions.title"/><br>
            <a class="btn btn-info "
               href="${pageContext.request.contextPath}/controller?command=USER_PROFILE&user_id=${user_id}">
                <fmt:message key="label.admin.userActions.button.returnToProfile"/></a><br>
        </center>
        <%--#bef6fa--%>
        <div class="container" style="background-color: white">
            <div class="col-12">
            <%--STRART All transactions--%>
            <div class="col-12">
                <div class="card mb-3">
                    <div class="card-body">

                        <c:forEach items="${resultList}" var="action">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"><small><fmt:message key="label.admin.userActions.action"/></small>
                                    </h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <b>${action.actionType}:</b><br>
                                    <fmt:parseDate value="${action.dateTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                                   var="parsedDateTime" type="both"/>
                                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/><br>
                                    <fmt:message key="label.admin.userActions.tariffName"/><br>
                                    <b>${action.tariffName}:</b>
                                </div>
                            </div>
                            <hr>
                        </c:forEach>

                    </div>
                </div>
            </div>

                <center>
                    <br>
                    <c:if test="${previewPage >=0}">
                        <a class="btn btn-info "
                           href="${pageContext.request.contextPath}/controller?command=USER_ACTION&user_id=${user_id}&nextPage=${previewPage}">
                            <fmt:message key="label.admin.userActions.button.previousPage"/></a>
                    </c:if>
                    <a class="btn btn-info "
                       href="${pageContext.request.contextPath}/controller?command=USER_ACTION&user_id=${user_id}&nextPage=${nextPage}">
                        <fmt:message key="label.admin.userActions.button.nextPage"/></a>
                </center>

            </div>
        </div>
    </div>
</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>