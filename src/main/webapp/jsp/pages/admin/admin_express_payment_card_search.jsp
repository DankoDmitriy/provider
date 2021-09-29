<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>
<%@ taglib prefix="ctg" uri="customtag" %>
<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="CARD_SEARCH">
        <div class="col-md-4 mb-3">
            <label for="validationServer01"><fmt:message key="label.admin.paymentCardSearch.interCardNumber"/></label>
            <input type="text" class="form-control is-valid" id="validationServer01" placeholder=""
                   name="cardNumber" pattern="[A-Z]{3,5}[0-9]{7}" required>
        </div>
        <button class="btn btn-primary" type="submit"><fmt:message
                key="label.admin.paymentCardSearch.button.search"/></button>
    </form>

    <c:if test="${searchResult == true}">
        <div class="row">
            <div class="col-sm-3">
                <h6 class="mb-0"><small><fmt:message key="label.admin.paymentCardSearch.paymentCard"/></small>
                </h6>
            </div>
            <div class="col-sm-9 text-secondary">
                <fmt:message key="label.admin.paymentCardSearch.paymentCard.amount"/> <b>${requestScope.card.amount}</b><br>
                <fmt:message key="label.admin.paymentCardSearch.paymentCarDate"/>
                <ctg:datetag localDateTime="${requestScope.card.activationDate}" fullFormat="true"/><br>
                <fmt:message key="label.admin.paymentCardSearch.paymentCardStatus"/>
                <b>${requestScope.card.cardStatus}</b>
            </div>
        </div>
        <hr>
        <c:set var="used" value="USED"/>
        <c:if test="${requestScope.card.cardStatus eq used}">
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0"><small><fmt:message
                            key="label.admin.paymentCardSearch.paymentCardActivate"/></small>
                    </h6>
                </div>
                <div class="col-sm-9 text-secondary">
                    <b>
                            ${requestScope.user.firstName}
                            ${requestScope.user.lastName}
                            ${requestScope.user.patronymic}
                    </b><br>
                    <b><fmt:message
                            key="label.admin.paymentCardSearch.contractNumber"/>: ${requestScope.user.contractNumber}</b>
                </div>
            </div>
            <hr>
        </c:if>
    </c:if>
    <c:if test="${searchResult == false}">
        <fmt:message key="label.admin.paymentCardSearch.result"/>
    </c:if>
</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>