package com.guillempg.challenge.dto;

import java.util.List;

import com.guillempg.challenge.domain.Course;
import com.guillempg.challenge.domain.CourseRegistration;
import com.guillempg.challenge.domain.Student;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
public class StudentRegistrationDTO
{
    private Long studentId;

    @EqualsAndHashCode.Include
    private String studentName;

    @EqualsAndHashCode.Include
    private List<String> courseNames;

    public static StudentRegistrationDTO from(final Student savedStudent)
    {
        final List<String> courseNames = savedStudent.getCourseRegistrations().stream().map(cr -> cr.getCourse().getName()).toList();

        return new StudentRegistrationDTO()
            .setStudentName(savedStudent.getName())
            .setCourseNames(courseNames);
    }
}

