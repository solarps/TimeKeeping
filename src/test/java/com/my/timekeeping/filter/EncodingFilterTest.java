package com.my.timekeeping.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.*;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EncodingFilterTest {

    @InjectMocks
    private EncodingFilter encodingFilter;

    @Mock
    private ServletRequest request;

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private FilterConfig filterConfig;

    @Test
    public void doFilterTest() throws IOException, ServletException {
        Mockito.doNothing().when(filterChain).doFilter(Mockito.eq(request), Mockito.eq(response));

        String encode = "UTF-8";
        when(filterConfig.getInitParameter("encoding")).thenReturn(encode);

        encodingFilter.init(filterConfig);
        when(request.getCharacterEncoding()).thenReturn("ISO-8859-1");

        encodingFilter.doFilter(request, response, filterChain);
        verify(request).setCharacterEncoding(encode);
        verify(response).setCharacterEncoding(encode);
    }

}
