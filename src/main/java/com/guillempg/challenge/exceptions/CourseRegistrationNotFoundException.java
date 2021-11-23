package com.guillempg.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseRegistrationNotFoundException extends RuntimeException
{
    public CourseRegistrationNotFoundException(String msg)
    {
        super(msg);
    }
}
