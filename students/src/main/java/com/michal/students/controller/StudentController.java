package com.michal.students.controller;

import com.michal.students.model.Student;
import com.michal.students.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private  final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(@RequestParam(required = false) Student.Status status) {
        return  studentService.getStudents(status);
    }


    @PostMapping
    public Student addStudent(@RequestBody @Valid Student student) {
       return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {

        return studentService.getStudentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
         studentService.deleteStudent(id);
    }


    @PutMapping("/{id}")
    public Student putStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        return studentService.putStudent(id,student);
    }

    @PatchMapping("/{id}")
    public void patchStudent(@PathVariable Long id, @RequestBody Student student) {
         studentService.patchStudent(id,student);
    }

    @PostMapping("/emails")
    public List<Student> getStudentsByEmails(@RequestBody List<String> emails){
        return studentService.getStudentByEmails(emails);
    }
}
