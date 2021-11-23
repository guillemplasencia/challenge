package com.guillempg.challenge.repositories;

import com.guillempg.challenge.domain.CourseRegistration;
import com.guillempg.challenge.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long>
{
}
