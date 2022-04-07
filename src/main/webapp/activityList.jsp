<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/includes/taglib.jspf" %>

<html lang="${sessionScope.lang}">
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <link rel="stylesheet" type="text/css" href="style.css">
    <title><fmt:message key="activities"/> </title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>

<div class="container">

    <div class="row">
        <div class="col-sm-3 text-center text-secondary"><h4><fmt:message key="sbc"/></h4></div>
        <div class="col-sm-4 text-center text-secondary"><h4><fmt:message key="sbn1"/></h4></div>
        <div class="col-sm-6 text-center text-secondary"></div>
    </div>

    <form method="get" action="controller">

        <div class="row">
            <%--CATEGORY FILTRATION--%>
            <div class="col-sm-3 text-center">
                <h6><fmt:message key="chooseCategory"/></h6>
                <select class="form-select" name="category" style="width: 70%; display: inline">
                    <option value="ALL"><fmt:message key="choose"/></option>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category}"><c:out value="${category}"/></option>
                    </c:forEach>
                </select>
            </div>

            <%--SEARCH BY NAME--%>
            <div class="col-sm-4 text-center">

                <h6><fmt:message key="ean"/></h6>

                <input type="text" placeholder="<fmt:message key="name2"/>" name="name">

            </div>

            <div class="col-sm-4 text-center">
                <input type="hidden" name="command" value="globalActivityFilter">
                <button type="submit" class="btn btn-sm btn-primary me-2"
                        value="Search"><fmt:message key="search"/>
                </button>
                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                    <button class="btn-modal btn-sm btn-primary me-2 btn" type="button">
                        <fmt:message key="ana_btn"/>
                    </button>
                </c:if>
            </div>
        </div>
    </form>
    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
        <div class="modal-overlay">
            <div class="modal-content text-center" style="user-select: none">
                <span class="close">&times;</span>
                <form method="post" action="controller">
                    <h1 class="h3 mt-4 mb-3 font-weight-normal"><fmt:message key="pfes"/></h1>
                    <input name="name" type="name" id="name" class="form-control "
                           placeholder="<fmt:message key="name"/>" required>
                    <br>
                    <c:if test="${fn:length(categoryList) != 0}">
                        <input class="form-check-input" type="checkbox" id="CheckCategory" onclick="checkCategory()">
                        <label class="form-check-label" for="CheckCategory" id="CheckCategoryLabel"><fmt:message
                                key="cec"/></label>
                    </c:if>
                    <br id="br">
                    <input name="category" type="category" id="category" class="form-control "
                           placeholder="<fmt:message key="category"/> "
                           required>
                    <br>
                    <input type="hidden" name="command" value="createActivity">
                    <input type="submit" class="btn btn-lg btn-primary btn-block" value="<fmt:message key="create"/> ">
                </form>
            </div>
        </div>
    </c:if>
</div>
<div class="container-fluid">
    <c:choose>
        <c:when test="${fn:length(activityList) == 0}">
            <div class="alert alert-warning" role="alert">
                <fmt:message key="nsa"/>
            </div>
        </c:when>
        <c:otherwise>
            <table id="example" class="table table-striped text-center">
                <caption class="text-center"><fmt:message key="found_a"/></caption>

                <thead class="thead-light">
                <tr>
                    <th scope="col"><fmt:message key="name"/></th>
                    <th scope="col"><fmt:message key="category"/></th>
                    <th scope="col"><fmt:message key="action"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="activity" items="${activityList}">
                    <tr>
                        <form method="post" action="controller">
                            <td style="width: 35%">
                                <c:out value="${activity.name}"/>
                            </td>
                            <td style="width: 35%">
                                <c:out value="${activity.category}"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                                        <input name="id" type="hidden" value="${activity.id}">
                                        <input name="name" type="hidden" value="${activity.name}">
                                        <input name="category" type="hidden" value="${activity.category}">
                                        <input type="hidden" name="command" value="deleteActivity">
                                        <button type="submit" class="btn btn-sm btn-danger" value="Delete">
                                            <fmt:message key="delete"/>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${activity.state == 'UNFOLLOWED'}">
                                            <input name="activity_id" type="hidden" value="${activity.id}">
                                            <input name="user_id" type="hidden" value="${sessionScope.user.id}">
                                            <input type="hidden" name="command" value="followRequestActivity">
                                            <button type="submit" class="btn btn-sm btn-primary "
                                                    value="Follow">
                                                <fmt:message key="follow"/>
                                            </button>
                                        </c:if>
                                        <c:if test="${activity.state == 'FOLLOWED'}">
                                            <input name="activity_id" type="hidden" value="${activity.id}">
                                            <input name="user_id" type="hidden" value="${sessionScope.user.id}">
                                            <input type="hidden" name="command" value="unfollowActivity">
                                            <button type="submit" class="btn btn-sm btn-outline-primary "
                                                    value="Unfollow">
                                                <fmt:message key="unfollow"/>
                                            </button>
                                        </c:if>
                                        <c:if test="${activity.state == 'WAITING'}">
                                            <div class="alert alert-secondary" role="alert">
                                                <fmt:message key="request_sent"/>
                                            </div>
                                            <input name="activity_id" type="hidden" value="${activity.id}">
                                            <input name="user_id" type="hidden" value="${sessionScope.user.id}">
                                            <input type="hidden" name="command" value="unfollowActivity">
                                            <button type="submit" class="btn btn-sm btn-outline-primary "
                                                    value="Unfollow">
                                                <fmt:message key="unfollow"/>
                                            </button>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
<c:if test="${sessionScope.error ne null}">
    <div class="error-modal">
        <div class="error-modal-overlay">
            <div class="error-modal-content">
                <span class="error-close close">&times;</span>
                <div class="alert alert-danger" role="alert">
                        ${sessionScope.error}
                </div>
            </div>
        </div>
    </div>
</c:if>

<script>

    const btn = document.querySelector('.btn-modal');
    const modal_overlay = document.querySelector('.modal-overlay');
    const close = document.querySelector('.close');
    const modal_content = document.querySelector('.modal-content');

    btn.addEventListener('click', () => {
        modal_content.classList.add('modal--visible');
        modal_overlay.classList.add('modal-overlay--visible');
    })
    modal_overlay.addEventListener('click', (e) => {
        if (e.target == modal_overlay) {
            modal_overlay.classList.remove('modal-overlay--visible');
        }
    })
    modal_content.addEventListener('click', (e) => {
        if (e.target == close) {
            modal_overlay.classList.remove('modal-overlay--visible');
        }
    })

    const checkBox = document.getElementById('CheckCategory');
    const category = document.getElementById('category');
    const labelSelect = document.getElementById('CheckCategoryLabel');

    function checkCategory() {
        let element = '<select class="form-select" name="category" style="width: 70%; display: inline; margin-bottom: 5px" ' +
            'id="selectCategory">' +
            '<c:forEach var="category" items="${categoryList}">' +
            '<option value="${category}"><c:out value="${category}"/></option>' +
            ' </c:forEach>' +
            '</select>';
        if (checkBox.checked == true) {
            document.getElementById('category').remove();
            document.getElementById('br').insertAdjacentHTML('afterend', element);
        } else {
            console.log("unchecked")
            document.getElementById('selectCategory').remove();
            document.getElementById('br').insertAdjacentHTML('afterend', category.outerHTML);
        }
    }

    const error_modal = document.querySelector('.error-modal-content')
    const error_modal_overlay = document.querySelector('.error-modal-overlay')
    const error_close = document.querySelector('.error-close');


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