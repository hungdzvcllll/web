package com.web.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.web.web.dto.response.AccessFolderResponse;
import com.web.web.entity.AccessFolder;

@Mapper
public interface AccessFolderMapper {
    @Mapping(target="folderId",source="folder.id")
    @Mapping(target="userId",source="user.id")
    AccessFolderResponse toDTO(AccessFolder af);
}
