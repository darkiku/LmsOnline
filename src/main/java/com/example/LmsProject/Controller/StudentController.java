package com.example.LmsProject.Controller;

import com.example.LmsProject.Entity.Student;
import com.example.LmsProject.Service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/me")
    public ResponseEntity<Student> authenticatedStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student currentStudent = (Student) authentication.getPrincipal();
        return ResponseEntity.ok(currentStudent);
    }

    @GetMapping("/")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.allStudents();
        return ResponseEntity.ok(students);
    }
}