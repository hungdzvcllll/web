package com.web.web.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.web.web.dto.request.ClassRequest;
import com.web.web.dto.request.StudySetRequest;
import com.web.web.dto.response.StudySetResponse;
import com.web.web.entity.Folder;
import com.web.web.entity.StudySet;

@Mapper
public interface StudySetMapper {
    @Mapping(source="folderId",target="folder")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StudySetRequest dto, @MappingTarget StudySet entity);
    default Folder mapFolder(Integer id){
        if(id==null)
            return null;
        Folder f=new Folder();
        f.setId(id);
        return f; 
    }
    @Mapping(source="folder.id",target="folderId")
    public StudySetResponse toDTO(StudySet ss);
}
