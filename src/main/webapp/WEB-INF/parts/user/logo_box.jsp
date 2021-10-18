<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%--START Logo Box--%>
<div class="card">
    <div class="card-body">
        <div class="d-flex flex-column align-items-center text-center">
            <img src="${pageContext.request.contextPath}/image/logo.png" alt="Admin"
                 class="rounded-circle" width="350">

            <div class="mt-3">
                <h4><fmt:message key="label.user.logobox.isp"/></h4>
                <p class="text-secondary mb-1"><fmt:message key="label.user.logobox.moto"/></p>
            </div>
        </div>
    </div>
</div>
<%--END Logo Box--%>