package pl.opalka.course.exceptions;

import org.springframework.beans.factory.annotation.Autowired;

public class CourseException  extends RuntimeException {
    private CourseError courseError;
    @Autowired
    public CourseException(CourseError courseError) {
        this.courseError = courseError;
    }

    public CourseError getCourseError() {
        return courseError;
    }
}
