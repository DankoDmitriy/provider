<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>
<%@ taglib prefix="ctg" uri="customtag" %>
<%@include file="../../../WEB-INF/parts/admin/header.jsp" %>
<%@include file="../../../WEB-INF/parts/admin/navbar.jsp" %>

<div class="container mt-5">
    <c:if test="${resultWork == true}">
        <p><fmt:message key="label.admin.usersList.message.changeUserDataTrue"/></p>
    </c:if>
    <c:if test="${resultWork == false}">
        <p><fmt:message key="label.admin.usersList.message.changeUserDataFalse"/></p>
    </c:if>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">-</th>
            <th scope="col"><fmt:message key="label.admin.usersList.firstAndLastName"/></th>
            <th scope="col"><fmt:message key="label.admin.usersList.contract"/></th>
            <th scope="col"><fmt:message key="label.admin.usersList.balance"/></th>
            </th>
            <th scope="col"><fmt:message key="label.admin.usersList.email"/></th>
            </th>
            <th scope="col"><fmt:message key="label.admin.usersList.status"/></th>
            </th>
            <th scope="col"><fmt:message key="label.admin.usersList.edit"/></th>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultList}" var="user" varStatus="count">
            <tr>
                <th scope="row">${count.count}</th>
                <td>
                        ${user.firstName}
                        ${user.lastName}
                    </br>
                        ${user.patronymic}
                </td>
                <td>
                        ${user.contractNumber}
                    </br>
                    <ctg:datetag localDateTime="${user.contractDate}" fullFormat="false"/>
                </td>
                <td>${user.balance}</td>
                <td>${user.email}</td>
                <td>${user.status}</td>
                <td>
                    <a class="iksweb"
                       href="${pageContext.request.contextPath}/controller?command=USER_EDIT&user_id=${user.userId}"
                       title="Edit">Edit</a>
                    <a class="iksweb"
                       href="${pageContext.request.contextPath}/controller?command=USER_PROFILE&user_id=${user.userId}"
                       title="Profile">Profile</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <center>
        <br>
        <c:if test="${previewPage >=0}">
            <a class="btn btn-info "
               href="${pageContext.request.contextPath}/controller?command=USERS_LIST&userRole=${userRole}&nextPage=${previewPage}">
                <fmt:message key="label.admin.userActions.button.previousPage"/></a>
        </c:if>
        <c:if test="${nextPage >=0}">
            <a class="btn btn-info "
               href="${pageContext.request.contextPath}/controller?command=USERS_LIST&userRole=${userRole}&nextPage=${nextPage}">
                <fmt:message key="label.admin.userActions.button.nextPage"/></a>
        </c:if>
    </center>

</div>
<%@include file="../../../WEB-INF/parts/admin/footer.jsp" %>

