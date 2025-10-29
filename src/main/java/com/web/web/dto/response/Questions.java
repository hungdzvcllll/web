package com.web.web.dto.response;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Questions {
    private Integer id;
    private String question;
    private ArrayList<String> answer;
}
