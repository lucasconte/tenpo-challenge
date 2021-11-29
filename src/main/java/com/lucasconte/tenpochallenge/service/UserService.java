package com.lucasconte.tenpochallenge.service;

import com.lucasconte.tenpochallenge.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    UserDTO save(UserDTO user);
    Optional<UserDTO> findByUsername(String username);
}
