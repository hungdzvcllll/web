package com.web.web.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VipUserResponse {
    private Integer id;
    private LocalDateTime expired;
    private Integer payValue;
    private Integer userId;
    private String userName;
    private String locale;
}
