package com.lucasconte.tenpochallenge.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasconte.tenpochallenge.error.ApiError;
import com.lucasconte.tenpochallenge.exception.JWTServiceException;
import com.lucasconte.tenpochallenge.service.JWTService;
import com.lucasconte.tenpochallenge.service.TokenBlacklistedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final Set<String> shouldNotFilterUris;
    private final TokenBlacklistedService tokenBlacklistedService;

    public JWTAuthenticationFilter(JWTService jwtService,
                                   Set<String> shouldNotFilterUris,
                                   TokenBlacklistedService tokenBlacklistedService) {
        this.jwtService = jwtService;
        this.shouldNotFilterUris = shouldNotFilterUris;
        this.tokenBlacklistedService = tokenBlacklistedService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authorizationHeader = request.getHeader(AUTHORIZATION);

            jwtService.decodeUsername(authorizationHeader)
                    .filter(username -> tokenBlacklistedService.findToken(authorizationHeader).isEmpty())
                    .ifPresent( (username) ->
                            SecurityContextHolder.getContext().setAuthentication(
                                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()))
                    );

            filterChain.doFilter(request, response);

        } catch (JWTServiceException e) {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),
                    ApiError.of(HttpStatus.UNAUTHORIZED, "The authorization token is not valid", e));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return shouldNotFilterUris.stream().anyMatch(uri -> uri.equals(request.getRequestURI()));
    }


}


