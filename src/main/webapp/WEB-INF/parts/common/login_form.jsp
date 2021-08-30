<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru_RU" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>
<fmt:message key="label.hello"/>



<H1>Hello ${sessionScope.user.role}</H1>
<form action="/provider_war_exploded/controller" method="post">
    <input type="hidden" name="command" value="login">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Login: </label>
        <div class="col-sm-4">
            <input type="text" name="name" placeholder="user name" class="form-control"/ >
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Password: </label>
        <div class="col-sm-4">
            <input type="password" name="password" class="form-control" placeholder="password"/>
        </div>
    </div>
    <button type="submit" class="btn btn-primary"/>
    Sign In</button>

    <c:if test="${sessionScope.isLoginError == true}">
        <div class="col-sm-4">Your login or password is not correct. Try again.</div>
    </c:if>

</form>
