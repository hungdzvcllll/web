package com.web.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudySetResponse {
    private Integer id;
    private String name;
    private String link;
    private Integer folderId;
}
