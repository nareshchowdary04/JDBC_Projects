package com.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.student.model.Student;

@Service
public class StudentService {

    private List<Student> students = new ArrayList<>();

    // Register student
    public Student registerStudent(Student student) {

        students.add(student);

        return student;
    }

    // View all students
    public List<Student> getAllStudents() {

        return students;
    }

    // Find student by ID
    public Student getStudentById(int studentId) {

        return students.stream()
                .filter(student -> student.getStudentId() == studentId)
                .findFirst()
                .orElse(null);
    }

    // Delete student registration
    public String deleteStudent(int studentId) {

        Student student = getStudentById(studentId);

        if (student != null) {
            students.remove(student);
            return "Student registration deleted successfully";
        }

        return "Student not found";
    }

    // CUSTOM METHOD 1
    // Find students by course
    public List<Student> getStudentsByCourse(String courseName) {

        return students.stream()
                .filter(student ->
                        student.getCourseName().equalsIgnoreCase(courseName))
                .toList();
    }

    // CUSTOM METHOD 2
    // Find students by name
    public List<Student> getStudentsByName(String studentName) {

        return students.stream()
                .filter(student ->
                        student.getStudentName().equalsIgnoreCase(studentName))
                .toList();
    }

    // CUSTOM METHOD 3
    // Find students older than given age
    public List<Student> getStudentsAboveAge(int age) {

        return students.stream()
                .filter(student -> student.getAge() > age)
                .toList();
    }
}