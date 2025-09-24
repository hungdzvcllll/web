package com.web.web.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.web.web.dto.request.ClassRequest;
import com.web.web.dto.request.UserRequest;
import com.web.web.dto.response.UserResponse;
import com.web.web.entity.User;

@Mapper
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserRequest dto, @MappingTarget User entity);
    public UserResponse toDTO(User u);
}
