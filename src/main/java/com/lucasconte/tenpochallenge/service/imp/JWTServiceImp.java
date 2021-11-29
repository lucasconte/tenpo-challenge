package com.lucasconte.tenpochallenge.service.imp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lucasconte.tenpochallenge.dto.TokenDTO;
import com.lucasconte.tenpochallenge.exception.JWTServiceException;
import com.lucasconte.tenpochallenge.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JWTServiceImp implements JWTService {
    private static final String BEARER = "Bearer ";
    private static final String USERNAME = "username";

    private final int expiresInMilliseconds;
    private final String secret;

    public JWTServiceImp(@Value("${jwt.expiresInMilliseconds}") int expiresInMilliseconds,
                         @Value("${jwt.secret}") String secret) {
        this.expiresInMilliseconds = expiresInMilliseconds;
        this.secret = secret;
    }

    @Override
    public TokenDTO createToken(String username) {
        var now = new Date();
        var expiresAt = new Date(System.currentTimeMillis() + this.expiresInMilliseconds);
        var token = JWT.create()
                .withIssuedAt(now)
                .withNotBefore(now)
                .withExpiresAt(expiresAt)
                .withClaim(USERNAME, username)
                .sign(Algorithm.HMAC256(this.secret));
        return new TokenDTO(token, now, expiresAt, BEARER);
    }

    @Override
    public Optional<String> decodeUsername(String token) throws JWTServiceException {
        try {
            return Optional.ofNullable(token)
                    .map(this::decodedJWT)
                    .map(decodedJWT -> decodedJWT.getClaim(USERNAME).asString());
        } catch (Exception e) {
            throw new JWTServiceException(e.getMessage());
        }
    }
    @Override
    public Optional<Date> decodeExpiresAt(String token) throws JWTServiceException {
        try {
            return Optional.ofNullable(token)
                    .map(this::decodedJWT)
                    .map(DecodedJWT::getExpiresAt);
        } catch (Exception e) {
            throw new JWTServiceException(e.getMessage());
        }
    }

    private DecodedJWT decodedJWT(String token) {
        return JWT.require(Algorithm.HMAC256(this.secret))
                .build()
                .verify(token.substring(BEARER.length()));
    }


}
