package com.lucasconte.tenpochallenge.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token_blacklisted", indexes = @Index(columnList = "token", name = "token_idx"))
public class TokenBlacklistedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expiresAt;

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }
}
