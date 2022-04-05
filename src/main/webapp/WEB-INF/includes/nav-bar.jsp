<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
    <a href="/" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
        <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
            <use xlink:href="#bootstrap"></use>
        </svg>
    </a>


    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${sessionScope.user eq null}">
                        <li><a href="index.jsp" class="nav-link active">Home</a></li>
                        <li><a href="login.jsp" class="nav-link">Activity</a></li>
                        <li><a href="login.jsp" class="nav-link">Account</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="main.jsp" class="nav-link active">Home</a></li>
                        <li><a class="nav-link"
                               href="${pageContext.request.contextPath}/controller?command=getAllActivity">Activities</a>
                        </li>
                        <c:choose>
                            <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                                <li><a class="nav-link"
                                       href="${pageContext.request.contextPath}/controller?command=getAllUsers">Users</a>
                                </li>

                            </c:when>
                            <c:otherwise>

                            </c:otherwise>

                        </c:choose>
                        <li><a href="account.jsp" class="nav-link">Account</a></li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </nav>


    <%-- <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
         <c:choose>
             <c:when test="${sessionScope.user eq null}">
                 <li><a href="index.jsp" class="nav-link px-2 link-secondary">Home</a></li>
                 <li><a href="login.jsp" class="nav-link px-2 link-dark">Activity</a></li>
                 <li><a href="login.jsp" class="nav-link px-2 link-dark">Account</a></li>
                 <li><a href="#" class="nav-link px-2 link-dark">FAQs</a></li>
                 <li><a href="#" class="nav-link px-2 link-dark">About</a></li>
             </c:when>
             <c:otherwise>
                 <li><a href="main.jsp" class="nav-link px-2 link-secondary">Home</a></li>
                 <li><a class="nav-link px-2 link-dark"
                        href="${pageContext.request.contextPath}/controller?command=getAllActivity">Activities</a>
                 </li>
                 <c:choose>
                     <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                         <li><a class="nav-link px-2 link-dark"
                                href="${pageContext.request.contextPath}/controller?command=getAllUsers">Users</a>
                         </li>

                     </c:when>
                     <c:otherwise>

                     </c:otherwise>

                 </c:choose>
                 <li><a href="account.jsp" class="nav-link px-2 link-dark">Account</a></li>
             </c:otherwise>
         </c:choose>
     </ul>
 --%>
    <div class="col-md-3 text-end">
        <c:choose>
            <c:when test="${sessionScope.user eq null}">
                <a href="login.jsp" class="signin">
                    <button type="button" class="btn btn-outline-primary me-2">Sign-in</button>
                </a>
                <a href="register.jsp" class="signup">
                    <button type="button" class="btn btn-primary">Sign-up</button>
                </a>
            </c:when>
            <c:otherwise>
                <a class="signout">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="logout">
                        <button type="submit" class="btn btn-outline-primary me-2">Sign-out</button>
                    </form>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</header>