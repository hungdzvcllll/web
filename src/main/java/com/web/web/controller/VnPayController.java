package com.web.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.dto.request.VipUserRequest;
import com.web.web.service.impl.VNPayUtils;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/VnPay")
//nạp vip thông qua vnpay
public class VnPayController {
    @Autowired
    VNPayUtils utils;
    @PostMapping("/pay")
    //nạp vip
    public ResponseEntity<?> pay(@RequestBody VipUserRequest request,HttpServletRequest req,HttpServletResponse resp){
        try{
            
            return ResponseEntity.ok(utils.createVNPayPayment(request,req,resp));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PutMapping("/paymentResult")
    //kết quả sau nạp vip
     public ResponseEntity<?> payResult(@RequestBody Map<String,String> result){
        try{
            return ResponseEntity.ok(utils.handleVNPayReturn(result));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    
}
