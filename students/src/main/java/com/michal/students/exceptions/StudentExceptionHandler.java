package com.michal.students.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleExpection(StudentException e) {
        HttpStatus httpStatus;
        switch (e.getStudentError()){
            case  STUDENT_NOT_FOUND:
                    httpStatus = HttpStatus.NOT_FOUND;
            case STUDENT_EMAIL_CONFLICT:
                httpStatus = HttpStatus.CONFLICT;
            case STUDENT_IS_NOT_ACTIVE:
                httpStatus = HttpStatus.BAD_REQUEST;
            default: httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        }

        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getStudentError().getMessage()));
    }
}