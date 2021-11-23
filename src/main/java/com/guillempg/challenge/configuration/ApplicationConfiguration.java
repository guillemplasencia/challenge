package com.guillempg.challenge.configuration;

import com.guillempg.challenge.repositories.CourseRegistrationRepository;
import com.guillempg.challenge.repositories.CourseRepository;
import com.guillempg.challenge.repositories.StudentRepository;
import com.guillempg.challenge.services.StudentService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration
{
    @Bean
    public StudentService studentService(final StudentRepository studentRepository,
                                         final CourseRepository courseRepository,
                                         final CourseRegistrationRepository courseRegistrationRepository){
        return new StudentService(studentRepository, courseRepository, courseRegistrationRepository);
    }
}
