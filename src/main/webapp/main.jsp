<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="WEB-INF/includes/taglib.jspf" %>
<html lang="${sessionScope.lang}">
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <title><fmt:message key="welcome"/></title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>

<h3><fmt:message key="welcome"/></h3>
<hr/>
${sessionScope.user.role} ${sessionScope.user.name}, <fmt:message key="hello"/>!
<hr/>
<br>
<%@include file="WEB-INF/includes/footer-links.jsp" %>
</body>
</html>
