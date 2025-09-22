package com.web.web.mapper;

import org.mapstruct.Mapper;

import com.web.web.dto.request.ClassRequest;
import com.web.web.dto.response.ClassResponse;
import com.web.web.entity.Class;
@Mapper
public interface ClassMapper {
    public Class toEntity(ClassRequest cr);
    public ClassResponse toDTO(Class c);
}
