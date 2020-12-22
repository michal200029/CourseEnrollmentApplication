package com.opalka.notification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@ToString
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
