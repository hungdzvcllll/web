package com.web.web.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.web.web.dto.request.ClassRequest;
import com.web.web.dto.request.StudySetItemRequest;
import com.web.web.dto.request.StudySetRequest;
import com.web.web.dto.request.TestRequest;
import com.web.web.dto.response.StudySetItemResponse;
import com.web.web.dto.response.TestResponse;
import com.web.web.entity.StudySet;
import com.web.web.entity.StudySetItem;
import com.web.web.entity.Test;
import com.web.web.repository.StudySetRepository;

@Mapper(componentModel = "spring")
public interface StudySetItemMapper {
    //@Autowired
    //public static final StudySetRepository repo;
    //@Mapping(target="studySet",source="studySetId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StudySetItemRequest dto, @MappingTarget StudySetItem entity);
    default StudySet mapStudySet(Integer id){
        if(id==null)
            return null;
        StudySet ss=new StudySet();
        ss.setId(id);
        return ss; 
    }    
    @Mapping(target="studySetId",source="studySet.id")
    public StudySetItemResponse toDTO(StudySetItem t);
}
