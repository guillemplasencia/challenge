package com.guillempg.challenge.dto;

import com.guillempg.challenge.domain.CourseRegistration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
public class StudentCourseScoreDTO
{
    @EqualsAndHashCode.Include
    private String studentName;

    @EqualsAndHashCode.Include
    private String courseName;

    @EqualsAndHashCode.Include
    private Double score;

    public static StudentCourseScoreDTO from(final CourseRegistration savedScore)
    {
        return new StudentCourseScoreDTO()
            .setStudentName(savedScore.getStudent().getName())
            .setCourseName(savedScore.getCourse().getName())
            .setScore(savedScore.getScore());
    }
}
