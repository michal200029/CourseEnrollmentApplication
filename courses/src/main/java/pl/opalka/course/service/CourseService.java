package pl.opalka.course.service;

import pl.opalka.course.model.Course;
import pl.opalka.course.model.CourseMembers;
import pl.opalka.course.model.dto.StudentDTO;

import java.util.List;

public interface CourseService {
    List <Course> getCourses(Course.Statuses status);
    Course getCourseByCode(String code);
    Course addCourse(Course course);
    void deleteCourse(String code);
    List <Course> getAllCourses();
    void courseEnrollment(Long studentId, String courseCode );
    List<StudentDTO> getCourseEnrollmentForCourse(String courseCode);
    void courseFinishEnroll(String curseCode);

}
