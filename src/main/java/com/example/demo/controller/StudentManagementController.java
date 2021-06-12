package com.example.demo.controller;

import com.example.demo.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Rashed Alam"),
            new Student(2, "Rashed"),
            new Student(3, "Test")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        System.out.println("getAllStudents");
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println("registerNewStudent");
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable int studentId) {
        System.out.println("deleteStudent");
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable int studentId, @RequestBody Student student) {
        System.out.println("updateStudent");
    }

}
