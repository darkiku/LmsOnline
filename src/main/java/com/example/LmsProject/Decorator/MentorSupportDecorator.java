package com.example.LmsProject.Decorator;


import com.example.LmsProject.Entity.Course;

public class MentorSupportDecorator extends Course {
    private Course course;
    public MentorSupportDecorator(Course course) {
        this.course = course;
    }
    @Override
    public String getCourseName(){
        return course.getCourseName()+ "(with Mentor support)";
    }
    @Override
    public void deliverContent() {
        course.deliverContent();
        System.out.println("Mentor will support you");
    }

}
