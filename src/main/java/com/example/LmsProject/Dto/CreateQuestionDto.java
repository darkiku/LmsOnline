package com.example.LmsProject.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQuestionDto {
    private Long quizId;
    private String questionText;
    private Integer points;
}