package com.lucasconte.tenpochallenge.mapper;

import com.lucasconte.tenpochallenge.dto.TokenBlacklistedDTO;
import com.lucasconte.tenpochallenge.entity.TokenBlacklistedEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenBlacklistedMapper {

    public TokenBlacklistedDTO fromEntity(TokenBlacklistedEntity entity) {
        var dto = new TokenBlacklistedDTO();
        dto.setToken(entity.getToken());
        dto.setExpiresAt(entity.getExpiresAt());
        return dto;
    }

    public TokenBlacklistedEntity toEntity(TokenBlacklistedDTO dto) {
        var entity = new TokenBlacklistedEntity();
        entity.setToken(dto.getToken());
        entity.setExpiresAt(dto.getExpiresAt());
        return entity;
    }
}
