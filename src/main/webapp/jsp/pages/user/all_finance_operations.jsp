<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--HEADER--%>
<%@include file="../../../WEB-INF/parts/user/header.jsp" %>
<%--NAVIGATION MENU--%>
<%@include file="../../../WEB-INF/parts/user/navbar.jsp" %>

<%--Left inform box--%>
<div class="row gutters-sm">
    <div class="col-md-4 mb-3">

        <%--Logo Box--%>
        <%@include file="../../../WEB-INF/parts/user/logo_box.jsp" %>
        <%--Account status and other information--%>
        <%@include file="../../../WEB-INF/parts/user/account_status_information.jsp" %>
    </div>

    <%--STRART All transactions--%>
    <div class="col-md-8">
        <div class="card mb-3">
            <div class="card-body">

                <c:forEach items="${transactionsAll}" var="transaction">
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0"><small>Operation:</small></h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                                ${transaction.type}: <b>${transaction.sum}$</b> ${transaction.date}
                        </div>
                    </div>
                    <hr>
                </c:forEach>

            </div>
        </div>
    </div>
    <%--END All transactions--%>
</div>
<%--FOOTER--%>
<%@include file="../../../WEB-INF/parts/user/footer.jsp" %>