package com.guillempg.challenge.services;

import java.util.List;

import com.guillempg.challenge.domain.Course;
import com.guillempg.challenge.domain.CourseRegistration;
import com.guillempg.challenge.domain.Student;
import com.guillempg.challenge.dto.StudentCourseScoreDTO;
import com.guillempg.challenge.dto.StudentRegistrationDTO;
import com.guillempg.challenge.exceptions.CourseNotFoundException;
import com.guillempg.challenge.exceptions.CourseRegistrationNotFoundException;
import com.guillempg.challenge.exceptions.StudentNotFoundException;
import com.guillempg.challenge.repositories.CourseRegistrationRepository;
import com.guillempg.challenge.repositories.CourseRepository;
import com.guillempg.challenge.repositories.StudentRepository;

import org.springframework.transaction.annotation.Transactional;

public class StudentService
{
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    public StudentService(final StudentRepository studentRepository,
                          final CourseRepository courseRepository,
                          final CourseRegistrationRepository courseRegistrationRepository)
    {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public List<Student> listEnrolledStudents(final String courseName)
    {
        final Course course =
            courseRepository.findByNameIgnoreCase(courseName).orElseThrow(() -> new CourseNotFoundException(String.format("Course ",
                courseName, " not found")));

        return studentRepository.findStudentByCourseName(course.getName());
    }

    @Transactional
    public void deleteStudent(final String studentName)
    {
        studentRepository.deleteByNameIgnoreCase(studentName);
    }

    @Transactional
    public Student registerStudent(final StudentRegistrationDTO registrationRequest)
    {
        final Student student = new Student()
            .setName(registrationRequest.getStudentName());

        final List<CourseRegistration> courseRegistrations = findOrCreateCourses(
            courseRepository,
            registrationRequest.getCourseNames()).stream()
            .map(course -> new CourseRegistration().setCourse(course).setStudent(student))
            .toList();

        student.setCourseRegistrations(courseRegistrations);

        return studentRepository.save(student);
    }

    private List<Course> findOrCreateCourses(final CourseRepository courseRepository, final List<String> courseNames)
    {
        return courseNames.stream()
            .map(courseName -> courseRepository.findByNameIgnoreCase(courseName)
                .orElseGet(() -> courseRepository.save(new Course().setName(courseName))))
            .toList();
    }

    public CourseRegistration score(final StudentCourseScoreDTO scoreRequest)
    {
        final Course course =
            courseRepository.findByNameIgnoreCase(scoreRequest.getCourseName())
                .orElseThrow(() -> new CourseNotFoundException(String.format("Course ",
                scoreRequest.getCourseName(), " not found")));

        final Student student =
            studentRepository.findStudentByNameIgnoreCase(scoreRequest.getStudentName())
                .orElseThrow(() -> new StudentNotFoundException(String.format("Student ",
                    scoreRequest.getStudentName(), " not found")));

        final CourseRegistration courseRegistration =
            courseRegistrationRepository.findByCourseNameIgnoreCaseAndStudentNameIgnoreCase(course.getName(), student.getName())
            .orElseThrow(() -> new CourseRegistrationNotFoundException(String.format("Course registration for Student ",
                scoreRequest.getStudentName(), " into course ", scoreRequest.getCourseName(), "not found")));

        courseRegistration.setScore(scoreRequest.getScore());
        courseRegistrationRepository.save(courseRegistration);
        return courseRegistration;
    }
}
