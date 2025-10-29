package com.web.web.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.web.web.dto.request.ChapterRequest;

import com.web.web.dto.response.ChapterResponse;

import com.web.web.entity.Chapter;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ChapterRequest dto, @MappingTarget Chapter entity);
    default Chapter mapStudySet(Integer id){
        if(id==null)
            return null;
        Chapter c=new Chapter();
        c.setId(id);
        return c; 
    }    
    @Mapping(target="textBookId",source="textBook.id")
    @Mapping(target="textBookName",source="textBook.name")
    public ChapterResponse toDTO(Chapter t);
}
