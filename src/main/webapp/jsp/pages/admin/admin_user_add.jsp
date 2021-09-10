<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="USER_ADD">
        <%--User data--%>
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer01">Имя</label>
                <input type="text" class="form-control is-valid" id="validationServer01" placeholder=""
                       name="first_name" pattern="[a-zA-ZА-Яа-я]{2,55}" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer02">Фамилия</label>
                <input type="text" class="form-control is-valid" id="validationServer02" placeholder=""
                       name="last_name" pattern="[a-zA-ZА-Яа-я]{2,55}" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer03">Отчество</label>
                <input type="text" class="form-control is-valid" id="validationServer03" placeholder=""
                       name="patronymic" minlength="2" maxlength="55">
            </div>
        </div>
        <%--END User data--%>
        <%--Contract data--%>
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer05">Дата заключения контракта</label>
                <input type="date" class="form-control is-valid" id="validationServer05"
                       name="contract_date" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="inlineFormCustomSelectPref">Тариф</label>
                <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref" name="tariff_id">
                    <option selected>Choose...</option>
                    <c:forEach var="tariff" items="${tariffs}">
                        <option value="${tariff.tariffId}">${tariff.description}</option>
                        --%>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-4 mb-3">
                <label for="validationServer08">E-mail</label>
                <input type="text" class="form-control is-valid" id="validationServer08" placeholder=""
                       name="e_mail" minlength="8" maxlength="60"
                       pattern="^([A-Za-z0-9_-]+\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"
                       required>
            </div>
        </div>
        <%-- END Contract data--%>
        <button class="btn btn-primary" type="submit">Добавить пользователя</button>
    </form>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>

