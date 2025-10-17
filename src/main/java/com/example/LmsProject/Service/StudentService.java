package com.example.LmsProject.Service;

import com.example.LmsProject.Entity.Student;
import com.example.LmsProject.Repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> allStudents() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void updateStudent(Student student, long id) {
        Student studentToUpdate = studentRepository.findById(id).orElse(null);
        if (studentToUpdate != null) {
            studentToUpdate.setFirstName(student.getFirstName());
            studentToUpdate.setLastName(student.getLastName());
            studentRepository.save(studentToUpdate);
        }
    }
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }
}