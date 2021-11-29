package com.lucasconte.tenpochallenge.service.imp;

import com.lucasconte.tenpochallenge.dto.TokenBlacklistedDTO;
import com.lucasconte.tenpochallenge.dto.UserDTO;
import com.lucasconte.tenpochallenge.exception.JWTServiceException;
import com.lucasconte.tenpochallenge.exception.LogoutException;
import com.lucasconte.tenpochallenge.exception.SignUpException;
import com.lucasconte.tenpochallenge.service.AuthenticationService;
import com.lucasconte.tenpochallenge.service.JWTService;
import com.lucasconte.tenpochallenge.service.TokenBlacklistedService;
import com.lucasconte.tenpochallenge.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserService userService;
    private final TokenBlacklistedService tokenBlacklistedService;
    private final JWTService jwtService;

    public AuthenticationServiceImp(UserService userService,
                                    TokenBlacklistedService tokenBlacklistedService,
                                    JWTService jwtService) {
        this.userService = userService;
        this.tokenBlacklistedService = tokenBlacklistedService;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO signUp(UserDTO userDTO) throws SignUpException {
        if (userService.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new SignUpException("There is already a user with username " + userDTO.getUsername());
        }
        return userService.save(userDTO);
    }

    @Override
    public void logout(String token) throws LogoutException {
        try {
            var expiresAt = jwtService.decodeExpiresAt(token)
                    .orElseThrow(() -> new LogoutException("There is no expiresAt claim in token"));

            tokenBlacklistedService.save(
                    new TokenBlacklistedDTO(token, expiresAt)
            );

        } catch (JWTServiceException e) {
           throw new LogoutException(e.getMessage());
        }
    }

}
