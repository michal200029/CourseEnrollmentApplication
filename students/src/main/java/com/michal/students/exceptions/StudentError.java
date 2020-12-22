package com.michal.students.exceptions;

public enum StudentError {
    STUDENT_NOT_FOUND("Student does not exist"),
    STUDENT_EMAIL_CONFLICT("Student's email already exists"),
    STUDENT_IS_NOT_ACTIVE("Student is not active");

    private  String message;

    StudentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
