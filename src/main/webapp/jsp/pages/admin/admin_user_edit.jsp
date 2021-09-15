<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="USER_EDIT">
        <input type="hidden" name="user_id" value="${user.userId}">
        <input type="hidden" name="userOrigin" value="${userOrigin}">
        <%--User data--%>
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer01"><fmt:message key="label.admin.userEdit.firstName"/></label>
                <input type="text" class="form-control is-valid" id="validationServer01" placeholder=""
                       value="${user.firstName}" name="first_name" pattern="[a-zA-ZА-Яа-я]{2,55}" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer02"><fmt:message key="label.admin.userEdit.lastName"/></label>
                <input type="text" class="form-control is-valid" id="validationServer02" placeholder=""
                       value="${user.lastName}" name="last_name" pattern="[a-zA-ZА-Яа-я]{2,55}" required>
            </div>

        </div>
        <%--END User data--%>
        <%--Othet data--%>
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer03"><fmt:message key="label.admin.userEdit.patronymic"/></label>
                <input type="text" class="form-control is-valid" id="validationServer03" placeholder=""
                       value="${user.patronymic}" name="patronymic" pattern="[a-zA-ZА-Яа-я]{2,55}">
            </div>

            <div class="col-md-4 mb-3">
                <label for="validationServer08"><fmt:message key="label.admin.userEdit.email"/></label>
                <input type="text" class="form-control is-valid" id="validationServer08" placeholder=""
                       name="e_mail" minlength="8" maxlength="60"
                       value="${user.email}"
                       pattern="^([A-Za-z0-9_-]+\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"
                       required>
            </div>
        </div>
        <%-- END Othet data--%>
        <button class="btn btn-primary" type="submit"><fmt:message key="label.admin.userEdit.edit"/></button>
    </form>
</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>