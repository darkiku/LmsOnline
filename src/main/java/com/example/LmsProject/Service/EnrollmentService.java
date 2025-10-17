package com.example.LmsProject.Service;

import com.example.LmsProject.Entity.Enrollment;
import com.example.LmsProject.Entity.Student;
import com.example.LmsProject.Entity.Course;
import com.example.LmsProject.Repository.EnrollmentRepository;
import com.example.LmsProject.Repository.StudentRepository;
import com.example.LmsProject.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentService {
   @Autowired
   CourseRepository courseRepository;
   @Autowired
   StudentRepository studentRepository;
   @Autowired
   EnrollmentRepository enrollmentRepository;
    public Enrollment enrollStudent(Long courseId, boolean withCertificate, boolean withMentorSupport, boolean withGamification) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setWithCertificate(withCertificate);
        enrollment.setWithMentorSupport(withMentorSupport);
        enrollment.setWithGamification(withGamification);
        enrollment.setCompleted(false);
        enrollment.setEnrolledAt(LocalDateTime.now());

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }


    public Enrollment completeCourse(Long courseId) {
        Enrollment enrollment = enrollmentRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        enrollment.setCompleted(true);
        enrollment.setCompletedAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }

}