package com.web.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.web.dto.request.UserRequest;
import com.web.web.dto.response.UserResponse;
import com.web.web.entity.User;

public interface UserServiceInterface {
    public void register(UserRequest ur);
    public void confirmRegister(String username,String registerCode);
    public User findCurrentUser();
    public UserResponse responseTofindCurrentUser();
    public void resetPassword(String username);
    public void updateYourProfile(UserRequest ur);
    public UserResponse findById(Integer id);
    public Page<UserResponse> findAll(Pageable pageable);
}
