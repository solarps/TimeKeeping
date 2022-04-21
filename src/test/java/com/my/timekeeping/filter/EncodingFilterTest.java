package com.my.timekeeping.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EncodingFilterTest {

    @Mock
    HttpSession session;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private ServletContext servletContext;


    @InjectMocks
    private EncodingFilter loggingFilter;


    @Test
    public void testDoFilter() throws IOException, ServletException {

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getServletContext()).thenReturn(servletContext);
        Mockito.doNothing().when(filterChain).doFilter(Mockito.eq(request), Mockito.eq(response));

    }
}
