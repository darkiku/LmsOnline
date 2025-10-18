package com.example.LmsProject.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAnswerDto {
    private Long questionId;
    private String answerText;
    private Boolean isCorrect;
}