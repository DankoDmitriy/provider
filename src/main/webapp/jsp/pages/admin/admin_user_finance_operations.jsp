<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>

<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/profile.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/bootstrap.min.css">

<div class="container mt-2">
    <div class="container">
        <center>Finances operations of user</center>
        <div class="container" style="background-color: #bef6fa">
            <div class="col-12">
                <center>Finances operations of user</center>
                <br>
                <c:if test="${previewPage >=0}">
                    <a class="btn btn-info "
                       href="${pageContext.request.contextPath}/controller?command=USER_FINANCE&user_id=${user_id}&nextPage=${previewPage}">
                        Пред идущая страница</a>
                </c:if>
                <a class="btn btn-info "
                   href="${pageContext.request.contextPath}/controller?command=USER_FINANCE&user_id=${user_id}&nextPage=${nextPage}">
                    Следующая страницы</a>

                <table>
                    <tr>
                        <td>Дата</td>
                        <td>Тип</td>
                        <td>Сумма</td>
                    </tr>
                    <c:forEach var="transaction" items="${transactions}" varStatus="count">
                        <tr>
                            <td>${transaction.date}</td>
                            <td>${transaction.type}</td>
                            <td>${transaction.sum}</td>
                        </tr>
                    </c:forEach>
                </table>


            </div>
        </div>
    </div>
</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>