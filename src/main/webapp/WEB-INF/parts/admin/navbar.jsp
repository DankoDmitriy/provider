<nav class="navbar navbar-expand-lg navbar-light bg-light" xmlns="http://www.w3.org/1999/html">

    <a class="navbar-brand" href="/">DankoBill</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
<%--            <li class="nav-item">--%>
<%--                <a class="nav-link" href="/">Home</a>--%>
<%--            </li>--%>
<%--            <li class="nav-item">--%>
<%--                <a class="nav-link" href="/main">Messages</a>--%>
<%--            </li>--%>


            <%--            <#if isAdmin>--%>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Clients
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="/enterprise">All clients</a>
                </div>
            </li>
            <%--        </#if>--%>


            <%--    <#if isAdmin>--%>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Tariff Plans
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="/car">Tariffs</a>
                </div>
            </li>

            <%--        <#if isAdmin>--%>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Management
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="/department">Users Roles</a>
                </div>
            </li>
            <%--    </#if>--%>

<%--            </#if>--%>

<%--            <#if user??>--%>
<%--            <li class="nav-item">--%>
<%--                <a class="nav-link" href="/user/profile">Profile</a>--%>
<%--            </li>--%>
<%--            </#if>--%>

<%--            </ul>--%>
<%--            <div class="navbar-text mr-3">${name}</div>--%>
<%--            <#if known><@l.logout></@l.logout></#if>--%>

    </div>
</nav>