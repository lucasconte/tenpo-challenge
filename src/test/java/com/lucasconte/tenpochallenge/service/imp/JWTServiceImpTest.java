package com.lucasconte.tenpochallenge.service.imp;

import com.lucasconte.tenpochallenge.exception.JWTServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTServiceImpTest {
    private static final int EXPIRES_IN_MILLIS = 100000;
    private static final String SECRET = "secret";
    private JWTServiceImp jwtServiceImp;

    @BeforeEach
    void setUp() {
        jwtServiceImp = new JWTServiceImp(EXPIRES_IN_MILLIS, SECRET);
    }

    @Test
    void createToken() {
        var username = "username";
        var token = jwtServiceImp.createToken(username);
        var expiresAt =  token.getExpiresAt();
        var diff = expiresAt.getTime() - token.getCreatedAt().getTime();

        assertEquals(EXPIRES_IN_MILLIS, diff);
        assertEquals("Bearer " , token.getType());
        assertNotNull(token.getAccessToken());
    }

    @Test
    void decodeUsername() throws JWTServiceException {
        var username = "username";
        var token = jwtServiceImp.createToken(username);
        var decoded = jwtServiceImp.decodeUsername(token.getType() + token.getAccessToken());

        assertTrue(decoded.isPresent());
        assertEquals(username, decoded.get());
    }

    @Test
    void decodeExpiresAt() throws JWTServiceException {
        var username = "username";
        var token = jwtServiceImp.createToken(username);
        var decoded = jwtServiceImp.decodeExpiresAt(token.getType() + token.getAccessToken());

        assertTrue(decoded.isPresent());
        assertEquals(token.getExpiresAt().toString(), decoded.get().toString());
    }
}