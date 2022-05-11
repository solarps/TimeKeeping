package com.my.timekeeping.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionLocaleFilterTest {

    @InjectMocks
    private SessionLocaleFilter sessionLocaleFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession httpSession;

    @Mock
    private FilterChain filterChain;

    @Test
    public void doFilterTest() throws ServletException, IOException {
        doNothing().when(filterChain).doFilter(eq(request), eq(response));
        when(request.getParameter("sessionLocale")).thenReturn("ua");
        when(request.getSession()).thenReturn(httpSession);

        sessionLocaleFilter.doFilter(request, response, filterChain);

        verify(httpSession).setAttribute("lang", request.getParameter("sessionLocale"));
        verify(filterChain, times(1)).doFilter(request, response);
    }

}