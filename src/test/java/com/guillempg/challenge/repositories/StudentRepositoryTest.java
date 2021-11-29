package com.guillempg.challenge.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import com.guillempg.challenge.domain.Course;
import com.guillempg.challenge.domain.CourseRegistration;
import com.guillempg.challenge.domain.Student;
import com.guillempg.challenge.exceptions.CourseNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
class StudentRepositoryTest
{
    private static final String COURSE_A = "COURSE_A";
    private static final String COURSE_B = "COURSE_B";
    private static final String STUDENT_AA = "STUDENT_AA";
    private static final String STUDENT_AB = "STUDENT_AB";
    private static final String STUDENT_BB = "STUDENT_BB";

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @BeforeEach
    @AfterEach
    void clean()
    {
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        courseRegistrationRepository.deleteAll();
    }

    @Test
    void findStudentByCourseName()
    {
        RepositoriesTestUtil.registerStudentToCourse(
            COURSE_A,
            STUDENT_AA,
            courseRepository,
            studentRepository,
            courseRegistrationRepository);

        RepositoriesTestUtil.registerStudentToCourse(
            COURSE_A,
            STUDENT_AB,
            courseRepository,
            studentRepository,
            courseRegistrationRepository);

        final List<Student> studentByCourseName = studentRepository.findStudentByCourseName(COURSE_A);
        assertThat(studentByCourseName.size()).isEqualTo(2);
        assertThat(studentByCourseName.stream().map(Student::getName)).containsExactlyInAnyOrder(STUDENT_AA, STUDENT_AB);
    }

    @Test
    void findStudentsNotEnrolledInCourse()
    {
        RepositoriesTestUtil.registerStudentToCourse(
            COURSE_A,
            STUDENT_AA,
            courseRepository,
            studentRepository,
            courseRegistrationRepository);

        RepositoriesTestUtil.registerStudentToCourse(
            COURSE_A,
            STUDENT_AB,
            courseRepository,
            studentRepository,
            courseRegistrationRepository);

        RepositoriesTestUtil.registerStudentToCourse(
            COURSE_B,
            STUDENT_BB,
            courseRepository,
            studentRepository,
            courseRegistrationRepository);

        final List<Student> studentByCourseName = studentRepository.findStudentsNotEnrolledInCourse(COURSE_B);
        assertThat(studentByCourseName.size()).isEqualTo(2);
        assertThat(studentByCourseName.stream().map(Student::getName)).containsExactlyInAnyOrder(STUDENT_AA, STUDENT_AB);
    }
}