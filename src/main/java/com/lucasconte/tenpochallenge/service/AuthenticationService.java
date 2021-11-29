package com.lucasconte.tenpochallenge.service;

import com.lucasconte.tenpochallenge.dto.UserDTO;
import com.lucasconte.tenpochallenge.exception.LogoutException;
import com.lucasconte.tenpochallenge.exception.SignUpException;

public interface AuthenticationService {
    UserDTO signUp(UserDTO userDTO) throws SignUpException;
    void logout(String authorizationHeader) throws LogoutException;
}
