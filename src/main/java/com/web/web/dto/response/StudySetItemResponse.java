package com.web.web.dto.response;

import lombok.Data;

@Data
public class StudySetItemResponse {
    private Integer id;
    private String concept;
    private String define;
    private Integer studySetId;
    private String imageSource;
}
