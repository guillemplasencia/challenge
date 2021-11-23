package com.guillempg.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException
{
    public StudentNotFoundException(final String msg)
    {
        super(msg);
    }
}
