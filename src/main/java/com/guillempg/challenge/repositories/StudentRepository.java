package com.guillempg.challenge.repositories;

import java.util.List;

import com.guillempg.challenge.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    void deleteByNameIgnoreCase(String name);

    @Query(
        value = "SELECT s FROM Student s INNER JOIN s.courseRegistrations as r INNER JOIN r.course as c WHERE c.name = ?1 ORDER BY s.name ASC")
    List<Student> findStudentByCourseName(String courseName);
}
