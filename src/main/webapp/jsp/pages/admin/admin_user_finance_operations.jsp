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
            <fmt:message key="label.admin.userTransactions.title"/><br>
            <a class="btn btn-info "
               href="${pageContext.request.contextPath}/controller?command=USER_PROFILE&user_id=${user_id}">
                <fmt:message key="label.admin.userTransactions.button.returnToProfile"/></a><br>
        </center>
        <div class="container" style="background-color: white">
            <div class="col-12">
                <%--STRART All transactions--%>
                <div class="col-12">
                    <div class="card mb-3">
                        <div class="card-body">

                            <c:forEach items="${resultList}" var="transaction">
                                <div class="row">
                                    <div class="col-sm-3">
                                        <h6 class="mb-0"><small><fmt:message
                                                key="label.admin.userTransactions.transaction"/></small>
                                        </h6>
                                    </div>
                                    <div class="col-sm-9 text-secondary">
                                            ${transaction.type}:
                                        <b>${transaction.sum}$</b><br>
                                        <fmt:parseDate value="${transaction.date}" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime" type="both"/>
                                        <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
                                    </div>
                                </div>
                                <hr>
                            </c:forEach>

                        </div>
                    </div>
                </div>
                <%--END All transactions--%>
                <center>
                    <br>
                    <c:if test="${previewPage >=0}">
                        <a class="btn btn-info "
                           href="${pageContext.request.contextPath}/controller?command=USER_FINANCE&user_id=${user_id}&nextPage=${previewPage}">
                            <fmt:message key="label.admin.userTransactions.button.previousPage"/></a>
                    </c:if>
                    <a class="btn btn-info "
                       href="${pageContext.request.contextPath}/controller?command=USER_FINANCE&user_id=${user_id}&nextPage=${nextPage}">
                        <fmt:message key="label.admin.userTransactions.button.nextPage"/></a>
                </center>

            </div>
        </div>
    </div>
</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>