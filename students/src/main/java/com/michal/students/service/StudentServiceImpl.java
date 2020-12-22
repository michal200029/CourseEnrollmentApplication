package com.michal.students.service;

import com.michal.students.exceptions.StudentError;
import com.michal.students.exceptions.StudentException;
import com.michal.students.model.Student;
import com.michal.students.repository.StudentRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(Student.Status status) {
        if(status!=null){
            return studentRepository.findAllByStatus(status);
        }
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        validateStudentEmailExists(student);
        return studentRepository.save(student);

    }

    public Student getStudentById(Long id) {
        Student student= studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if(!Student.Status.ACTIVE.equals(student.getStatus()))
            throw new StudentException(StudentError.STUDENT_IS_NOT_ACTIVE);
        return student;
    }



    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        student.setStatus(Student.Status.INACTIVE);
        studentRepository.save(student);
    }

    public Student putStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    validateStudentEmailExists(student);
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setEmail(student.getEmail());
                    studentFromDb.setStatus(student.getStatus());
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(() ->new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    public Student patchStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!StringUtils.isEmpty(student.getFirstName())) {
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())) {
                        studentFromDb.setLastName(student.getLastName());
                    }
                    if (!StringUtils.isEmpty(student.getStatus())) {
                        studentFromDb.setStatus(student.getStatus());
                    }
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(() ->new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    public List<Student> getStudentByEmails(List<String> emails) {
        return studentRepository.findAllByEmailIn(emails);
    }

    private void validateStudentEmailExists(Student student){
        if(studentRepository.existsByEmail(student.getEmail()))
            throw new  StudentException(StudentError.STUDENT_EMAIL_CONFLICT);
    }
}
