package com.web.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterResponse {
    private Integer id;
    private String name;
    private String solveFile;
    private Integer textBookId;
    private String textBookName;
}
