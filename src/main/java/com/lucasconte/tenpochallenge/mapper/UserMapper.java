package com.lucasconte.tenpochallenge.mapper;

import com.lucasconte.tenpochallenge.dto.UserDTO;
import com.lucasconte.tenpochallenge.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO fromEntity(final UserEntity entity) {
        var userDTO = new UserDTO();
        userDTO.setId(entity.getId());
        userDTO.setUsername(entity.getUsername());
        userDTO.setPassword(entity.getPassword());
        userDTO.setCreatedAt(entity.getCreatedAt());
        return userDTO;
    }

    public UserEntity toEntity(final UserDTO userDTO) {
        var entity = new UserEntity();
        entity.setUsername(userDTO.getUsername());
        entity.setPassword(userDTO.getPassword());
        return entity;
    }
}
