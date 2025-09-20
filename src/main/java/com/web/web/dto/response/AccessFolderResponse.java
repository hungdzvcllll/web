package com.web.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessFolderResponse {
    private Integer userId;
    private Integer folderId;
    private String role;
}
