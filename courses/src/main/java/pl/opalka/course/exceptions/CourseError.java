package pl.opalka.course.exceptions;

public enum CourseError {
    COURSE_NOT_FOUND("Course did not find"),
    INVALID_START_DATE("Start date is after finish date"),
    INVALID_PARTICIPANTS_VALUE("Participants amount is bigger then participants limit"),
    COURSE_CANNOT_SET_FULL_STATUS("Full status cannot be set"),
    COURSE_CANNOT_SET_ACTIVE_STATUS("Active status cannot be set"),
    COURSE_IS_NOT_ACTIVE("Course is not active"),
    STUDENT_IS_NOT_ACTIVE("Student is not active"),
    STUDENT_IS_ALREADY_ENROLLED("Student is already enrolled for this course"),
    STUDENT_CAN_NOT_BE_ENROLLED("Student can not be enrolled for this course");
    private  String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
