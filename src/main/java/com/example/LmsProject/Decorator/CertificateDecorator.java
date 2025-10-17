package com.example.LmsProject.Decorator;

import com.example.LmsProject.Entity.Course;

public class CertificateDecorator extends Course {
    private Course course;
    public CertificateDecorator(Course course) {
        this.course = course;
    }
    @Override
    public void deliverContent() {
        course.deliverContent();
        System.out.println("You want some certificate? finish course bro");
    }

    @Override
    public String getCourseName(){
        return course.getCourseName()+ "(with Certificate)";
    }
}
