package com.web.web.mapper;

import org.mapstruct.Mapper;

import com.web.web.dto.request.UserRequest;
import com.web.web.dto.response.UserResponse;
import com.web.web.entity.User;

@Mapper
public interface UserMapper {
    public User toEntity(UserRequest ur);
    public UserResponse toDTO(User u);
}
