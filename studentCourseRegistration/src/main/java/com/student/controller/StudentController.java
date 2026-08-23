package com.student.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.model.Student;
import com.student.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. Register student
    @PostMapping
    public ResponseEntity<Student> registerStudent(
            @Valid @RequestBody Student student) {

        Student registeredStudent =
                studentService.registerStudent(student);

        return ResponseEntity.ok(registeredStudent);
    }

    // 2. View all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents());
    }

    // 3. Find student by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable int studentId) {

        Student student =
                studentService.getStudentById(studentId);

        if (student != null) {
            return ResponseEntity.ok(student);
        }

        return ResponseEntity.notFound().build();
    }

    // 4. Delete registration
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int studentId) {

        String message =
                studentService.deleteStudent(studentId);

        if (message.equals("Student not found")) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(message);
    }

    // CUSTOM API 1
    // Find students by course
    @GetMapping("/course/{courseName}")
    public ResponseEntity<List<Student>> getStudentsByCourse(
            @PathVariable String courseName) {

        return ResponseEntity.ok(
                studentService.getStudentsByCourse(courseName));
    }

    // CUSTOM API 2
    // Find students by name
    @GetMapping("/name/{studentName}")
    public ResponseEntity<List<Student>> getStudentsByName(
            @PathVariable String studentName) {

        return ResponseEntity.ok(
                studentService.getStudentsByName(studentName));
    }

    // CUSTOM API 3
    // Find students above a particular age
    @GetMapping("/age/{age}")
    public ResponseEntity<List<Student>> getStudentsAboveAge(
            @PathVariable int age) {

        return ResponseEntity.ok(
                studentService.getStudentsAboveAge(age));
    }
}