package com.my.timekeeping.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This servlet filter for checking is user authorized
 * Class implements the Filter interface {@link javax.servlet.Filter} and overrides doFilter and destroy methods
 *
 * @author Andrey
 * @version 1.0
 */
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AuthFilter.class);
    private List<String> excludedPages = Arrays.asList("/index.jsp", "/register.jsp");

    /**
     * Method checks is user authorized and if not redirects servlet to login.jsp
     *
     * @param servletRequest servlet request
     * @param servletResponse servlet response
     * @param filterChain list of filters to be processed
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.trace("auth filter get down to work");
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        final HttpSession session = req.getSession();
        final String command = req.getParameter("command");
        String path = req.getServletPath();

        if (session != null && session.getAttribute("user") == null
                && !command.equals("login") && !excludedPages.contains(path)) {
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        excludedPages = null;
    }
}