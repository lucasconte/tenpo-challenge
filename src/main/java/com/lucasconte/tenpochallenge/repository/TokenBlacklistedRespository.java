package com.lucasconte.tenpochallenge.repository;

import com.lucasconte.tenpochallenge.entity.TokenBlacklistedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenBlacklistedRespository extends JpaRepository<TokenBlacklistedEntity, Long> {
    Optional<TokenBlacklistedEntity> findByToken(String token);
}
