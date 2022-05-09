<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
    <a href="/" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
        <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
            <use xlink:href="#bootstrap"></use>
        </svg>
    </a>


    <nav class="navbar navbar-expand-lg fixed-top navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li><a href="index.jsp" class="nav-link active"><fmt:message key="home"/></a></li>
                <li><a class="nav-link"
                       href="${pageContext.request.contextPath}/controller?command=getAllActivity"><fmt:message
                        key="activities"/></a>
                </li>
                    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                        <li><a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=getAllUsers"><fmt:message
                                key="users"/></a>
                        </li>
                    </c:if>
                <li><a href="account.jsp" class="nav-link"><fmt:message key="account"/></a></li>
            </ul>
        </div>

        <ul class="navbar-nav">
            <li><a href="${pageContext.request.contextPath}?sessionLocale=en"><img
                    alt="<fmt:message key="lang.en"/>"
                    src="flags/GB.png" width="40px" height="30px" style="margin: 5px"></a>
            </li>
            <li><a href="${pageContext.request.contextPath}?sessionLocale=ua"><img
                    alt="<fmt:message key="lang.ua"/>"
                    src="flags/UA.png" width="40px" height="30px" style="margin: 5px"></a>
            </li>
        </ul>

        <div class="text-end ">

            <c:choose>
                <c:when test="${sessionScope.user eq null}">
                    <a href="login.jsp" class="signin">
                        <button type="button" class="btn btn-outline-primary me-2"><fmt:message key="sign_in"/></button>
                    </a>
                    <a href="register.jsp" class="signup">
                        <button type="button" class="btn btn-primary"><fmt:message key="sign_up"/></button>
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="signout">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="logout">
                            <button type="submit" class="btn btn-outline-primary me-2"><fmt:message
                                    key="sign_out"/></button>
                        </form>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
</header>