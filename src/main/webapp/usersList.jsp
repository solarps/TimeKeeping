<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/includes/taglib.jspf" %>
<html lang="${sessionScope.lang}">
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <link rel="stylesheet" type="text/css" href="style.css">
    <title><fmt:message key="users"/></title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>

<div class="container">

    <div class="row">
        <div class="col-sm-4 text-center text-secondary"><h4><fmt:message key="filter"/></h4></div>
        <div class="col-sm-3 text-center text-secondary"><h4><fmt:message key="sbn2"/></h4></div>
        <div class="col-sm-3 text-center text-secondary"><h4><fmt:message key="sbl"/></h4></div>
    </div>

    <form method="get" action="controller">

        <div class="row">
            <%--ROLE FILTRATION--%>
            <div class="col-sm-2">
                <h6><fmt:message key="role"/></h6>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="ADMIN" id="adminCheck"
                           name="role">
                    <label class="form-check-label" for="adminCheck"><fmt:message key="admin"/> </label>
                </div>

                <div class="form-check text-left">
                    <input class="form-check-input" type="checkbox" value="USER" id="userCheck"
                           name="role">
                    <label class="form-check-label" for="userCheck"><fmt:message key="user"/> </label>
                </div>
            </div>
            <div class="col-sm-2">
                <h6>Sorting</h6>
                <select class="form-select" id="sort" required name="sort">
                    <option value="ROLE"><fmt:message key="br"/></option>
                    <option value="NAME"><fmt:message key="bn"/></option>
                    <option value="LOGIN"><fmt:message key="bl"/></option>
                </select>
            </div>

            <%--SEARCH BY NAME--%>
            <div class="col-sm-3 text-center">

                <h6><fmt:message key="eun"/></h6>

                <input type="text" placeholder="<fmt:message key="name"/> " name="name">

            </div>

            <%--SEARCH BY LOGIN--%>
            <div class="col-sm-3 text-center">
                <h6><fmt:message key="eul"/></h6>

                <input type="text" placeholder="<fmt:message key="login"/>" name="login">

            </div>
            <div class="col-sm-2 text-center">
                <input type="hidden" name="command" value="globalUsersFilter">
                <input type="submit" class="btn btn-lg btn-primary me-2"
                       value="<fmt:message key="search"/> ">
            </div>
        </div>
    </form>
</div>

