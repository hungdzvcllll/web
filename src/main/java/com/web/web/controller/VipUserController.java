package com.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.service.impl.VipUserService;

@RestController
@RequestMapping("/vipUser")
//danh sách ae đăng ký vip
public class VipUserController {
    @Autowired
    VipUserService service;
    @GetMapping("/getYourVipRegister")
    //check lại các lần nạp vip của bạn
    public ResponseEntity<?> getYourVipRegister(Pageable pageable){
        try{
            return ResponseEntity.ok(service.yourVipRegister(pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findAll")
    //tìm tất cả(chỉ dành cho admin)
    public ResponseEntity<?> findAll(Pageable pageable){
        try{
            return ResponseEntity.ok(service.findAll(pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    
}
