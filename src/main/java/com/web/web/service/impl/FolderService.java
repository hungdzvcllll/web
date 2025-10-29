package com.web.web.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.web.dto.response.FolderResponse;
import com.web.web.entity.Class;
import com.web.web.entity.UserInClass;
import com.web.web.mapper.FolderMapper;
import com.web.web.entity.Folder;
import com.web.web.entity.User;
import com.web.web.repository.ClassRepository;
import com.web.web.repository.FolderRepository;
import com.web.web.repository.UserInClassRepository;

@Service
public class FolderService {
    @Autowired
    FolderRepository folderRepo;
    @Autowired
    AccessFolderService afService;
    @Autowired
    ClassRepository classRepo;
    //@Autowired
    //UserInClassRepository uInClassRepo;
    @Autowired
    UserService userService;
    @Autowired
    UserInClassService uInClassService;
    @Autowired
    FolderMapper folderMapper;
    
    public void savePersonalFolder(String name,Boolean isPrivate ){
        Folder f=new Folder(null,name,isPrivate,null,null,null);
        Folder save=folderRepo.save(f);
        afService.saveCreate(save);
    }
    public void saveClassFolder(String name,Integer classId){
        Class classs=classRepo.findById(classId).get();
        uInClassService.checkIfYouAreInClass(classs);
        Folder folder=new Folder(null,name,true,classs,
        null,null);
        folderRepo.save(folder);
    }
     public void updateFolder(Integer id,String name,Boolean isPrivate){
        afService.checkCreate(id);
        Folder folder=folderRepo.findById(id).get();
        if(folder==null)
            throw new RuntimeException("folder not exist");
        if(folder.getBelongToClass()!=null)
            throw new RuntimeException("folder belong to class,can't be public");
        folder.setName(name);
        folder.setIsPrivate(isPrivate);
        folderRepo.save(folder);
    }
    
    public Page<FolderResponse> findClassFolder(Integer classId,Pageable pageable){
        Class classs=classRepo.findById(classId).get();
        uInClassService.checkIfYouAreInClass(classs);
        return  folderRepo.findByBelongToClass(classs,pageable).map(folderMapper::toDTO);
    }
    public void checkIfYouCanAccessFolder(Integer id){
        Folder folder=folderRepo.findById(id).get();
        if(folder==null)
            throw new RuntimeException("folder not exist");
        if(folder.getIsPrivate()==true){
            if(folder.getBelongToClass()==null)
                afService.checkAccess(id);
            else
                uInClassService.checkIfYouAreInClass(folder.getBelongToClass());
        }
    }
    public FolderResponse findById(Integer id){
        checkIfYouCanAccessFolder(id);
        return folderMapper.toDTO(folderRepo.findById(id).get());
    }
    
    public Page<FolderResponse> findPublicByName(String name,Pageable pageable){
        return folderRepo.findByIsPrivateAndName(false,name,pageable).map(folderMapper::toDTO);
    }
}
