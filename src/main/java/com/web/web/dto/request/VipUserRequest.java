package com.web.web.dto.request;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VipUserRequest {
    //private Integer id;
    //private LocalDateTime expired;
    //private Integer payValue;
    private String returnUrl;
    private String cancelUrl;
    private String bankCode;
    private String locale = "vn";
    //@ManyToOne(cascade = CascadeType.ALL)
    //private User user;
    private String metadata;
}
