package com.web.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.web.web.dto.request.StudySetItemRequest;
import com.web.web.dto.request.TestRequest;
import com.web.web.dto.response.StudySetItemResponse;
import com.web.web.dto.response.TestResponse;
import com.web.web.entity.StudySet;
import com.web.web.entity.StudySetItem;
import com.web.web.entity.Test;

@Mapper
public interface StudySetItemMapper {
    @Mapping(target="studySet",source="studySetId")
    public Test toEntity(TestRequest tr);
    default StudySet mapStudySet(Integer id){
        if(id==null)
            return null;
        StudySet ss=new StudySet();
        ss.setId(id);
        return ss; 
    }    
    @Mapping(target="studySetId",source="studySet.id")
    public TestResponse toDTO(Test t);
}
