package com.example.LmsProject.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQuizDto {
    private String title;
    private Integer passingScore;
    private Long courseId;
}