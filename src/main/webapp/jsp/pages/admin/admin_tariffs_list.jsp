<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<%--<jsp:useBean id="users" scope="request" type="java.util.List"/>--%>
<div class="container mt-5">
    <table class="table">
        <thead>
        <tr>

            <th scope="col">-</th>
            <th scope="col">Название</th>
            <th scope="col">Скорость</th>
            <th scope="col">Трафик</th>
            <th scope="col">Стоимость</th>
            <th scope="col">Статус</th>
            <th scope="col">
                Периодичность</br>списания
            </th>
            <th scope="col">Редактировать</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tariffs}" var="tariff" varStatus="count">
            <tr>
                <th scope="row">${count.count}</th>
                <td>
                        ${tariff.description}
                </td>
                <td>
                    Мах скорость: ${tariff.maxSpeed} Mbit\s
                    </br>
                    Мin скорость: ${tariff.minSpeed} Mbit\s

                </td>
                <td>
                    <c:out value="${tariff.traffic/1024}"/> Gb
                </td>
                <td>${tariff.price}</td>
                <td>${tariff.status}</td>
                <td>${tariff.period}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=TARIFF_EDIT&tariff_id=${tariff.tariffId}">
                        Редактировать
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>

