package com.guillempg.challenge.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import com.guillempg.challenge.domain.Course;
import com.guillempg.challenge.domain.CourseRegistration;
import com.guillempg.challenge.domain.Student;

import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
class CourseRegistrationRepositoryTest
{
    private static final String COURSE_A = "COURSE_A";
    private static final String STUDENT_A = "STUDENT_A";
    private static final String UNEXISTING_COURSE = "UNEXISTING_COURSE";
    private static final String UNEXISTING_STUDENT = "UNEXISTING_STUDENT";
    private static final String OK = "OK";
    private static final String STUDENT_NOT_FOUND = "STUDENT_NOT_FOUND";
    private static final String COURSE_NOT_FOUND = "COURSE_NOT_FOUND";

    @Autowired
    private DataSource datasource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

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
    void injectedComponentsAreNotNull()
    {
        assertThat(datasource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(courseRegistrationRepository).isNotNull();
    }

    private static Stream<Arguments> studentRegistrationArguments()
    {
        return Stream.of(
            Arguments.of(COURSE_A, STUDENT_A, OK),
            Arguments.of(COURSE_A, UNEXISTING_STUDENT, STUDENT_NOT_FOUND),
            Arguments.of(UNEXISTING_COURSE, STUDENT_A, COURSE_NOT_FOUND)
        );
    }

    @ParameterizedTest
    @MethodSource("studentRegistrationArguments")
    void findByCourseNameIgnoreCaseAndStudentNameIgnoreCase(String courseName, String studentName, String expectedOutcome)
    {
        final CourseRegistration courseRegistration = RepositoriesTestUtil.registerStudentToCourse(
            COURSE_A,
            STUDENT_A,
            courseRepository,
            studentRepository,
            courseRegistrationRepository);

        final Optional<CourseRegistration> result =
            courseRegistrationRepository.findByCourseNameIgnoreCaseAndStudentNameIgnoreCase(courseName, studentName);

        switch(expectedOutcome)
        {
            case OK:
            {
                assertThat(result).isPresent();
                assertThat(result.get()).isEqualTo(courseRegistration);
                break;
            }
            case STUDENT_NOT_FOUND:
            {
                assertThat(result).isEmpty();
            }
            case COURSE_NOT_FOUND:
            {
                assertThat(result).isEmpty();
            }
        }

        clean();
    }
}