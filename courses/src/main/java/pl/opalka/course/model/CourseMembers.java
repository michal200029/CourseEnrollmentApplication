package pl.opalka.course.model;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class CourseMembers {
    @NotNull
    private LocalDateTime enrollmentDate;
    @NotBlank
    private String email;


    public CourseMembers(@NotBlank String email) {
        this.enrollmentDate = LocalDateTime.now();
        this.email = email;
    }
}
