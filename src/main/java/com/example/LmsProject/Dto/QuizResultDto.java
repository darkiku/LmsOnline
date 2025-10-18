package com.example.LmsProject.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizResultDto {
    private Integer score;
    private Integer totalPoints;
    private Boolean passed;
    private String message;
}