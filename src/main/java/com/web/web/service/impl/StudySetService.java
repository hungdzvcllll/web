package com.web.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.web.dto.response.StudySetResponse;
import com.web.web.entity.Folder;
import com.web.web.entity.StudySet;
import com.web.web.mapper.StudySetMapper;
import com.web.web.repository.StudySetRepository;

@Service
public class StudySetService {
    @Autowired
    FolderService folderService;
    @Autowired
    StudySetRepository studySetRepo;
    @Autowired
    StudySetMapper mapper;
    public void create(Integer folderId,String name){
        folderService.checkIfYouCanAccessFolder(folderId);
        StudySet studySet=new StudySet(null,name,new Folder(folderId),null,null);
        studySetRepo.save(studySet);
    }
    public StudySetResponse findById(Integer id){
        StudySet set=studySetRepo.findById(id).get();
        folderService.checkIfYouCanAccessFolder(set.getFolder().getId());
        return mapper.toDTO(set);
    }
    public Page<StudySetResponse> findByFolder(Integer folderId,Pageable pageable){
        folderService.checkIfYouCanAccessFolder(folderId);
        
        return studySetRepo.findByFolder(new Folder(folderId), null).map(mapper::toDTO);
    }

    public void delete(Integer id) {
        StudySet set = studySetRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("StudySet not found"));
        folderService.checkIfYouCanAccessFolder(set.getFolder().getId());
        studySetRepo.deleteById(id);
    }
}