<div class="container-fluid">
    <c:choose>
        <c:when test="${fn:length(userList) == 0}"><h4 class="bg-warning">No such users</h4></c:when>

        <c:otherwise>
            <table class="table table-striped text-center">
                <caption class="text-center"><fmt:message key="found_u"/></caption>

                <thead class="thead-light">
                <tr>
                    <th scope="col"><fmt:message key="name"/></th>
                    <th scope="col"><fmt:message key="login"/></th>
                    <th scope="col"><fmt:message key="role"/></th>
                    <th scope="col"><fmt:message key="activities"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>
                            <c:out value="${user.name}"></c:out>
                        </td>
                        <td>
                            <c:out value="${user.login}"></c:out>
                        </td>
                        <td>
                            <c:out value="${user.role}"></c:out>
                        </td>
                        <c:choose>
                            <c:when test="${user.role ne 'ADMIN' and fn:length(user.activities)!=0}">
                                <td>

                                    <button class="btn-modal btn-sm btn-primary me-2 btn" id="${user.id}"
                                            style="border-radius: 5px">
                                        <fmt:message key="activities"/>
                                    </button>
                                    <div class="modal-overlay" id="${user.id}">
                                        <div class="modal-content" id="${user.id}">
                                            <span class="close">&times;</span>
                                            <table class="table text-center">

                                                <thead>
                                                <tr>
                                                    <th><fmt:message key="name2"/></th>
                                                    <th><fmt:message key="category"/></th>
                                                    <th><fmt:message key="action"/></th>
                                                </tr>
                                                </thead>

                                                <tbody>
                                                <c:forEach var="activity" items="${user.activities}">
                                                    <tr>
                                                        <td>
                                                            <c:out value="${activity.name}"></c:out>
                                                        </td>
                                                        <td>
                                                            <c:out value="${activity.category}"></c:out>
                                                        </td>
                                                        <td>
                                                            <c:if test="${activity.state == 'WAITING'}">
                                                                <form method="post" action="controller">
                                                                    <input name="activity_id" type="hidden"
                                                                           value="${activity.id}">
                                                                    <input name="user_id" type="hidden"
                                                                           value="${user.id}">
                                                                    <input type="hidden" name="command"
                                                                           value="confirmActivity">
                                                                    <input type="submit"
                                                                           class="btn btn-lg btn-primary me-2"
                                                                           value="Confirm"><fmt:message key="confirm"/>
                                                                </form>
                                                                <form method="post" action="controller">
                                                                    <input name="activity_id" type="hidden"
                                                                           value="${activity.id}">
                                                                    <input name="user_id" type="hidden"
                                                                           value="${user.id}">
                                                                    <input type="hidden" name="command"
                                                                           value="refuseActivity">
                                                                    <input type="submit"
                                                                           class="btn btn-lg btn-primary me-2"
                                                                           value="Refuse"><fmt:message key="refuse"/>
                                                                </form>
                                                            </c:if>
                                                            <c:if test="${activity.state == 'FOLLOWED'}">
                                                                <form method="post" action="controller">
                                                                    <input name="activity_id" type="hidden"
                                                                           value="${activity.id}">
                                                                    <input name="user_id" type="hidden"
                                                                           value="${user.id}">
                                                                    <input type="hidden" name="command"
                                                                           value="refuseActivity">
                                                                    <button type="submit" class="btn btn-sm btn-danger"
                                                                            value="Delete"><fmt:message key="delete"/>
                                                                    </button>
                                                                </form>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </td>

                            </c:when>
                            <c:otherwise>
                                <td><fmt:message key="na"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
<c:if test="${requestScope.error != null}">
    <div class="error-modal">
        <div class="error-modal-overlay">
            <div class="error-modal-content">
                <span class="error_close close">&times;</span>
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

                    <%-- this way we print exception stack trace --%>
                <c:if test="${not empty exception}">
                    <hr/>
                    <h3>Stack trace:</h3>
                    <c:forEach var="stackTraceElement" items="${exception.stackTrace}">
                        ${stackTraceElement}
                    </c:forEach>
                </c:if>

            </div>
        </div>
    </div>
</c:if>
<script>
    const modals = document.querySelectorAll('.modal-content');
    const buttons = document.querySelectorAll('.btn-modal');
    const modal_overlays = document.querySelectorAll('.modal-overlay');
    const close = document.querySelectorAll('.close');

    buttons.forEach(button => button.addEventListener('click', () => {
        modals.forEach(modal => {
            if (modal.id == button.id) {
                modal_overlays.forEach(overlay => {
                    if (overlay.id == modal.id) {
                        modal.classList.add('modal--visible');
                        overlay.classList.add('modal-overlay--visible');
                        close.forEach(c => {
                            overlay.addEventListener('click', (e) => {
                                if (e.target == overlay || e.target == c) {
                                    overlay.classList.remove('modal-overlay--visible');
                                }
                            })
                        })
                    }
                })
            }
        })
    }))

    const error_modal = document.querySelector('.error-modal-content')
    const error_modal_overlay = document.querySelector('.error-modal-overlay')
    const error_close = document.querySelector('.error_close');


    document.querySelector('.error-modal-content').classList.add('modal--visible');
    error_modal_overlay.classList.add('modal-overlay--visible');


    error_modal_overlay.addEventListener('click', (e) => {
        if (e.target == error_modal_overlay || e.target == error_close) {
            error_modal_overlay.classList.remove('modal-overlay--visible');
        }
    })

</script>
<%--<%@include file="WEB-INF/includes/footer-links.jsp" %>--%>
</body>
</html>
