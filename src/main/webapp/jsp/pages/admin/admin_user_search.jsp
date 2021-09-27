<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="USER_SEARCH">
        <%--User data--%>
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer01">Имя</label>
                <input type="text" class="form-control is-valid" id="validationServer01" placeholder=""
                       name="first_name" pattern="[a-zA-ZА-Яа-я]{2,55}">
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer02">Фамили</label>
                <input type="text" class="form-control is-valid" id="validationServer02" placeholder=""
                       name="last_name" pattern="[a-zA-ZА-Яа-я]{2,55}">
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer03">Отчество</label>
                <input type="text" class="form-control is-valid" id="validationServer03" placeholder=""
                       name="patronymic" pattern="[a-zA-ZА-Яа-я]{2,55}">
            </div>
        </div>
        <%--END User data--%>
        <%--Contract data--%>
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer05">Контракт</label>
                <input type="text" class="form-control is-valid" id="validationServer05"
                       name="contract_number"
                       pattern="^[0-9]{2,15}$">
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer08">E-mail</label>
                <input type="text" class="form-control is-valid" id="validationServer08" placeholder=""
                       name="e_mail" minlength="5" maxlength="15"
                       pattern="^[A-Za-z0-9_-]{5,15}$">
            </div>
        </div>
        <%-- END Contract data--%>
        <button class="btn btn-primary" type="submit">Поиск</button>
    </form>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>

