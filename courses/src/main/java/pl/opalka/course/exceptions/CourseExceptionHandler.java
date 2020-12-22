package pl.opalka.course.exceptions;

import feign.FeignException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorInfo> handleExpection(CourseException e) {
        HttpStatus httpStatus;
        switch (e.getCourseError()){
            case COURSE_NOT_FOUND:
                httpStatus = HttpStatus.NOT_FOUND;
            case COURSE_CANNOT_SET_FULL_STATUS:
                httpStatus = HttpStatus.BAD_REQUEST;
            case INVALID_PARTICIPANTS_VALUE:
                httpStatus = HttpStatus.CONFLICT;
            case INVALID_START_DATE:
                httpStatus = HttpStatus.BAD_REQUEST;
            case COURSE_CANNOT_SET_ACTIVE_STATUS:
                httpStatus = HttpStatus.CONFLICT;
            case COURSE_IS_NOT_ACTIVE:
                httpStatus = HttpStatus.BAD_REQUEST;
            case STUDENT_IS_NOT_ACTIVE:
                httpStatus = HttpStatus.BAD_REQUEST;
            case STUDENT_IS_ALREADY_ENROLLED:
                httpStatus = HttpStatus.CONFLICT;
            case STUDENT_CAN_NOT_BE_ENROLLED:
                httpStatus = HttpStatus.BAD_REQUEST;
            default:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getCourseError().getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeingExpection(FeignException e) {
        return ResponseEntity.status(e.status()).body(new JSONObject(e.contentUTF8()).toMap());
    }


}
