package com.example.LmsProject.Dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class QuizSubmissionDto {
    private Long quizId;
    private Long enrollmentId;
    private Map<Long, Long> answers;
}
