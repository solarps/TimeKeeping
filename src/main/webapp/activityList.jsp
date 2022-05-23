<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/includes/taglib.jspf" %>

<html lang="${sessionScope.lang}">
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <link rel="stylesheet" type="text/css" href="style.css">
    <title><fmt:message key="activities"/></title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>

<div class="container">

    <div class="row">
        <c:if test="${sessionScope.user.role eq 'USER'}">
            <div class="col-sm-3 text-center text-secondary"><h4><fmt:message key="sbs"/></h4></div>
        </c:if>
        <div class="col-sm-3 text-center text-secondary"><h4><fmt:message key="sbc"/></h4></div>
        <div class="col-sm-4 text-center text-secondary"><h4><fmt:message key="sbn1"/></h4></div>
        <div class="col-sm-6 text-center text-secondary"></div>
    </div>

    <form method="get" action="controller">

        <div class="row">

            <%--STATE FILTRATION FOR USER--%>
            <c:if test="${sessionScope.user.role eq 'USER'}">
                <div class="col-sm-3">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="FOLLOWED" id="followCheck"
                               name="state">
                        <label class="form-check-label" for="followCheck"><fmt:message key="following"/> </label>
                    </div>

                    <div class="form-check text-left">
                        <input class="form-check-input" type="checkbox" value="UNFOLLOWED" id="unfollowCheck"
                               name="state">
                        <label class="form-check-label" for="unfollowCheck"><fmt:message key="not_follow"/> </label>
                    </div>

                    <div class="form-check text-left">
                        <input class="form-check-input" type="checkbox" value="WAITING" id="requestCheck"
                               name="state">
                        <label class="form-check-label" for="requestCheck"><fmt:message key="request"/> </label>
                    </div>
                </div>

            </c:if>

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
            <c:choose>
                <c:when test="${sessionScope.user.role eq 'ADMIN'}">
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
                </c:when>
                <c:otherwise>
                    <div class="col-sm-2 text-center">
                        <input type="hidden" name="command" value="globalActivityFilter">
                        <button type="submit" class="btn btn-sm btn-primary me-2"
                                value="Search"><fmt:message key="search"/>
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </form>
    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
        <div class="modal-overlay">
            <div class="modal-content text-center" style="width:380px; user-select: none">
                <span class="close">&times;</span>
                <div class="conteiner">
                    <form method="post" action="controller">
                        <h1 class="h3 mt-4 mb-3 font-weight-normal"><fmt:message key="pfes"/></h1>
                        <input name="name" id="name" class="form-control "
                               placeholder="<fmt:message key="name"/>" required>
                        <br>
                        <c:if test="${fn:length(categoryList) != 0}">
                            <input class="form-check-input" type="checkbox" id="CheckCategory"
                                   onclick="checkCategory()">
                            <label class="form-check-label" for="CheckCategory" id="CheckCategoryLabel"><fmt:message
                                    key="cec"/></label>
                        </c:if>
                        <br id="br">
                        <input name="category" id="category" class="form-control "
                               placeholder="<fmt:message key="category"/> "
                               required>
                        <br>
                        <input type="hidden" name="command" value="createActivity">
                        <input type="submit" class="btn btn-lg btn-primary btn-block"
                               value="<fmt:message key="create"/> ">
                    </form>
                </div>
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
                    <c:if test="${sessionScope.user.role ne 'ADMIN'}">
                        <th scope="col"><fmt:message key="spent_time"/></th>
                    </c:if>
                    <th scope="col"><fmt:message key="action"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="activity" items="${activityList}">
                    <tr>
                        <td>
                            <c:out value="${activity.name}"/>
                        </td>
                        <td style="width: 35%">
                            <c:out value="${activity.category}"/>
                        </td>
                        <c:if test="${sessionScope.user.role ne 'ADMIN'}">
                            <td>
                                <c:out value="${activity.spentTime.time}"/>
                            </td>
                        </c:if>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                                    <form method="post" action="controller">
                                        <input name="id" type="hidden" value="${activity.id}">
                                        <input name="name" type="hidden" value="${activity.name}">
                                        <input name="category" type="hidden" value="${activity.category}">
                                        <input type="hidden" name="command" value="deleteActivity">
                                        <button type="submit" class="btn btn-sm btn-danger" value="Delete">
                                            <fmt:message key="delete"/>
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${activity.state == 'UNFOLLOWED'}">
                                        <form method="post" action="controller">
                                            <input name="activity_id" type="hidden" value="${activity.id}">
                                            <input name="user_id" type="hidden" value="${sessionScope.user.id}">
                                            <input type="hidden" name="command" value="followRequestActivity">
                                            <button type="submit" class="btn btn-sm btn-primary "
                                                    value="Follow">
                                                <fmt:message key="follow"/>
                                            </button>
                                        </form>
                                    </c:if>
                                    <c:if test="${activity.state == 'FOLLOWED'}">
                                        <button class="btn-modal btn-sm btn-primary me-2 btn" id="${activity.id}"
                                                style="border-radius: 5px">
                                            <fmt:message key="set_time"/>
                                        </button>
                                        <div class="modal-overlay" id="${activity.id}">
                                            <div class="modal-content text-center" id="${activity.id}"
                                                 style="width:380px; user-select: none">
                                                <span class="close">&times;</span>
                                                <div class="conteiner">
                                                    <form method="post" action="controller">
                                                        <div style="margin: 10px">
                                                            <input name="hours" type="number"
                                                                   style="width:60px;text-align: right" size="40"
                                                                   min="0"
                                                                   max="9999"
                                                                   value="${activity.spentTime.hours}" required>
                                                            :
                                                            <input name="minutes" type="number"
                                                                   style="width:40px;text-align: right" size="40"
                                                                   min="0"
                                                                   max="59" value="${activity.spentTime.minutes}"
                                                                   required>
                                                            :
                                                            <input name="seconds" type="number"
                                                                   style="width:40px;text-align: right" size="40"
                                                                   min="0"
                                                                   max="59" value="${activity.spentTime.seconds}"
                                                                   required>
                                                        </div>
                                                        <input type="hidden" name="activity_id" value="${activity.id}">
                                                        <input type="hidden" name="command" value="setSpentTime">
                                                        <input type="submit" class="btn btn-lg btn-primary btn-block"
                                                               value="<fmt:message key="set"/>">
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <form method="post" action="controller">
                                            <input name="activity_id" type="hidden" value="${activity.id}">
                                            <input name="user_id" type="hidden" value="${sessionScope.user.id}">
                                            <input type="hidden" name="command" value="unfollowActivity">
                                            <button type="submit" class="btn btn-sm btn-outline-primary "
                                                    value="Unfollow">
                                                <fmt:message key="unfollow"/>
                                            </button>
                                        </form>
                                    </c:if>
                                    <c:if test="${activity.state == 'WAITING'}">
                                        <form method="post" action="controller">
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
                                        </form
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
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

    const modals = document.querySelectorAll('.modal-content');
    const buttons = document.querySelectorAll('.btn-modal');
    const modal_overlays = document.querySelectorAll('.modal-overlay');
    const closes = document.querySelectorAll('.close');

    buttons.forEach(button => button.addEventListener('click', () => {
        modals.forEach(modal => {
            if (modal.id == button.id) {
                modal_overlays.forEach(overlay => {
                    if (overlay.id == modal.id) {
                        modal.classList.add('modal--visible');
                        overlay.classList.add('modal-overlay--visible');
                        closes.forEach(c => {
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