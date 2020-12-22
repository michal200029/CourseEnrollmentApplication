package pl.opalka.course.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.opalka.course.exceptions.CourseError;
import pl.opalka.course.exceptions.CourseException;
import pl.opalka.course.model.Course;
import pl.opalka.course.model.CourseMembers;
import pl.opalka.course.model.dto.NotificationInfoDTO;
import pl.opalka.course.model.dto.StudentDTO;
import pl.opalka.course.respository.CourseRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient, RabbitTemplate rabbitTemplate) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public List<Course> getCourses(Course.Statuses status) {
         if(status==null)
             return courseRepository.findAll();
         else
             return courseRepository.findAllByStatus(status);
    }

    @Override
    public List<Course> getAllCourses() {
            return courseRepository.findAll();
    }


    @Override
    public Course getCourseByCode(String code) {
        return courseRepository.findById(code)
                .orElseThrow(()-> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course addCourse(Course course) {
        course.mainValidate();
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String code) {
        Course course = courseRepository.findById(code)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
        course.setStatus(Course.Statuses.INACTIVE);
        courseRepository.save(course);
    }

    @Override
    public void courseEnrollment(Long studentId, String courseCode) {
        Course course = getCourseByCode(courseCode);
        validateCourseStatus(course);

        StudentDTO student = studentServiceClient.getStudentById(studentId); //powienien active ale w razie w jeszcze raz validacja
        validateStudentStatusBeforeEnrollment(course, student);
        course.increaseParticipants();
        course.getCourseMembersList().add(new CourseMembers(student.getEmail()));

        courseRepository.save(course);

    }

    @Override
    public List<StudentDTO> getCourseEnrollmentForCourse(String courseCode) {
        Course course = getCourseByCode(courseCode);
        List<@NotNull String> emailsMembers =  course.getCourseMembersList()
                .stream()
                .map(CourseMembers::getEmail)
                .collect(Collectors.toList());
        return studentServiceClient.getStudentsByEmails(emailsMembers);
    }

    public void courseFinishEnroll(String curseCode) {
        Course course= getCourseByCode(curseCode);
        if(Course.Statuses.INACTIVE.equals(course.getStatus()))
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        course.setStatus(Course.Statuses.INACTIVE);
        courseRepository.save(course);
        List<@NotNull String> emailsMembers =  course.getCourseMembersList()
                .stream()
                .map(CourseMembers::getEmail)
                .collect(Collectors.toList());
        NotificationInfoDTO notificationInfoDTO = NotificationInfoDTO.builder()
                .courseCode(course.getCourseCode())
                .courseName(course.getName())
                .courseDescription(course.getDescription())
                .courseEndDate(course.getEndDate())
                .courseStartDate(course.getStartDate())
                .emailsList(emailsMembers).build();
        rabbitTemplate.convertAndSend("enrollFinish",notificationInfoDTO);

    }


    private void validateStudentStatusBeforeEnrollment(Course course, StudentDTO student) {
        if(!StudentDTO.Status.ACTIVE.equals(student.getStatus()))
            throw new CourseException(CourseError.STUDENT_IS_NOT_ACTIVE);
        if(course.getCourseMembersList()
                .stream()
                .anyMatch(member -> student.getEmail().equals(member.getEmail())))
                throw new CourseException(CourseError.STUDENT_IS_ALREADY_ENROLLED);
    }

    private void validateCourseStatus(Course course) {
        if(!Course.Statuses.ACTIVE.equals(course.getStatus()))
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
    }

}
