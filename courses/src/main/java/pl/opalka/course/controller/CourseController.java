package pl.opalka.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.opalka.course.model.Course;
import pl.opalka.course.model.dto.StudentDTO;
import pl.opalka.course.service.CourseService;
import pl.opalka.course.service.StudentServiceClient;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final StudentServiceClient studentServiceClient;
    @Autowired
    public CourseController(CourseService courseService, StudentServiceClient studentServiceClient) {
        this.courseService = courseService;
        this.studentServiceClient = studentServiceClient;
    }
    @GetMapping()
    public List<Course> getCourses(@RequestParam Course.Statuses status){
        return courseService.getCourses(status);
    }
    @GetMapping("/all")
    public List<Course> getAlCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping()
    public void addCourse(@RequestBody  @Valid Course course){
        courseService.addCourse(course);
    }

    @GetMapping("/{code}")
    public Course getCourseByCode(@PathVariable @Valid String code){
        return courseService.getCourseByCode(code);
    }

    @DeleteMapping("/{code}")
    public void deleteCourse(@PathVariable @Valid String code){
        courseService.deleteCourse(code);
    }

    @PostMapping("/enrollment/{courseCode}/{studentId}")
    public ResponseEntity<?> courseEnrollment(@PathVariable String courseCode, @PathVariable Long studentId){
        courseService.courseEnrollment(studentId,courseCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}/members")
    public String getCourseMembers(@PathVariable String code,Model model){
        model.addAttribute("members",courseService.getCourseEnrollmentForCourse(code));
        model.addAttribute("code",code);
        return "members";
    }

    @PostMapping("/{code}/finish-enroll")
    public ResponseEntity<?> courseFinishEnroll(@PathVariable String code){
        courseService.courseFinishEnroll(code);
        return ResponseEntity.ok().build();
    }
}
