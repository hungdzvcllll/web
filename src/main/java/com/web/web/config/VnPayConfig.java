package com.web.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "vnpay")
public class VnPayConfig {
    private String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private String queryUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
    private String merchantId = "D9M6WNSL";
    private String hashSecret = "8PVLJ4P1CY8O5QIVV7I63MVYCUMVME9T";
    private String returnUrl = "http://localhost:8081/VnPay/paymentResult";
    private String notifyUrl = "http://localhost:8083/api/payment/vnpay/notify";
    private String version = "2.1.0";
    private String command = "pay";
    private String orderType = "other";
    private String currencyCode = "VND";
    private String locale = "vn";
}