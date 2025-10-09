package com.web.web.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.web.web.dto.request.ClassRequest;
import com.web.web.dto.request.TestRequest;

import com.web.web.dto.response.TestResponse;
import com.web.web.entity.StudySet;
import com.web.web.entity.Test;

@Mapper(componentModel = "spring")
public interface TestMapper {
    @Mapping(target="studySet",source="studySetId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TestRequest dto, @MappingTarget Test entity);
    default StudySet mapStudySet(Integer id){
        if(id==null)
            return null;
        StudySet ss=new StudySet();
        ss.setId(id);
        return ss; 
    }    
    @Mapping(target="studySetId",source="studySet.id")
    @Mapping(target="userId",source="user.id")
    public TestResponse toDTO(Test t);
}
