package com.guillempg.challenge.config;

import java.time.Duration;

import com.guillempg.challenge.client.ApplicationClient;
import com.guillempg.challenge.client.ApplicationClientImpl;
import com.guillempg.challenge.repositories.CourseRegistrationRepository;
import com.guillempg.challenge.repositories.CourseRepository;
import com.guillempg.challenge.repositories.StudentRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;

@Lazy
@TestConfiguration
public class TestConfig
{
    @Bean
    public TestRestTemplate applicationRestTemplate(@Value("${local.server.port}") int localServerPort)
    {
        return new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:" + localServerPort));
    }

    @Bean
    public WebTestClient webTestClient(@Value("${local.server.port}") int localServerPort)
    {
        return WebTestClient.bindToServer(new ReactorClientHttpConnector())
            .baseUrl("http://localhost:" + localServerPort)
            .responseTimeout(Duration.ofSeconds(10))
            .build();
    }

    @Bean
    public ApplicationClient applicationClient(final WebTestClient webTestClient)
    {
        return new ApplicationClientImpl(webTestClient);
    }

    @Bean
    public CompositeRepository wrapperRepository(CourseRegistrationRepository registrationRepository,
                                                 StudentRepository studentRepository,
                                                 CourseRepository courseRepository)
    {
        return () ->
        {
            registrationRepository.deleteAll();
            studentRepository.deleteAll();
            courseRepository.deleteAll();
        };
    }
}
