package com.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.service.impl.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    NotificationService service;
    @GetMapping("/yourNoti")
    public ResponseEntity<?>yourNoti(Pageable pageable){
        try{
             return ResponseEntity.ok(service.yourNotification(pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
