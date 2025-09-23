package com.web.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {
    private Integer id;
    private String name;
    private String link;
    private Integer successPercent;
    private Integer studySetId;
    private Integer userId;
}
