package com.web.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.web.dto.response.ClassResponse;
import com.web.web.entity.Class;
import com.web.web.mapper.ClassMapper;
import com.web.web.repository.ClassRepository;

@Service
public class ClassService {
    @Autowired
    ClassRepository classRepo;
    @Autowired
    UserInClassService uInClassService;
    @Autowired
    ClassMapper mapper;
    public void createClass(String name){
        Class c=new Class(null,name,null,null);
        Class save=classRepo.save(c);
        uInClassService.saveTeacherRole(save);
    }
    public ClassResponse findById(Integer id){
        Class c=classRepo.findById(id).get();
        uInClassService.checkIfYouAreInClass(c);
        return mapper.toDTO(c);
    }
}
