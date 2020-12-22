package pl.opalka.course.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pl.opalka.course.model.dto.StudentDTO;

import java.util.List;


@FeignClient(name = "STUDENT-SERVICE") //eureka
@RequestMapping("/students")
public interface StudentServiceClient {
    @GetMapping("/{studentId}")
    StudentDTO getStudentById(@PathVariable Long studentId);

    @PostMapping("/emails")
    List<StudentDTO> getStudentsByEmails(@RequestBody List<String> emails);
}
