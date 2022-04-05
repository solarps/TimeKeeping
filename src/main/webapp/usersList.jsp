<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/includes/taglib.jspf" %>
<html>
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Users</title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>

<div class="container">

    <div class="row">
        <div class="col-sm-4 text-center text-secondary"><h4>Filtration</h4></div>
        <div class="col-sm-3 text-center text-secondary"><h4>Search by name</h4></div>
        <div class="col-sm-3 text-center text-secondary"><h4>Search by login</h4></div>
    </div>

    <form method="get" action="controller">

        <div class="row">
            <%--ROLE FILTRATION--%>
            <div class="col-sm-2">
                <h6>Role</h6>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="ADMIN" id="adminCheck"
                           name="role">
                    <label class="form-check-label" for="adminCheck">ADMIN</label>
                </div>

                <div class="form-check text-left">
                    <input class="form-check-input" type="checkbox" value="USER" id="userCheck"
                           name="role">
                    <label class="form-check-label" for="userCheck">User</label>
                </div>
            </div>
            <div class="col-sm-2">
                <h6>Sorting</h6>
                <select class="form-select" id="sort" required name="sort">
                    <option value="ROLE">By role</option>
                    <option value="NAME">By name</option>
                    <option value="LOGIN">By login</option>
                </select>
            </div>

            <%--SEARCH BY NAME--%>
            <div class="col-sm-3 text-center">

                <h6>Enter user name</h6>

                <input type="text" placeholder="name" name="name">

            </div>

            <%--SEARCH BY LOGIN--%>
            <div class="col-sm-3 text-center">
                <h6>Enter user login</h6>

                <input type="text" placeholder="login" name="login">

            </div>
            <div class="col-sm-2 text-center">
                <input type="hidden" name="command" value="globalUsersFilter">
                <input type="submit" class="btn btn-lg btn-primary me-2"
                       value="Search">
            </div>
        </div>
    </form>
</div>

<div class="container-fluid">
    <c:choose>
        <c:when test="${fn:length(userList) == 0}"><h4 class="bg-warning">No such users</h4></c:when>

        <c:otherwise>
            <table class="table table-striped text-center">
                <caption class="text-center">found users</caption>

                <thead class="thead-light">
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Login</th>
                    <th scope="col">Role</th>
                    <th scope="col">Activity</th>
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
                                        Activity
                                    </button>
                                    <div class="modal-overlay" id="${user.id}">
                                        <div class="modal-content" id="${user.id}">
                                            <span class="close">&times;</span>
                                            <table class="table text-center">

                                                <thead>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>Category</th>
                                                    <th>Action</th>
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
                                                                    <input name="activity_id" type="hidden" value="${activity.id}">
                                                                    <input name="user_id" type="hidden" value="${user.id}">
                                                                    <input type="hidden" name="command" value="confirmActivity">
                                                                    <input type="submit" class="btn btn-lg btn-primary me-2"
                                                                           value="Confirm">
                                                                </form>
                                                                <form method="post" action="controller">
                                                                    <input name="activity_id" type="hidden" value="${activity.id}">
                                                                    <input name="user_id" type="hidden" value="${user.id}">
                                                                    <input type="hidden" name="command" value="refuseActivity">
                                                                    <input type="submit" class="btn btn-lg btn-primary me-2"
                                                                           value="Refuse">
                                                                </form>
                                                            </c:if>
                                                            <c:if test="${activity.state == 'FOLLOWED'}">
                                                                <form method="post" action="controller">
                                                                    <input name="activity_id" type="hidden" value="${activity.id}">
                                                                    <input name="user_id" type="hidden" value="${user.id}">
                                                                    <input type="hidden" name="command" value="refuseActivity">
                                                                    <button type="submit" class="btn btn-sm btn-danger" value="Delete">
                                                                        Delete
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
                                <td>No such activities</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
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
</script>
<%--<%@include file="WEB-INF/includes/footer-links.jsp" %>--%>
</body>
</html>
