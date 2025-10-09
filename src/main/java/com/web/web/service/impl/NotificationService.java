package com.web.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.web.dto.response.NotificationResponse;
import com.web.web.entity.Notification;
import com.web.web.entity.User;
import com.web.web.mapper.NotificationMapper;
import com.web.web.repository.NotificationRepository;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notiRepo;
    @Autowired
    UserService userService;
    @Autowired
    NotificationMapper mapper;
    public void saveNotification(User u,String noti){
        Notification notification=new Notification(null,noti,u);
        notiRepo.save(notification);
    }
    public Page<NotificationResponse> yourNotification(Pageable pageable){
        User u=userService.findCurrentUser();
        return notiRepo.findByUser(u, pageable).map(mapper::toDTO);
    }
}
