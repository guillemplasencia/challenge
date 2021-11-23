package com.guillempg.challenge.repositories;

import java.util.Optional;

import com.guillempg.challenge.domain.Course;
import com.guillempg.challenge.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>
{
    Optional<Course> findByNameIgnoreCase(String name);
}
