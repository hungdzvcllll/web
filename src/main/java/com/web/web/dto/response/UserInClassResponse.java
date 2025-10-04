package com.web.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInClassResponse {
    private String role;
    private Integer userId;
    private String username;
    private Integer classId;
    private String className;
}
