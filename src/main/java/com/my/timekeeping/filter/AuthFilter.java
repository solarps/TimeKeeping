package com.my.timekeeping.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//TODO REWRITE FILTER

//@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.trace("auth filter get down to work");
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        final HttpSession session = req.getSession();
        final String command = req.getParameter("command");

        if (session != null && session.getAttribute("user") == null
                && !"login".equals(command)){
            /*req.getSession().setAttribute("error", "Sorry,  you have to be authorized to have access to the resource.");*/
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
