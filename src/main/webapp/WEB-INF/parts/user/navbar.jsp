<!-- Breadcrumb -->
<nav aria-label="breadcrumb" class="main-breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=HOME">
                Home
            </a></li>

        <%--                Menu for actions--%>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=PERSONAL_FINANCE_OPERATIONS">
                Finance
            </a></li>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=USER_TARIFF_LIST">
                Tariffs
            </a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}">Actions</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}">Tickets</a></li>
        <li class="breadcrumb-item">
            <a href="${pageContext.request.contextPath}/controller?command=logout">
                Logout
            </a></li>
    </ol>

</nav>
<!-- /Breadcrumb -->