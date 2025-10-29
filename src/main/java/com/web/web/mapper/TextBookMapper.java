package com.web.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.web.web.dto.response.TextBookResponse;
import com.web.web.entity.TextBook;

@Mapper(componentModel = "spring")
public interface TextBookMapper {
    @Mapping(target="authorId",source="user.id")
    @Mapping(target="authorName",source="user.username")
    public TextBookResponse toDTO(TextBook book);
}
