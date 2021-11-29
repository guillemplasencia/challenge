package com.guillempg.challenge.repositories;

import com.guillempg.challenge.domain.Course;
import com.guillempg.challenge.domain.CourseRegistration;
import com.guillempg.challenge.domain.Student;

public class RepositoriesTestUtil
{
    public static CourseRegistration registerStudentToCourse(String courseName,
                                                       String studentName,
                                                       CourseRepository courseRepository,
                                                       StudentRepository studentRepository,
                                                       CourseRegistrationRepository courseRegistrationRepository)
    {
        final Course course = new Course().setName(courseName);
        courseRepository.save(course);
        final Student student = new Student().setName(studentName);
        studentRepository.save(student);
        final CourseRegistration courseRegistration = new CourseRegistration()
            .setCourse(course)
            .setStudent(student);
        courseRegistrationRepository.save(courseRegistration);
        return courseRegistration;
    }

}
