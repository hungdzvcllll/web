package com.web.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.web.entity.User;
import com.web.web.entity.UserInClass;
import com.web.web.repository.ClassRepository;
import com.web.web.repository.FolderRepository;
import com.web.web.repository.UserInClassRepository;
import com.web.web.entity.Class;
@Service
public class UserInClassService {
 @Autowired
    FolderRepository folderRepo;
    @Autowired
    AccessFolderService afService;
    @Autowired
    ClassRepository classRepo;
    @Autowired
    UserInClassRepository uInClassRepo;
    @Autowired
    UserService userService;
    public void checkIfYouAreInClass(Class classs){
        User u=userService.findCurrentUser();
        UserInClass userInClass=uInClassRepo.findByUserAndClasss(u,classs);
        if(userInClass==null)
            throw new RuntimeException("you are not in this class");
    }
}
