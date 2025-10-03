package com.web.web.dto.response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassResponse {
    private Integer id;
    private String name;
    private Boolean isPrivate;
}
