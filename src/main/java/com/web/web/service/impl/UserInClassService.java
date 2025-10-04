package com.web.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.web.entity.User;
import com.web.web.entity.UserInClass;
import com.web.web.entity.embedId.UserInClassId;
import com.web.web.mapper.UserInClassMapper;
import com.web.web.repository.ClassRepository;
import com.web.web.repository.FolderRepository;
import com.web.web.repository.UserInClassRepository;
import com.web.web.repository.UserRepository;
import com.web.web.dto.response.UserInClassResponse;
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
    @Autowired
    UserInClassMapper mapper;
    @Autowired
    UserRepository userRepo;
    @Autowired
    NotificationService notiService;
    public void checkIfYouAreInClass(Class classs){
        User u=userService.findCurrentUser();
        UserInClass userInClass=uInClassRepo.findByUserAndClasss(u,classs);
        if(userInClass==null)
            throw new RuntimeException("you are not in this class");
    }
    public void saveTeacherRole(Class classs){
        User u=userService.findCurrentUser();
        UserInClass uInClass=new UserInClass(new UserInClassId(),u,classs,"TEACHER");
        uInClassRepo.save(uInClass);
    }
    public void checkIfYouAreTeacher(Class classs){
        User u=userService.findCurrentUser();
        UserInClass userInClass=uInClassRepo.findByUserAndClasssAndRole(u,classs,"TEACHER");
        if(userInClass==null)
            throw new RuntimeException("you have no permission"); 
    }
    public Page<UserInClassResponse> yourClass(Pageable pageable){
        User u=userService.findCurrentUser();
        return uInClassRepo.findByUser(u,pageable).map(mapper::toDTO);
    }
    public void deleteStudentInClass(Integer userId,Integer classId){
        User u=userRepo.findById(userId).get();
        if(u.getId()==userService.findCurrentUser().getId())
            throw new RuntimeException("can't delete yourrself");
        Class classs=classRepo.findById(classId).get();
        checkIfYouAreTeacher(classs);
        notiService.saveNotification(u, "you was delete from "+classs.getName());
        uInClassRepo.deleteByUserAndClasss(u,classs);
    }
    public void addStudentInClass(Integer userId,Integer classId){
        User u=userRepo.findById(userId).get();
        if(u.getId()==userService.findCurrentUser().getId())
            throw new RuntimeException("can't add yourself");
        
        Class classs=classRepo.findById(classId).get();
        if(uInClassRepo.findByUserAndClasss(u,classs)!=null)
            throw new RuntimeException("user exist");
        checkIfYouAreTeacher(classs);
        notiService.saveNotification(u, "you was add to "+classs.getName());
        uInClassRepo.save(new UserInClass(new UserInClassId(),u,classs,"STUDENT"));
    }
    public Page<UserInClassResponse> userInClass(Integer classId,Pageable pageable){
        Class classs=classRepo.findById(classId).get();
        checkIfYouAreInClass(classs);
        return uInClassRepo.findByClasss(classs,pageable).map(mapper::toDTO);
    }    
    
}
