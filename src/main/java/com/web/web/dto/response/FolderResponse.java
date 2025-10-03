package com.web.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderResponse {
    private Integer id;
    private String name;
    private Boolean isPrivate;
    private Integer classId;
}
