package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionTheStringIsNull extends RuntimeException{
    public ExceptionTheStringIsNull() {
        super("The string is required");
    }
}
