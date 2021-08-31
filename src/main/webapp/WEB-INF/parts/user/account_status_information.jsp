<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%-- START Account status and other information--%>
<div class="card mt-3">
    <ul class="list-group list-group-flush">

        <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
            <h6 class="mb-0">
                <fmt:message key="label.user.accountstatus.balance"/>
            </h6>
            <span class="text-secondary"><b> ${sessionScope.user.balance}</b></span>
        </li>

        <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
            <h6 class="mb-0">
                <fmt:message key="label.user.accountstatus.status"/>
            </h6>
            <span class="text-secondary"><b> ${sessionScope.user.status}</b></span>
        </li>

        <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
            <h6 class="mb-0">
                <fmt:message key="label.user.accountstatus.tariff"/>
            </h6>
            <span class="text-secondary"><b> ${sessionScope.user.tariff.description}</b></span>
        </li>
    </ul>
</div>
<%-- END Account status and other information--%>