package com.lucasconte.tenpochallenge.service.imp;

import com.lucasconte.tenpochallenge.dto.UserDTO;
import com.lucasconte.tenpochallenge.mapper.UserMapper;
import com.lucasconte.tenpochallenge.repository.UserRepository;
import com.lucasconte.tenpochallenge.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository,
                          UserMapper userMapper,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO save(UserDTO user) {
        return Optional.ofNullable(user)
                .map(userMapper::toEntity)
                .map(entity -> {
                    entity.setPassword(passwordEncoder.encode(entity.getPassword()));
                    return entity;
                })
                .map(userRepository::save)
                .map(userMapper::fromEntity)
                .orElse(null);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::fromEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username)
                .map(savedUser ->
                        User.builder()
                                .username(savedUser.getUsername())
                                .password(savedUser.getPassword())
                                .authorities(Collections.emptyList())
                                .build())
                .orElseThrow(() -> new UsernameNotFoundException("There is no user for username " + username));
    }
}
