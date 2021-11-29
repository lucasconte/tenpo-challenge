package com.lucasconte.tenpochallenge.controller;

import com.lucasconte.tenpochallenge.controller.request.SignUpRequest;
import com.lucasconte.tenpochallenge.dto.UserDTO;
import com.lucasconte.tenpochallenge.error.ApiError;
import com.lucasconte.tenpochallenge.exception.LogoutException;
import com.lucasconte.tenpochallenge.exception.SignUpException;
import com.lucasconte.tenpochallenge.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signUp")
    public ResponseEntity signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            return ResponseEntity.ok(authenticationService.signUp(
                    new UserDTO(signUpRequest.getUsername(), signUpRequest.getPassword())
            ));

        } catch (SignUpException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiError.conflict(e));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiError.internalServerError(e));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader(AUTHORIZATION) String authorizationHeader) {
        try {
            authenticationService.logout(authorizationHeader);
            return ResponseEntity.ok().build();
        } catch (LogoutException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.unauthorized(e));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiError.internalServerError(e));
        }
    }

}
