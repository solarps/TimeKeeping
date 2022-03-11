<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="WEB-INF/includes/taglib.jspf"%>
<html>
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <title>Registration</title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>
<form action="controller" method="post">
    <input type="hidden" name="command" value="register">
    <section class="vh-100" style="background-color: #eee;">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-lg-12 col-xl-11">
                    <div class="card text-black" style="border-radius: 25px;">
                        <div class="card-body p-md-5">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                                    <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Registration</p>

                                    <form class="mx-1 mx-md-4">

                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <em class="fas fa-user fa-lg me-3 fa-fw"></em>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="text" name="name" class="form-control" placeholder="Name"/>
                                            </div>
                                        </div>

                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <em class="fas fa-user fa-lg me-3 fa-fw"></em>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="text" name="login" class="form-control"
                                                       placeholder="Login"/>
                                            </div>
                                        </div>

                                        <%--<div class="d-flex flex-row align-items-center mb-4">
                                            <em class="fas fa-envelope fa-lg me-3 fa-fw"></em>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="email" name="email" class="form-control"
                                                       placeholder="Email"/>
                                            </div>
                                        </div>--%>

                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <em class="fas fa-lock fa-lg me-3 fa-fw"></em>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="password" name="password" class="form-control"
                                                       placeholder="Password"/>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <%--<div class="col-md-6 mb-4">
                                                <em class="fas fa-lock fa-lg me-3 fa-fw"></em>
                                                <div class="form-outline flex-fill mb-0">
                                                    <select class="select" name="role">
                                                        <option value="1" disabled>Role</option>
                                                        <option value="4">client</option>
                                                        <option value="3">master</option>
                                                        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                                            <option value="2">manager</option>
                                                        </c:if>
                                                    </select>
                                                </div>
                                            </div>--%>

                                            <div class="col-md-6  mb-4">
                                                <em class="fas fa-lock fa-lg me-3 fa-fw"></em>
                                                <div class="form-outline flex-fill mb-0">
                                                    <input type="submit" class="btn btn-lg btn-primary btn-block"
                                                           value="Register">
                                                </div>
                                            </div>

                                            <div class="d-flex flex-row align-items-center mb-4">
                                                <em class="fas fa-lock fa-lg me-3 fa-fw"></em>
                                                <div class="form-outline flex-fill mb-0">
                                                    <br><h4 class="text-danger">${sessionScope.error}</h4>
                                                </div>
                                            </div>
                                        </div>
                                    </form>

                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</form>

<%@include file="WEB-INF/includes/footer-links.jsp" %>
</body>
</html>