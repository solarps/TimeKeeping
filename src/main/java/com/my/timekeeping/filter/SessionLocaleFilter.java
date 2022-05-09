package com.my.timekeeping.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This servlet filter for checking and changing session locale
 * Class implements the Filter interface {@link javax.servlet.Filter} and overrides init, doFilter and destroy methods
 *
 * @author Andrey
 * @version 1.0
 */
@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(SessionLocaleFilter.class);

    /**
     * Method change session locale by attribute
     *
     * @param request     servlet request, locale parameter gets from here
     * @param response    servlet response
     * @param filterChain list of filters to be processed
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        logger.trace("session locale filter get down to work");

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("sessionLocale") != null) {
            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
        }
        filterChain.doFilter(request, response);
    }

}
