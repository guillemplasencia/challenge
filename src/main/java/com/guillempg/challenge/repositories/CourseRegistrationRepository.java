package com.guillempg.challenge.repositories;

import java.util.Optional;

import com.guillempg.challenge.domain.CourseRegistration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long>
{
    @Query(value = "SELECT r FROM CourseRegistration r INNER JOIN r.course c INNER JOIN r.student s WHERE c.name = ?1 AND s.name = ?2")
    Optional<CourseRegistration> findByCourseNameIgnoreCaseAndStudentNameIgnoreCase(String courseName, String studentName);
}
