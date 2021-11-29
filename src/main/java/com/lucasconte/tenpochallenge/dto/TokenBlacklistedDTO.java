package com.lucasconte.tenpochallenge.dto;

import java.util.Date;

public class TokenBlacklistedDTO {
    private String token;
    private Date expiresAt;

    public TokenBlacklistedDTO() {}

    public TokenBlacklistedDTO(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
