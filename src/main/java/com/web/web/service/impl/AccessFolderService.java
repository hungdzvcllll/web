package com.web.web.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.web.dto.response.AccessFolderResponse;
import com.web.web.dto.response.FolderResponse;
import com.web.web.entity.AccessFolder;
import com.web.web.entity.Folder;
import com.web.web.entity.User;
import com.web.web.entity.embedId.AccessFolderId;
import com.web.web.mapper.AccessFolderMapper;
import com.web.web.repository.AccessFolderRepository;
import com.web.web.repository.FolderRepository;
import com.web.web.repository.UserRepository;

@Service
public class AccessFolderService {
    @Autowired
    UserService userService;
    //@Autowired
    //FolderService folderService;
    @Autowired
    AccessFolderRepository acRepo;
    @Autowired
    AccessFolderMapper acMapper;
    @Autowired
    FolderRepository folderRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    NotificationService notiService;
    public AccessFolder saveCreate(Folder f) {
        User u = userService.findCurrentUser();
        System.out.println("folder id:"+f.getId());
        AccessFolder af = new AccessFolder(new AccessFolderId(), u, f, "CREATE");
        return acRepo.save(af);
    }

    public Folder checkCreate(Integer folderId){
        Folder folder=folderRepo.findById(folderId).get();
        User u=userService.findCurrentUser();
        AccessFolder af=acRepo.findByUserAndFolderAndRole(u,folder,"CREATE");
        if(af==null)
            throw new RuntimeException("You don't have permission with this folder");
        return folder;
    }
    
    public Page<AccessFolderResponse> yourFolder(Pageable pageable) {
        User u=userService.findCurrentUser();
        
        return acRepo.findByUser(u, pageable).map(acMapper::toDTO);
    }
    public void checkAccess(Integer folderId){
        Folder folder=folderRepo.findById(folderId).get();
        User u=userService.findCurrentUser();
        AccessFolder af=acRepo.findByUserAndFolder(u,folder);
            if(af==null)
                throw new RuntimeException("You don't have permission with this folder");
    }
    public void inviteUserFolder(Integer userId,Integer folderId){
        Folder folder=checkCreate(folderId);
        User u=userRepo.findById(userId).get();
        if(u==null)
            throw new RuntimeException("User not exist");
        if(u.getId()==userService.findCurrentUser().getId())
            throw new RuntimeException("can't invite yourself");
        if(acRepo.findByUserAndFolder(u,folder)!=null)
             throw new RuntimeException("user existed");
        AccessFolder access=new AccessFolder(new AccessFolderId(),u,folder,"INVITE");
        acRepo.save(access);
        notiService.saveNotification(u, "you was invite to use folder "+folder.getName());
    }
        

}
