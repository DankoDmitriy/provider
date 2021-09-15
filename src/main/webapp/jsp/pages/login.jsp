<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../WEB-INF/parts/admin/header.jsp"%>
<div class="container mt-5">
    <c:set var="rus" value="ru_RU" scope="page"/>
    <c:set var="eng" value="en_EN" scope="page"/>

    <c:if test="${sessionScope.local eq rus}">
        <a href="${pageContext.request.contextPath}/controller?command=LOCAL&newLocal=en_EN">English version</a>
    </c:if>
    <c:if test="${sessionScope.local eq eng}">
        <a href="${pageContext.request.contextPath}/controller?command=LOCAL&newLocal=ru_RU">Russian version</a>
    </c:if>
    <%@include file="../../WEB-INF/parts/common/login_form.jsp"%>
</div>
<%@include file="../../WEB-INF/parts/admin/footer.jsp"%>
