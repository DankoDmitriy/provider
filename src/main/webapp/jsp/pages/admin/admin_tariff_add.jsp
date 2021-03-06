<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="TARIFF_ADD">
        <%--Tariff data--%>
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationServer01"> <fmt:message key="label.admin.tariffAdd.title"/></label>
                <input type="text" class="form-control is-valid" id="validationServer01" placeholder=""
                       name="tariff_name" pattern="[0-9a-zA-ZА-Яа-я -]{2,100}" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer02"><fmt:message key="label.admin.tariffAdd.maxSpeed"/></label>
                <input type="text" class="form-control is-valid" id="validationServer02" placeholder=""
                       name="max_speed" pattern="^[1-9]{1}[0-9]{0,4}$" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer03"><fmt:message key="label.admin.tariffAdd.minSpeed"/></label>
                <input type="text" class="form-control is-valid" id="validationServer03" placeholder=""
                       name="min_speed" pattern="^[1-9]{1}[0-9]{0,4}$" required>
            </div>
        </div>
        <%--END Tariff data--%>
        <%--Tariff traffic and price--%>
        <div class="form-row">

            <div class="col-md-4 mb-3">
                <label for="validationServer04"><fmt:message key="label.admin.tariffAdd.traffic"/></label>
                <input type="text" class="form-control is-valid" id="validationServer04" placeholder=""
                       name="traffic" pattern="^[1-9]{1}[0-9]{0,7}$" required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationServer05"><fmt:message key="label.admin.tariffAdd.price"/></label>
                <input type="text" class="form-control is-valid" id="validationServer05" placeholder=""
                       name="price" pattern="^[0-9]+[.][0-9]{1}[1-9]{1}$" required>
            </div>
        </div>
        <%-- END Tariff traffic and price--%>
        <%-- Tariff status and period--%>
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="inlineFormCustomSelectPref1"><fmt:message key="label.admin.tariffAdd.status"/></label>
                <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref1" name="status">
                    <option selected><fmt:message key="label.admin.tariffAdd.choose"/></option>
                    <c:forEach var="tariffStatus" items="${tariffStatuses}">
                        <option value="${tariffStatus}">${tariffStatus}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4 mb-3">
                <label for="inlineFormCustomSelectPref2"><fmt:message key="label.admin.tariffAdd.writeOf"/></label>
                <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref2" name="period">
                    <option selected><fmt:message key="label.admin.tariffAdd.choose"/></option>
                    <c:forEach var="writeOff" items="${writeOffs}">
                        <option value="${writeOff}">${writeOff}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <%-- Tariff status and period--%>
        <button class="btn btn-primary" type="submit"><fmt:message key="label.admin.tariffAdd.add"/></button>
    </form>
</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>