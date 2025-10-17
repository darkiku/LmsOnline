package com.example.LmsProject.Controller;

import com.example.LmsProject.Dto.CourseDto;
import com.example.LmsProject.Entity.Course;
import com.example.LmsProject.Entity.Enrollment;
import com.example.LmsProject.Entity.Student;
import com.example.LmsProject.Service.CourseService;
import com.example.LmsProject.Facade.StudentPortalFacade;
import com.example.LmsProject.Service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentPortalFacade studentPortalFacade;

    @GetMapping("/")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(201).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(course, id);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/enroll")
    public ResponseEntity<Enrollment> enrollInCourse(@RequestBody CourseDto courseDTO) {
        Enrollment enrollment = enrollmentService.enrollStudent(
                courseDTO.getCourseId(),
                courseDTO.isWithCertificate(),
                courseDTO.isWithMentorSupport(),
                courseDTO.isWithGamification()
        );
        return ResponseEntity.status(201).body(enrollment);
    }

    @GetMapping("/my-enrollments")
    public ResponseEntity<List<Enrollment>> getMyEnrollments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = (Student) authentication.getPrincipal();
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(student.getId());
        return ResponseEntity.ok(enrollments);
    }

    @PutMapping("/complete/{enrollmentId}")
    public ResponseEntity<Enrollment> completeCourse(@PathVariable Long enrollmentId) {
        Enrollment enrollment = enrollmentService.completeCourse(enrollmentId);
        return ResponseEntity.ok(enrollment);
    }
}
