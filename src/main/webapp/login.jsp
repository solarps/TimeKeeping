<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/includes/taglib.jspf" %>

<html lang="${sessionScope.lang}">
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <title><fmt:message key="login"/></title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>

<div class="text-center">
    <form action="controller" method="post" style="max-width: 300px; margin: auto">
        <input type="hidden" name="command" value="login">
        <h1 class="h3 mt-4 mb-3 font-weight-normal"><fmt:message key="please_sing_in"/></h1>
        <input name="login" type="login" id="login" class="form-control " placeholder="<fmt:message key="login" />"
               required><br>
        <input name="password" type="password" id="password" class="form-control"
               placeholder="<fmt:message key="password" />" required><br>
        <input type="submit" class="btn btn-lg btn-primary" value="<fmt:message key="log_in" />">
        <br><h6 class="text-danger">${sessionScope.error}</h6>
    </form>
</div>
<%@include file="WEB-INF/includes/footer-links.jsp" %>
</body>
</html>