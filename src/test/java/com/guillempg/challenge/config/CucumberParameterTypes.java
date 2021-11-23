package com.guillempg.challenge.config;

import java.util.Arrays;
import java.util.List;

import com.guillempg.challenge.dto.LightweightStudentDTO;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;

public class CucumberParameterTypes
{
    @ParameterType(name = "courses", value = ".*")
    public List<String> courses(String rawCourses)
    {
        return Arrays.stream(rawCourses.split(","))
            .map(String::trim)
            .map(s -> s.replace("\"",""))
            .toList();
    }

    @DataTableType
    @ParameterType(name = "lightweightStudentDTO", value = ".*")
    public LightweightStudentDTO lightweightStudentDTO(String studentName)
    {
        return new LightweightStudentDTO().setStudentName(studentName);
    }
}
