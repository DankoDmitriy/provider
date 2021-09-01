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

    <div class="col-lg-8">
        <div class="card">
            <div class="card-body">
                <c:if test="${not empty errorMessage}">
                    <div style="text-align: center;">${errorMessage}</div>
                </c:if>
                <form action="/provider_war_exploded/controller" method="post">
                    <input type="hidden" name="command" value="USER_CHANGE_PASSWORD">
                    <div class="row mb-3">
                        <div class="col-sm-3">
                            <h6 class="mb-0"><fmt:message key="label.user.changePassword.newPassword"/></h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <input type="password" class="form-control" name="password">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-9 text-secondary">
                            <input type="submit" class="btn btn-primary px-4" value="<fmt:message key="label.user.changePassword.changeButton"/>">
                        </div>
                    </div>
                </form>
                <fmt:message key="label.user.changePassword.msg"/>

            </div>
        </div>
    </div>
    <%--FOOTER--%>
<%@include file="../../../WEB-INF/parts/user/footer.jsp" %>