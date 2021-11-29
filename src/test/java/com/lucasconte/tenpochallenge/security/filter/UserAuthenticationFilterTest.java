package com.lucasconte.tenpochallenge.security.filter;

import com.lucasconte.tenpochallenge.dto.TokenDTO;
import com.lucasconte.tenpochallenge.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserAuthenticationFilterTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTService jwtService;
    private UserAuthenticationFilter userAuthenticationFilter;

    @BeforeEach
    void setUp() {
        openMocks(this);
        userAuthenticationFilter = new UserAuthenticationFilter(authenticationManager, jwtService);
    }

    @Test
    void attemptAuthentication() {
        var username = "username";
        var password = "password";
        var request = mock(HttpServletRequest.class);
        var response = mock(HttpServletResponse.class);
        when(request.getParameter(username)).thenReturn(username);
        when(request.getParameter(password)).thenReturn(password);
        var expected = new UsernamePasswordAuthenticationToken(username, password);

        userAuthenticationFilter.attemptAuthentication(request, response);
        verify(authenticationManager, times(1)).authenticate(expected);
    }

    @Test
    void successfulAuthentication() throws IOException, ServletException {
        var request = mock(HttpServletRequest.class);
        var response = mock(HttpServletResponse.class);
        var filterChain = mock(FilterChain.class);
        var authResult = mock(Authentication.class);
        var user = mock(org.springframework.security.core.userdetails.User.class);
        var username = "username";
        var token = new TokenDTO("token", null, null, "Bearer");
        var outputStream = mock(ServletOutputStream.class);

        when(authResult.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        when(jwtService.createToken(username)).thenReturn(token);
        when(response.getOutputStream()).thenReturn(outputStream);

        userAuthenticationFilter.successfulAuthentication(request, response, filterChain, authResult);
        verify(response, times(1)).setContentType(MediaType.APPLICATION_JSON_VALUE);

    }
}