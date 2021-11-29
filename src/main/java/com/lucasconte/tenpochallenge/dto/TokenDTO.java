package com.lucasconte.tenpochallenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TokenDTO {
    private String accessToken;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date expiresAt;

    private String type;

    public TokenDTO(String accessToken, Date createdAt, Date expiresAt, String type) {
        this.accessToken = accessToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public String getType() {
        return type;
    }
}
