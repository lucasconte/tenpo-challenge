package com.lucasconte.tenpochallenge.service.imp;

import com.lucasconte.tenpochallenge.dto.TokenBlacklistedDTO;
import com.lucasconte.tenpochallenge.entity.TokenBlacklistedEntity;
import com.lucasconte.tenpochallenge.mapper.TokenBlacklistedMapper;
import com.lucasconte.tenpochallenge.repository.TokenBlacklistedRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TokenBlacklistedServiceImpTest {
    @Mock
    private TokenBlacklistedRespository tokenBlacklistedRespository;
    @Mock
    private TokenBlacklistedMapper tokenBlacklistedMapper;

    private TokenBlacklistedServiceImp tokenBlacklistedServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenBlacklistedServiceImp = new TokenBlacklistedServiceImp(tokenBlacklistedRespository, tokenBlacklistedMapper);
    }

    @Test
    void findToken() {
        var token = "token";
        var expiresAt = Mockito.mock(java.util.Date.class);

        var entity = new TokenBlacklistedEntity();
        entity.setToken(token);
        entity.setExpiresAt(expiresAt);

        var dto = new TokenBlacklistedDTO();
        dto.setToken(token);
        dto.setExpiresAt(expiresAt);

        when(tokenBlacklistedRespository.findByToken(token)).thenReturn(Optional.of(entity));
        when(tokenBlacklistedMapper.fromEntity(entity)).thenReturn(dto);

        assertEquals(Optional.of(dto), tokenBlacklistedServiceImp.findToken(token));
    }

    @Test
    void findToken_not_found() {
        var token = "token";

        when(tokenBlacklistedRespository.findByToken(token)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), tokenBlacklistedServiceImp.findToken(token));
    }

    @Test
    void save() {
        var token = "token";
        var expiresAt = Mockito.mock(java.util.Date.class);

        var dto = new TokenBlacklistedDTO();
        dto.setToken(token);
        dto.setExpiresAt(expiresAt);

        var expected = new TokenBlacklistedEntity();
        expected.setToken(token);
        expected.setExpiresAt(expiresAt);

        when(tokenBlacklistedMapper.toEntity(dto)).thenReturn(expected);
        when(tokenBlacklistedRespository.save(expected)).thenReturn(expected);
        when(tokenBlacklistedMapper.fromEntity(expected)).thenReturn(dto);

        assertEquals(dto, tokenBlacklistedServiceImp.save(dto));
    }

    @Test
    void save_null() {
       assertNull(tokenBlacklistedServiceImp.save(null));
    }
}