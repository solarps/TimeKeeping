<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/includes/taglib.jspf" %>

<html>
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <title>Error</title>
</head>
<body>
<h2 class="error">
    The following error occurred
</h2>

<%-- this way we get the error information (error 404)--%>
<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
<c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>

<%-- this way we get the exception --%>
<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

<c:if test="${not empty code}">
    <h3>Error code: ${code}</h3>
</c:if>

<c:if test="${not empty message}">
    <h3>Message: ${message}</h3>
</c:if>

<%-- if get this page using forward --%>
<c:if test="${not empty errorMessage and empty exception and empty code}">
    <h3>Error message: ${errorMessage}</h3>
</c:if>

<%@include file="WEB-INF/includes/footer-links.jsp" %>
</body>
</html>
