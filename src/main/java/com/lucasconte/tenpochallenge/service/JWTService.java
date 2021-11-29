package com.lucasconte.tenpochallenge.service;

import com.lucasconte.tenpochallenge.dto.TokenDTO;
import com.lucasconte.tenpochallenge.exception.JWTServiceException;

import java.util.Date;
import java.util.Optional;

public interface JWTService {
    TokenDTO createToken(String username);
    Optional<String> decodeUsername(String token) throws JWTServiceException;
    Optional<Date> decodeExpiresAt(String token) throws JWTServiceException;
}
