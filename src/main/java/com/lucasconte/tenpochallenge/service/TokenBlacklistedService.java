package com.lucasconte.tenpochallenge.service;

import com.lucasconte.tenpochallenge.dto.TokenBlacklistedDTO;

import java.util.Optional;

public interface TokenBlacklistedService {
    Optional<TokenBlacklistedDTO> findToken(String token);
    TokenBlacklistedDTO save(TokenBlacklistedDTO tokenBlacklistedDTO);
}
