<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>
<div class="container mt-5">

    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="CARD_ADD">

        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer01">Серия карт</label>
                <input type="text" class="form-control is-valid" id="validationServer01" placeholder=""
                       name="series" pattern="[A-Z]{3,5}" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer02">Номинал</label>
                <input type="text" class="form-control is-valid" id="validationServer02" placeholder=""
                       name="amount" pattern="[1-9]{1}[0-9]{0,4}" required>
            </div>
        </div>

        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer02">Колличество</label>
                <input type="text" class="form-control is-valid" id="validationServer02" placeholder=""
                       name="count" pattern="[1-9]{1}[0-9]{0,4}" required>
            </div>

            <div class="col-md-4 mb-3">
                <label for="validationServer05">Действительны ДО</label>
                <input type="date" class="form-control is-valid" id="validationServer05"
                       name="date_expired" required>
            </div>
        </div>

        <button class="btn btn-primary" type="submit">Добавить серию карт</button>
    </form>
</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>