package com.lucasconte.tenpochallenge.service.imp;

import com.lucasconte.tenpochallenge.dto.TokenBlacklistedDTO;
import com.lucasconte.tenpochallenge.mapper.TokenBlacklistedMapper;
import com.lucasconte.tenpochallenge.repository.TokenBlacklistedRespository;
import com.lucasconte.tenpochallenge.service.TokenBlacklistedService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenBlacklistedServiceImp implements TokenBlacklistedService {
    private final TokenBlacklistedRespository tokenBlacklistedRespository;
    private final TokenBlacklistedMapper tokenBlacklistedMapper;

    public TokenBlacklistedServiceImp(TokenBlacklistedRespository tokenBlacklistedRespository,
                                      TokenBlacklistedMapper tokenBlacklistedMapper) {
        this.tokenBlacklistedRespository = tokenBlacklistedRespository;
        this.tokenBlacklistedMapper = tokenBlacklistedMapper;
    }

    @Override
    public Optional<TokenBlacklistedDTO> findToken(String token) {
        return tokenBlacklistedRespository.findByToken(token)
                .map(tokenBlacklistedMapper::fromEntity);
    }

    @Override
    public TokenBlacklistedDTO save(TokenBlacklistedDTO tokenBlacklistedDTO) {
        return Optional.ofNullable(tokenBlacklistedDTO)
                .map(tokenBlacklistedMapper::toEntity)
                .map(tokenBlacklistedRespository::save)
                .map(tokenBlacklistedMapper::fromEntity)
                .orElse(null);
    }
}
