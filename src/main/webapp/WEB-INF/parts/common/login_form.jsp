<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<fmt:setLocale value="${sessionScope.local}" scope="session"/>--%>
<%--<fmt:setBundle basename="local.pagecontent"/>--%>
<%--<fmt:message key="label.hello"/>--%>

<H1><fmt:message key="label.login.hello"/> ${sessionScope.user.role}</H1>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="login">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <fmt:message key="label.login.login"/>:</label>
        <div class="col-sm-4">
            <input type="text" name="name" placeholder="<fmt:message key="label.login.login"/>" class="form-control"/ >
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <fmt:message key="label.login.password"/>: </label>
        <div class="col-sm-4">
            <input type="password" name="password" class="form-control"
                   placeholder="<fmt:message key="label.login.password"/>"/>
        </div>
    </div>
    <button type="submit" class="btn btn-primary"/>
    <fmt:message key="label.login.signin"/></button>

    <c:if test="${sessionScope.isLoginError == true}">
        <div class="col-sm-4">Your login or password is not correct. Try again.</div>
    </c:if>

</form>
