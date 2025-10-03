package com.web.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.web.entity.Notification;
import com.web.web.entity.User;
import com.web.web.repository.NotificationRepository;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notiRepo;
    public void saveNotification(User u,String noti){
        Notification notification=new Notification(null,noti,u);
        notiRepo.save(notification);
    }
}
