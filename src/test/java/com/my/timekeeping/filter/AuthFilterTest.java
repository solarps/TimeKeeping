package com.my.timekeeping.filter;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

    @InjectMocks
    private AuthFilter authFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpSession httpSession;

    @Mock
    private RequestDispatcher requestDispatcher;


    @Test
    public void doFilterTest() throws ServletException, IOException {
        doNothing().when(filterChain).doFilter(eq(request), eq(response));

        when(request.getParameter("command")).thenReturn("getAllActivity");
        when(request.getSession()).thenReturn(httpSession);
        when(request.getServletPath()).thenReturn("/otherurl.jsp");
        when(request.getRequestDispatcher("login.jsp")).thenReturn(requestDispatcher);

        authFilter.doFilter(request, response,
                filterChain);

        verify(requestDispatcher).forward(request, response);
        verify(filterChain, times(1)).doFilter(request, response);
    }

}