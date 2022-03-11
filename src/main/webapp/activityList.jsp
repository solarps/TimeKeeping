<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/includes/taglib.jspf" %>
<html>
<head>
    <%@include file="WEB-INF/includes/header-links.jsp" %>
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Activity</title>
</head>
<body>
<%@include file="WEB-INF/includes/nav-bar.jsp" %>

<div class="container">

    <div class="row">
        <div class="col-sm-3 text-center text-secondary"><h4>Search by category</h4></div>
        <div class="col-sm-4 text-center text-secondary"><h4>Search by name</h4></div>
        <div class="col-sm-6 text-center text-secondary"></div>
    </div>

    <form method="get" action="controller">

        <div class="row">
            <%--CATEGORY FILTRATION--%>
            <div class="col-sm-3 text-center">
                <h6>Choose category</h6>
                <select class="form-select" name="category" style="width: 70%; display: inline">
                    <option value="ALL">Choose...</option>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category}"><c:out value="${category}"></c:out></option>
                    </c:forEach>
                </select>
            </div>

            <%--SEARCH BY NAME--%>
            <div class="col-sm-4 text-center">

                <h6>Enter activity name</h6>

                <input type="text" placeholder="name" name="name">

            </div>

            <div class="col-sm-4 text-center">
                <input type="hidden" name="command" value="globalActivityFilter">
                <button type="submit" class="btn btn-sm btn-primary btn-block me-2"
                        value="Search">Search
                </button>
            </div>
        </div>
    </form>
    <button class="btn-modal btn-sm btn-primary me-2 btn btn-block" type="button">
        Add new activity
    </button>
    <div class="modal-overlay">
        <div class="modal-content text-center" style="user-select: none">
            <span class="close">&times;</span>
            <form method="post" action="controller">
                <h1 class="h3 mt-4 mb-3 font-weight-normal">Please fill empty spaces</h1>
                <input name="name" type="name" id="name" class="form-control " placeholder="Name" required>
                <br>
                <input class="form-check-input" type="checkbox" id="CheckCategory" onclick="checkCategory()">
                <label class="form-check-label" for="CheckCategory" id="CheckCategoryLabel">Create
                    new category</label>
                <br id="br">
                <select class="form-select" name="category" style="width: 70%; display: inline; margin-bottom: 5px"
                        id="selectCategory">
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category}"><c:out value="${category}"></c:out></option>
                    </c:forEach>
                </select>
                <br>
                <input type="hidden" name="command" value="createActivity">
                <input type="submit" class="btn btn-lg btn-primary btn-block" value="Create">
            </form>
        </div>
    </div>
</div>
<div class="container-fluid">
    <c:choose>
        <c:when test="${fn:length(activityList) == 0}"><h4 class="bg-warning">No such activities</h4></c:when>

        <c:otherwise>
            <table class="table text-center">
                <caption class="text-center">found activities</caption>

                <thead>
                <tr>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="activity" items="${activityList}">
                    <tr>
                        <td>
                            <c:out value="${activity.name}"></c:out>
                        </td>
                        <td>
                            <c:out value="${activity.category}"></c:out>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                                    <button class="btn btn-sm btn-danger btn-block">Delete</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-sm btn-primary btn-block">Follow</button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
    <br><h4 class="text-danger">${sessionScope.error}</h4>
</div>
<script>
    const btn = document.querySelector('.btn-modal');
    const modal_overlay = document.querySelector('.modal-overlay');
    const close = document.querySelector('.close');
    const modal_content = document.querySelector('.modal-content');


    btn.addEventListener('click', () => {
        document.querySelector('.modal-content').classList.add('modal--visible');
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
    const selectBar = document.getElementById('selectCategory');
    const labelSelect = document.getElementById('CheckCategoryLabel');

    function checkCategory() {
        let element = '<input name="category" type="category" id="category" class="form-control " placeholder="Category" required>';
        if (checkBox.checked == true) {
            document.getElementById('selectCategory').remove();
            document.getElementById('br').insertAdjacentHTML('afterend', element);
        } else {
            console.log("unchecked")
            document.getElementById('category').remove();
            document.getElementById('br').insertAdjacentHTML('afterend', selectBar.outerHTML);
        }
    }

</script>
<%--<%@include file="WEB-INF/includes/footer-links.jsp" %>--%>
</body>
</html>