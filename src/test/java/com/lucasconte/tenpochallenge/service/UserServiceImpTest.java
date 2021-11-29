package com.lucasconte.tenpochallenge.service;

import com.lucasconte.tenpochallenge.dto.UserDTO;
import com.lucasconte.tenpochallenge.entity.UserEntity;
import com.lucasconte.tenpochallenge.mapper.UserMapper;
import com.lucasconte.tenpochallenge.repository.UserRepository;
import com.lucasconte.tenpochallenge.service.imp.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImpTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final ArgumentCaptor<UserEntity> ENTITY_CAPTOR = ArgumentCaptor.forClass(UserEntity.class);

    private UserServiceImp userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImp(userRepository, userMapper, passwordEncoder);
    }

    @Test
    void save() {
        var dto = new UserDTO();
        dto.setId(1l);
        dto.setUsername("username");
        dto.setPassword("password");

        var entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);

        var expected = new UserEntity();
        expected.setUsername(dto.getUsername());
        expected.setPassword("passwordEncoded");

        when(userMapper.toEntity(dto)).thenReturn(entity);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn(expected.getPassword());
        when(userRepository.save(ENTITY_CAPTOR.capture())).thenReturn(expected);
        when(userMapper.fromEntity(expected)).thenReturn(dto);

        assertEquals(dto, userService.save(dto));
        assertEquals("username", ENTITY_CAPTOR.getValue().getUsername());
        assertEquals("passwordEncoded", ENTITY_CAPTOR.getValue().getPassword());

    }

    @Test
    void save_null() {
        assertNull(userService.save(null));
    }


    @Test
    void findByUsername() {
        var username = "username";
        var entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword("passwordEncoded");

        var expected = new UserDTO();
        expected.setUsername(username);
        expected.setPassword(entity.getPassword());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(entity));
        when(userMapper.fromEntity(entity)).thenReturn(expected);
        assertEquals(Optional.of(expected), userService.findByUsername(username));
    }

    @Test
    void findByUsername_not_found() {
        var username = "username";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), userService.findByUsername(username));
    }

    @Test
    void loadUserByUsername() {
        var username = "username";
        var entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword("passwordEncoded");
        var dto = new UserDTO();
        dto.setUsername(username);
        dto.setPassword(entity.getPassword());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(entity));
        when(userMapper.fromEntity(entity)).thenReturn(dto);

        var actual = userService.loadUserByUsername(username);
        assertEquals(entity.getUsername(), actual.getUsername());
        assertEquals(entity.getPassword(), actual.getPassword());
        assertTrue(actual.getAuthorities().isEmpty());
    }

    @Test
    void loadUserByUsername_not_found() {
        var username = "user0";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        var exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
        assertEquals(exception.getMessage(), "There is no user for username user0");
    }
}