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
    }
//    public void demonstratePatterns(){
//        System.out.println("\n========================================");
//        System.out.println(" LMS DESIGN PATTERNS DEMONSTRATION");
//        System.out.println("========================================\n");
//        System.out.println(" SCENARIO 1: Student enrolls in Math Course");
//        System.out.println("Selected options: Mentor Support + Certificate\n");
//        com.example.LmsProject.Pattern.Course mathCourse = new MathCourse();
//        mathCourse = new com.example.LmsProject.Pattern.MentorSupportDecorator(mathCourse);
//        mathCourse = new com.example.LmsProject.Pattern.GamificationDecorator(mathCourse);
//        EnrollInCourse(mathCourse);
//        startLearning(mathCourse);
//        completeCourse(mathCourse);
//        System.out.println("\n----------------------------------------\n");
//        System.out.println("SCENARIO 2: Student enrolls in Programming Course");
//        System.out.println("Selected options: Gamification\n");
//        com.example.LmsProject.Pattern.Course progCourse = new ProgramingCourse();
//        progCourse = new com.example.LmsProject.Pattern.GamificationDecorator(progCourse);
//        progCourse = new com.example.LmsProject.Pattern.MentorSupportDecorator(progCourse);
//        EnrollInCourse(progCourse);
//        startLearning(progCourse);
//        completeCourse(progCourse);
//        System.out.println("\n========================================");
//        System.out.println("Demonstration completed successfully!");
//        System.out.println("========================================\n");
//    }
//    public void EnrollInCourse(com.example.LmsProject.Pattern.Course course) {
//        System.out.println("ENROLLING IN COURSE");
//        System.out.println("Course: " + course.getCourseName());
//        System.out.println("Features included:");
//        for (String feature : course.getFeatures()) {
//            System.out.println("  • " + feature);
//        }System.out.println();
//    }
//    public void startLearning(com.example.LmsProject.Pattern.Course course) {
//        System.out.println("STARTING LEARNING");
//        System.out.println(course.deliverContent());
//        System.out.println();
//    }
//    public void completeCourse(com.example.LmsProject.Pattern.Course course) {
//        System.out.println("COMPLETED COURSE");
//        System.out.println("you succsesfully completed: " + course.getCourseName());
//        System.out.println("All features unlocked: " + course.getFeatures());
//    }
}