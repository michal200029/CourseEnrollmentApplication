package pl.opalka.course.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.opalka.course.exceptions.CourseError;
import pl.opalka.course.exceptions.CourseException;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Document
@Setter
@Getter
public class Course {
    @Id
    private String courseCode;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Future
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;
    @Min(0)
    private int participantsLimit;
    @NotNull
    @Min(0)
    private int participantsNumber;
    @NotNull
    private Statuses status;

    private List<CourseMembers> courseMembersList = new ArrayList<>();


    public enum Statuses {
        ACTIVE,
        INACTIVE,
        FULL
    }


    private void validateCourseDate(){
        if(startDate.isAfter(endDate))
            throw new CourseException(CourseError.INVALID_START_DATE);
    }

    private void validateParticipationsLimit(){
        if(participantsLimit<participantsNumber)
            throw new CourseException(CourseError.INVALID_PARTICIPANTS_VALUE);
    }

    private void validateFullStatus(){
        if(Statuses.FULL.equals(status) && participantsLimit<=participantsNumber)
            throw new CourseException(CourseError.COURSE_CANNOT_SET_FULL_STATUS);
        if(Statuses.ACTIVE.equals(status) && participantsLimit<=participantsNumber)
            throw new CourseException(CourseError.COURSE_CANNOT_SET_ACTIVE_STATUS);
    }

    public void increaseParticipants(){
        participantsNumber++;
        if(participantsNumber==participantsLimit)
            setStatus(Statuses.FULL);
    }

    public void mainValidate(){
        validateCourseDate();
        validateParticipationsLimit();
        validateFullStatus();

    }




}
