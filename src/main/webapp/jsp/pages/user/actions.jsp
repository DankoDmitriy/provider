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

    <%--STRART All transactions--%>
    <div class="col-md-8">
        <div class="card mb-3">
            <div class="card-body">

                <c:forEach items="${actions}" var="action">
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0"><small><fmt:message key="label.user.action.title"/></small>
                            </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <b>${action.actionType}:</b>
                            <fmt:parseDate value="${action.dateTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                           var="parsedDateTime" type="both"/>
                            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
                            <fmt:message key="label.user.action.tariffName"/>
                            <b>${action.tariffName}:</b>
                        </div>
                    </div>
                    <hr>
                </c:forEach>

            </div>
        </div>
    </div>
    <%--END All transactions--%>
</div>
<%--FOOTER--%>
<%@include file="../../../WEB-INF/parts/user/footer.jsp" %>