package com.web.web.controller;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.dto.request.VipUserRequest;
import com.web.web.service.impl.VNPayUtils;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/VnPay")
//nạp vip thông qua vnpay
public class VnPayController {
    @Autowired
    VNPayUtils utils;

    @Value("${app.frontend.url}")
    private String frontendUrl;

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
/*    @PutMapping("/paymentResult")
    //kết quả sau nạp vip
     public ResponseEntity<?> payResult(@RequestBody Map<String,String> result){
        try{
            return ResponseEntity.ok(utils.handleVNPayReturn(result));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
*/
    @GetMapping("/paymentResult")
    public void payResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Map<String, String> params = request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

            String status = utils.handleVNPayReturn(params);

            String redirectUrl = frontendUrl + "/pages/payment-result.html?" +  
                                 "status=" + status + 
                                 "&vnp_TxnRef=" + params.get("vnp_TxnRef") + 
                                 "&vnp_Amount=" + params.get("vnp_Amount") +
                                 "&vnp_ResponseCode=" + params.get("vnp_ResponseCode");
            
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(frontendUrl + "/pages/payment-result.html?status=fail&message=" + e.getMessage());
        }
        }
    }
}
