package com.guillempg.challenge.dto;

import java.util.List;

import com.guillempg.challenge.domain.Student;

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
public class LightweightStudentDTO
{
    @EqualsAndHashCode.Include
    private String studentName;

    public static List<LightweightStudentDTO> from(final List<Student> students)
    {
        return students.stream()
            .map(s -> new LightweightStudentDTO().setStudentName(s.getName()))
            .toList();
    }
}
