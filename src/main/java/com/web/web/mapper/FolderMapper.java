package com.web.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.web.web.dto.request.FolderRequest;
import com.web.web.dto.response.FolderResponse;
import com.web.web.entity.Folder;
import com.web.web.entity.Class;
@Mapper
public interface FolderMapper {
    @Mapping(target="belongToClass",source="classId")
    public Folder toEntity(FolderRequest fr);
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
