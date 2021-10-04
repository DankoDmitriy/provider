<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error Page</title></head>
<body>
Request from ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode} <br/>
Exception: ${pageContext.exception} <br/>
Message from exception: ${pageContext.exception.message}<br/>
stack trace : <br/>
<c:forEach items="${exception.stackTrace}" var="element">
    element =   ${element} </c:forEach> <br/>
<a href="${pageContext.request.contextPath}/index.jsp">click here</a>
</body>
</html>