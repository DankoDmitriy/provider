<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>


<div class="container mt-5">

    <table class="table table-sm">
        <thead>
        <tr>
            <th scope="col">#</th>
            <td colspan="2">Информация</td>
        </tr>
        </thead>

        <tbody>
        <tr>
            <th scope="row">Данные доступа</th>
            <td>
                Login: ${trUser.user.name}
                <br>
                Password: ${trUser.password}
            </td>

        </tr>
        <tr>
            <th scope="row">Тарифный план ${trUser.user.tariff.description}</th>
            <td>
                <fmt:message key="label.user.tariffs.information.maxSpeed"/> ${trUser.user.tariff.maxSpeed} Mbit\s:</br>
                <fmt:message key="label.user.tariffs.information.minSpeed"/> ${trUser.user.tariff.minSpeed} Mbit\s:</br>
                <fmt:message key="label.user.tariffs.information.traffic"/> - <c:out
                    value="${trUser.user.tariff.traffic/1024}"/> GB</br>
                <fmt:message key="label.user.tariffs.information.price"/> - ${trUser.user.tariff.price} $</br>
                <fmt:message key="label.user.tariffs.information.writeOf"/> ${trUser.user.tariff.period}</br>

            </td>

        </tbody>
    </table>
</div>


<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>