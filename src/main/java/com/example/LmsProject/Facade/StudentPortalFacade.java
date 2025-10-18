package com.example.LmsProject.Facade;

import com.example.LmsProject.Decorator.CertificateDecorator;
import com.example.LmsProject.Decorator.GamificationDecorator;
import com.example.LmsProject.Decorator.MentorSupportDecorator;
import com.example.LmsProject.Entity.Course;
//import com.example.LmsProject.Pattern.*;
import com.example.LmsProject.Entity.Student;
import com.example.LmsProject.Service.CourseService;
import com.example.LmsProject.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentPortalFacade {
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    CourseService courseService;
    public void enrollInCourse(Long id, boolean withCertificate, boolean withMentorSupport,boolean withGamification) {
        Course course = courseService.getCourseById(id);
        if (withCertificate) {
            course = new CertificateDecorator(course);
        }
        if (withMentorSupport) {
            course = new MentorSupportDecorator(course);
        }
        if (withGamification) {
            course = new GamificationDecorator(course);
        }
        course.deliverContent();
        System.out.println("enrolled in" + course.getCourseName());
    }}