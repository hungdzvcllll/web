package com.web.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.dto.request.VipUserRequest;
import com.web.web.service.impl.VNPayUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/VnPay")
public class VnPayController {
    @Autowired
    VNPayUtils utils;
    
    /**
     * Tạo URL thanh toán VNPay
     * POST /VnPay/pay
     */
    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody VipUserRequest request, HttpServletRequest req, HttpServletResponse resp) {
        try {
            return ResponseEntity.ok(utils.createVNPayPayment(request, req, resp));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    
    /**
     * VNPay callback - VNPay sẽ redirect user đến đây sau khi thanh toán
     * GET /VnPay/paymentResult?vnp_Amount=...&vnp_TxnRef=...&...
     * Sau khi xử lý, redirect user đến trang frontend
     */
    @GetMapping("/paymentResult")
    public void paymentCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            // handleVNPayCallback sẽ tự redirect về frontend
            utils.handleVNPayCallback(request, response);
        } catch (Exception e) {
            try {
                response.sendRedirect("http://localhost:3000/pages/payment-result.html?status=failed&message=" + e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * API xử lý kết quả từ frontend (backup - nếu cần gọi thủ công)
     * PUT /VnPay/paymentResult
     */
    @PutMapping("/paymentResult")
    public ResponseEntity<?> payResult(@RequestBody Map<String, String> result) {
        try {
            return ResponseEntity.ok(utils.handleVNPayReturn(result));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}