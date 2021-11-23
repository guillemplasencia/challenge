package com.guillempg.challenge.services;

import java.util.List;

import com.guillempg.challenge.domain.Course;
import com.guillempg.challenge.domain.CourseRegistration;
import com.guillempg.challenge.domain.Student;
import com.guillempg.challenge.dto.StudentRegistrationDTO;
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
        return studentRepository.findStudentByCourseName(courseName);
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
}
