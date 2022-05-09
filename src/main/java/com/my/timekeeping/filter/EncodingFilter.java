package com.my.timekeeping.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * This servlet filter for checking servlet encoding
 * Class implements the Filter interface {@link javax.servlet.Filter} and overrides init, doFilter and destroy methods
 *
 * @author Andrey
 * @version 1.0
 */
@WebFilter(urlPatterns = "/*", initParams = @WebInitParam(name = "encoding", value = "UTF-8"))
public class EncodingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(EncodingFilter.class);
    private String code;

    /**
     * Method for initialization filter, gets encoding from filter init parameters
     *
     * @param filterConfig filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) {
        code = filterConfig.getInitParameter("encoding");
        logger.trace("encoding filter init");
    }

    /**
     * Method checks servlet encoding and sets encoding from filter config
     *
     * @param servletRequest  servlet request
     * @param servletResponse servlet response
     * @param filterChain     list of filters to be processed
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.trace("encoding filter get down to work");
        String codeRequest = servletRequest.getCharacterEncoding();
        if (codeRequest != null && !code.equalsIgnoreCase(codeRequest)) {
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
