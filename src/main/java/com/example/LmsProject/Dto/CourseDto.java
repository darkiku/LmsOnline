package com.example.LmsProject.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {
    private Long courseId;
    private boolean withCertificate;
    private boolean withMentorSupport;
    private boolean withGamification;
}
