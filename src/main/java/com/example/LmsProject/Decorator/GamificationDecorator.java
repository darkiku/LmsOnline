package com.example.LmsProject.Decorator;

import com.example.LmsProject.Entity.Course;

public class GamificationDecorator extends Course {
    private Course course;
    public GamificationDecorator(Course course) {
        this.course = course;
    }
    @Override
    public void deliverContent() {
        course.deliverContent();
        System.out.println("Gamification added suiii");
    }
    @Override
    public String getCourseName() {
        return course.getCourseName()+ "(with Gamification)";
    }
}