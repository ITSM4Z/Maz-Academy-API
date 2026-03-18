package com.maz.academy.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("The user was not found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}