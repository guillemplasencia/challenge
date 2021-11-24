package com.guillempg.challenge.repositories;

import java.util.List;
import java.util.Optional;

import com.guillempg.challenge.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    Integer deleteByNameIgnoreCase(String name);

    Optional<Student> findStudentByNameIgnoreCase(String name);

    @Query(
        value = "SELECT s FROM Student s INNER JOIN s.courseRegistrations as r INNER JOIN r.course c WHERE c.name = ?1 ORDER BY s.name ASC")
    List<Student> findStudentByCourseName(String courseName);

    @Query(
        value = "SELECT s FROM Student s WHERE s.id not in (SELECT s2.id FROM Student s2 INNER JOIN s2.courseRegistrations r INNER JOIN r.course c where upper(c.name)=upper(?1))")
    List<Student> findStudentsNotEnrolledInCourse(String courseName);
}
