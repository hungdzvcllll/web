package com.web.web.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.web.web.dto.request.ClassRequest;
import com.web.web.dto.response.ClassResponse;
import com.web.web.entity.Class;
@Mapper(componentModel = "spring")
public interface ClassMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ClassRequest dto, @MappingTarget Class entity);
    public ClassResponse toDTO(Class c);
}
