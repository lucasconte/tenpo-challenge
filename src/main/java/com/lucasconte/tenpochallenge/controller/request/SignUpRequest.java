package com.lucasconte.tenpochallenge.controller.request;

import javax.validation.constraints.NotBlank;

public class SignUpRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
