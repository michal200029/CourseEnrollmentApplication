package com.michal.students.service;

import com.michal.students.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(Student.Status status);
    Student addStudent(Student student);
    Student getStudentById(Long id);
    void deleteStudent(Long id);
    Student putStudent(Long id,Student student);
    Student patchStudent(Long id,Student student);
    List<Student> getStudentByEmails(List<String> emails);
}
