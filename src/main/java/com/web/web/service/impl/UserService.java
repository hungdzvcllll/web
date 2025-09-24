package com.web.web.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.web.entity.User;
import com.web.web.mapper.UserMapper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.web.dto.request.UserRequest;
import com.web.web.dto.response.UserResponse;
import com.web.web.repository.UserRepository;
import com.web.web.service.UserServiceInterface;

@Service
public class UserService implements UserServiceInterface{
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder pe;
    @Autowired
    EmailSender sender;
    @Autowired
    UserMapper mapper;
    public void register(UserRequest ur){
        if(userRepo.findByUsername(ur.getUsername())!=null)
            throw new RuntimeException("please try another username");
        if(userRepo.findByEmailAndRegistered(ur.getEmail(),true)!=null)
            throw new RuntimeException("please try another email");
        String registerCode=UUID.randomUUID().toString();
        LocalDateTime registerExpired=LocalDateTime.now().plusHours(1);
        String password=pe.encode(ur.getPassword());
        User u=new User(null,ur.getUsername(),ur.getEmail(),"USER","NORMAL",password,false,null,null,registerCode,
        registerExpired,null,null,null,null,null);
        userRepo.save(u);
        sender.sendEmail("registerCode:"+registerCode,ur.getEmail());
    }
    public void confirmRegister(String username,String registerCode){
        User u=userRepo.findByUsernameAndRegistered(username,false);
        if(u==null||u.getConfirmRegisterExpired().isBefore(LocalDateTime.now()))
            throw new RuntimeException("code or username not true or code expired");
        u.setRegistered(true);
        userRepo.save(u);        
    }
    public User findCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        UserDetails u = (UserDetails) principal;
        String name = u.getUsername();
        return userRepo.findByUsernameAndRegistered(name,true);
    }
    public UserResponse responseTofindCurrentUser(){
        return mapper.toDTO(findCurrentUser());
    }
    public void resetPassword(String username){
        User u=userRepo.findByUsernameAndRegistered(username, true);
        if(u==null)
            throw new RuntimeException("user didn't register or not exist");
        String resetCode=UUID.randomUUID().toString();
        LocalDateTime resetExpired=LocalDateTime.now().plusHours(1);
        u.setResetPasswordCode(resetCode);
        u.setResetPasswordExpired(resetExpired);
        userRepo.save(u);
        sender.sendEmail("resetPasswordCode:"+resetCode,u.getEmail());
    }
    public void updateYourProfile(UserRequest ur){
        User u=findCurrentUser();
        if(userRepo.findByUsername(ur.getUsername())!=null&&ur.getUsername().equals(u.getUsername())==false)
            throw new RuntimeException("please try another username");
        if(userRepo.findByEmailAndRegistered(ur.getEmail(),true)!=null&&ur.getEmail().equals(u.getEmail())==false)
            throw new RuntimeException("please try another email"); 
        mapper.updateEntityFromDto(ur,u);
        this.userRepo.save(u);
    }
    public UserResponse findById(Integer id){
        return mapper.toDTO(this.userRepo.findById(id).get());
    }
    public Page<UserResponse> findAll(Pageable pageable){
        return userRepo.findAll(pageable).map(mapper::toDTO);
    }
    
}
