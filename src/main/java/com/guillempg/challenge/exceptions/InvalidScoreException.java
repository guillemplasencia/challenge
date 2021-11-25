package com.guillempg.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidScoreException extends RuntimeException
{
    public InvalidScoreException(final String msg)
    {
        super(msg);
    }
}
