package pl.opalka.course.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NotificationInfoDTO {
     private List<String> emailsList;
     private String courseCode;
     private String courseName;
     private String courseDescription;
     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
     private LocalDateTime courseStartDate;
     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
     private LocalDateTime courseEndDate;
}
