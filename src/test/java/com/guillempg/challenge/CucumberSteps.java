package com.guillempg.challenge;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.guillempg.challenge.client.ApplicationClient;
import com.guillempg.challenge.config.CompositeRepository;
import com.guillempg.challenge.dto.LightweightStudentDTO;
import com.guillempg.challenge.dto.StudentCourseScoreDTO;
import com.guillempg.challenge.dto.StudentRegistrationDTO;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CucumberSteps
{
    private final ConfigurableApplicationContext context;
    private final ApplicationClient applicationClient;
    private final CompositeRepository wrapperRepository;

    public CucumberSteps(final ConfigurableApplicationContext context,
                         final ApplicationClient applicationClient,
                         final CompositeRepository wrapperRepository)
    {
        this.context = context;
        this.applicationClient = applicationClient;
        this.wrapperRepository = wrapperRepository;
    }

    @After
    @Before
    public void cleanTables()
    {
        wrapperRepository.cleanTables();
    }

    @Given("the application is running")
    public void applicationIsRunning()
    {
        assertTrue(context.isRunning());
    }

    @Then("student named {string} successfully registers into courses {courses}")
    public void studentRegistersIntoCourses(String studentName, List<String> courseNames) {
        final String url = "/students/register";

        final StudentRegistrationDTO studentHttpRequest = new StudentRegistrationDTO()
            .setStudentName(studentName)
            .setCourseNames(courseNames);

        StudentRegistrationDTO expected = new StudentRegistrationDTO()
            .setStudentName(studentName)
            .setCourseNames(courseNames);

        applicationClient.getWebTestClient().post().uri(url)
            .bodyValue(studentHttpRequest)
            .exchange()
            .expectStatus().isOk()
            .expectBody(StudentRegistrationDTO.class).isEqualTo(expected);
    }

    @Then("student named {string} and its registered courses are successfully deleted")
    public void deleteStudent(String studentName){
        final String url = "/students/"+studentName;

        applicationClient.getWebTestClient().delete()
            .uri(url)
            .exchange()
            .expectStatus().isOk();
    }

    @Then("listing all students enrolled into course {string} shows:")
    public void listing_all_students_enrolled_into_course_shows(String courseName,
                                                                List<LightweightStudentDTO> expectedStudents) {
        ParameterizedTypeReference<List<LightweightStudentDTO>> lightweightStudentResponse = new ParameterizedTypeReference<>()
        {
        };

        applicationClient.getWebTestClient().get().uri("/students/list?courseName={name}", courseName)
            .exchange()
            .expectStatus().isOk()
            .expectBody(lightweightStudentResponse).isEqualTo(expectedStudents);
    }

    @Then("student {string} score {string} of course {string} is successfully saved")
    public void student_score_of_course_is_successfully_saved(String studentName,
                                                              String scoreString,
                                                              String courseName) {
        final String url = "/students/score";

        final StudentCourseScoreDTO scoreHttpRequest = new StudentCourseScoreDTO()
            .setStudentName(studentName)
            .setScore(Double.valueOf(scoreString))
            .setCourseName(courseName);

        StudentCourseScoreDTO expected = new StudentCourseScoreDTO()
            .setStudentName(studentName)
            .setScore(Double.valueOf(scoreString))
            .setCourseName(courseName);

        applicationClient.getWebTestClient().post().uri(url)
            .bodyValue(scoreHttpRequest)
            .exchange()
            .expectStatus().isOk()
            .expectBody(StudentCourseScoreDTO.class).isEqualTo(expected);
    }

    @Then("list of students not enrolled in course {string} shows:")
    public void list_of_students_not_enrolled_in_course_shows(String courseName,
                                                              List<LightweightStudentDTO> expectedStudents) {

        ParameterizedTypeReference<List<LightweightStudentDTO>> lightweightStudentResponse = new ParameterizedTypeReference<>()
        {
        };

        applicationClient.getWebTestClient().get().uri("/students/listNotEnrolled?courseName={name}", courseName)
            .exchange()
            .expectStatus().isOk()
            .expectBody(lightweightStudentResponse).isEqualTo(expectedStudents);
    }
}
