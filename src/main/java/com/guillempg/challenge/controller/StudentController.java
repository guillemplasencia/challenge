package com.guillempg.challenge.controller;

import java.util.List;

import com.guillempg.challenge.domain.Student;
import com.guillempg.challenge.dto.LightweightStudentDTO;
import com.guillempg.challenge.dto.StudentRegistrationDTO;
import com.guillempg.challenge.services.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private final StudentService studentService;

    public StudentController(final StudentService studentService)
    {
        this.studentService = studentService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<StudentRegistrationDTO> registerStudent(@RequestBody StudentRegistrationDTO registrationRequest)
    {
        final var savedStudent = studentService.registerStudent(registrationRequest);
        final var resp = StudentRegistrationDTO.from(savedStudent);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("{studentName}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String studentName)
    {
        studentService.deleteStudent(studentName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<LightweightStudentDTO>> listEnrolledStudents(@RequestParam String courseName)
    {
        final List<LightweightStudentDTO> lightweightStudentDTOS = studentService.listEnrolledStudents(courseName).stream()
            .map(student -> new LightweightStudentDTO()
                .setStudentName(student.getName()))
            .toList();

        return new ResponseEntity<>(lightweightStudentDTOS, HttpStatus.OK);
    }
}
