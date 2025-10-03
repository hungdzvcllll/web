package com.web.web.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.web.web.dto.request.ClassRequest;
import com.web.web.dto.request.FolderRequest;
import com.web.web.dto.response.FolderResponse;
import com.web.web.entity.Folder;
import com.web.web.entity.Class;
@Mapper(componentModel = "spring")
public interface FolderMapper {
    @Mapping(target="belongToClass",source="classId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FolderRequest dto, @MappingTarget Folder entity);
    default Class mapClass(Integer id){
        if(id==null)
            return null;
        Class c=new Class();
        c.setId(id);
        return c; 
    }
    @Mapping(target="classId",source="belongToClass.id")
    public FolderResponse toDTO(Folder f);
}
