<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="WEB-INF/includes/taglib.jspf" %>
<html lang="${sessionScope.lang}">
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <title><fmt:message key="reg"/></title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>
<div class="text-center" style="max-width: 300px; margin: auto">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="register">

        <h1 class="h3 mt-4 mb-3 font-weight-normal"><fmt:message
                key="reg"/></h1>
        <input name="name" type="text" class="form-control" placeholder="<fmt:message key="name"/>"
               required><br>
        <input type="text" name="login" class="form-control"
               placeholder="<fmt:message key="login"/>" required/><br>
        <input name="password" type="password" id="password" class="form-control"
               placeholder="<fmt:message key="password" />" required><br>
        <input type="submit" class="btn btn-lg btn-primary" value="<fmt:message key="register"/>">
        <br><h6 class="text-danger">${sessionScope.error}</h6>
    </form>
</div>


<%@include file="WEB-INF/includes/footer-links.jsp" %>
</body>
</html>