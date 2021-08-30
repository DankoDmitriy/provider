<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--Надо ли тут--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru_RU" scope="session"/>
<fmt:setBundle basename="pagecontent"/>

<%@include file="../../WEB-INF/parts/admin/header.jsp"%>
<div class="container mt-5">
    <%@include file="../../WEB-INF/parts/common/login_form.jsp"%>
</div>
<%@include file="../../WEB-INF/parts/admin/footer.jsp"%>
