package com.web.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInClassResponse {
    private String role;
    private String status;
    private Integer userId;
    private Integer classId;
}
