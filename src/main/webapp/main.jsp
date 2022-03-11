<%@include file="WEB-INF/includes/taglib.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="WEB-INF/includes/header-links.jsp"%>
    <title>Welcome</title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp"%>

<h3>Welcome</h3>
<hr/>
${sessionScope.user.role} ${sessionScope.user.name}, hello!
<hr/>
<br>
<%@include file="WEB-INF/includes/footer-links.jsp"%>
</body>
</html>
