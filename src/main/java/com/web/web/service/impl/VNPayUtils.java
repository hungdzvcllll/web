package com.web.web.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.web.web.config.VnPayConfig;
import com.web.web.dto.request.VipUserRequest;
import com.web.web.entity.VipUser;
import com.web.web.repository.VipUserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Service
public class VNPayUtils {
    @Autowired
    VipUserRepository vipRepo;
    @Autowired
    VipUserService vipService;
    @Autowired
    UserService userService;
    @Autowired
    private VnPayConfig vnPayConfig;
    
    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "Invalid IP:" + e.getMessage();
        }
        return ipAddress;
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String hashAllFields(Map<String, String> fields, String hashSecret) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();

        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(fieldValue);
                if (i < fieldNames.size() - 1) {
                    hashData.append('&');
                }
            }
        }
        return hmacSHA512(hashSecret, hashData.toString());
    }

    public static String buildQueryString(Map<String, String> params) {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                    query.append("=");
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }
            if (itr.hasNext()) {
                query.append("&");
            }
        }
        return query.toString();
    }

    public String createVNPayPayment(VipUserRequest vip, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // SỬA: Dùng findActiveVipByUserId thay vì findVipUserNotExpired
        List<VipUser> activeVips = vipRepo.findActiveVipByUserId(userService.findCurrentUser().getId());
        if (activeVips != null && !activeVips.isEmpty()) {
            throw new RuntimeException("Payment already exists for this order");
        }
        
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
       
        long amount = (long) 300000 * (long) 100;
        String vnp_TxnRef = getRandomNumber(8);
        String vnp_IpAddr = getIpAddress(req);

        String vnp_TmnCode = vnPayConfig.getMerchantId();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", vip.getBankCode());
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", vip.getLocale());
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        VipUser vipUs = vipService.save(vip, vnp_TxnRef);  
        vnp_Params.put("vnp_OrderInfo", Integer.toString(vipUs.getId()));
        
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayConfig.getPaymentUrl() + "?" + queryUrl;
        return paymentUrl;
    }
    
    public String handleVNPayReturn(Map<String, String> vnpayParams) {
        try {
            String vnp_SecureHash = vnpayParams.get("vnp_SecureHash");
            vnpayParams.remove("vnp_SecureHash");
            vnpayParams.remove("vnp_SecureHashType");

            String signValue = VNPayUtils.hashAllFields(vnpayParams, vnPayConfig.getHashSecret());

            if (signValue.equals(vnp_SecureHash)) {
                String vnp_TxnRef = vnpayParams.get("vnp_TxnRef");
                String vnp_ResponseCode = vnpayParams.get("vnp_ResponseCode");
                String vnp_TransactionStatus = vnpayParams.get("vnp_TransactionStatus");

                VipUser vip = vipRepo.findByTransactionId(vnp_TxnRef);
                if (vip == null)
                    throw new RuntimeException("not found");
                if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                    vip.setStatus("success");
                    vipRepo.save(vip);
                    return "success";
                } else {
                    vip.setStatus("failed");
                    vipRepo.save(vip);
                    return "failed";
                }
            } else {
                throw new RuntimeException("Invalid signature");
            }
        } catch (Exception e) {
            throw new RuntimeException("VNPay return processing failed: " + e.getMessage());
        }
    }
    
    /**
     * Xử lý callback từ VNPay (GET request) và redirect về frontend
     */
    public void handleVNPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy tất cả params từ VNPay
        Map<String, String> vnpayParams = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            if (values != null && values.length > 0) {
                vnpayParams.put(key, values[0]);
            }
        });
        
        String vnp_SecureHash = vnpayParams.get("vnp_SecureHash");
        vnpayParams.remove("vnp_SecureHash");
        vnpayParams.remove("vnp_SecureHashType");
        
        String signValue = VNPayUtils.hashAllFields(vnpayParams, vnPayConfig.getHashSecret());
        
        String vnp_TxnRef = vnpayParams.get("vnp_TxnRef");
        String vnp_ResponseCode = vnpayParams.get("vnp_ResponseCode");
        String vnp_TransactionStatus = vnpayParams.get("vnp_TransactionStatus");
        String vnp_Amount = vnpayParams.get("vnp_Amount");
        
        String status = "failed";
        String message = "Giao dịch thất bại";
        
        if (signValue.equals(vnp_SecureHash)) {
            VipUser vip = vipRepo.findByTransactionId(vnp_TxnRef);
            if (vip != null) {
                if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                    vip.setStatus("success");
                    vipRepo.save(vip);
                    status = "success";
                    message = "Giao dịch thành công";
                } else {
                    vip.setStatus("failed");
                    vipRepo.save(vip);
                    status = "failed";
                    message = "Giao dịch thất bại";
                }
            }
        }
        
        // Redirect về frontend với params
        String frontendUrl = "http://localhost:3000/pages/payment-result.html";
        String redirectUrl = frontendUrl + "?status=" + status 
            + "&code=" + vnp_ResponseCode 
            + "&txnRef=" + vnp_TxnRef
            + "&amount=" + vnp_Amount
            + "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
        
        response.sendRedirect(redirectUrl);
    }
}