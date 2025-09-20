package com.web.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudySetItemRequest {
    private String concept;
    private String define;
    private Integer studySetId;
}
