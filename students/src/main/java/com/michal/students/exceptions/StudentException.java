package com.michal.students.exceptions;

import org.springframework.beans.factory.annotation.Autowired;

public class StudentException extends RuntimeException {

    private StudentError studentError;
    @Autowired
    public StudentException(StudentError studentError){
        this.studentError=studentError;
    }

    public StudentError getStudentError() {
        return studentError;
    }
}
