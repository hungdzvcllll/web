package com.web.web.dto.request;

import org.hibernate.internal.build.AllowNonPortable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {
    private String name;
    private Integer studySetId;
}
