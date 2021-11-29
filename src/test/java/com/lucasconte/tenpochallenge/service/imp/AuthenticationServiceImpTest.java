package com.lucasconte.tenpochallenge.service.imp;

import com.lucasconte.tenpochallenge.dto.TokenBlacklistedDTO;
import com.lucasconte.tenpochallenge.dto.UserDTO;
import com.lucasconte.tenpochallenge.exception.JWTServiceException;
import com.lucasconte.tenpochallenge.exception.LogoutException;
import com.lucasconte.tenpochallenge.exception.SignUpException;
import com.lucasconte.tenpochallenge.service.JWTService;
import com.lucasconte.tenpochallenge.service.TokenBlacklistedService;
import com.lucasconte.tenpochallenge.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImpTest {
    @Mock
    private UserService userService;
    @Mock
    private TokenBlacklistedService tokenBlacklistedService;
    @Mock
    private JWTService jwtService;

    private AuthenticationServiceImp authenticationServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationServiceImp = new AuthenticationServiceImp(userService, tokenBlacklistedService, jwtService);
    }

    @Test
    void signUp() throws SignUpException {
        var user = new UserDTO();
        user.setUsername("username");
        user.setPassword("password");

        when(userService.findByUsername("username")).thenReturn(Optional.empty());
        when(userService.save(user)).thenReturn(user);
        assertEquals(user, authenticationServiceImp.signUp(user));
    }

    @Test
    void signUp_user_already_exist() throws SignUpException {
        var user = new UserDTO();
        user.setUsername("user0");
        user.setPassword("password");

        when(userService.findByUsername("user0")).thenReturn(Optional.of(user));

        var exception = assertThrows(SignUpException.class, () -> authenticationServiceImp.signUp(user));
        assertEquals("There is already a user with username user0", exception.getMessage());
    }

    @Test
    void logout() throws JWTServiceException, LogoutException {
        var tokenBlacklistedCaptor = ArgumentCaptor.forClass(TokenBlacklistedDTO.class);
        var expiresAt = mock(Date.class);
        var token = "token";
        when(jwtService.decodeExpiresAt(token)).thenReturn(Optional.of(expiresAt));
        when(tokenBlacklistedService.save(tokenBlacklistedCaptor.capture())).thenReturn(new TokenBlacklistedDTO());

        authenticationServiceImp.logout(token);
        assertEquals(token, tokenBlacklistedCaptor.getValue().getToken());
        assertEquals(expiresAt, tokenBlacklistedCaptor.getValue().getExpiresAt());
    }

    @Test
    void logout_no_expiresAt_claim_in_token() throws JWTServiceException, LogoutException {
        var token = "token";
        when(jwtService.decodeExpiresAt(token)).thenReturn(Optional.empty());

        var exception = assertThrows(LogoutException.class, () -> authenticationServiceImp.logout(token));

        verify(tokenBlacklistedService, never()).save(any());
        assertEquals("There is no expiresAt claim in token", exception.getMessage());
    }
}