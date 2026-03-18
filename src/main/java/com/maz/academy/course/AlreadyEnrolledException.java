package com.maz.academy.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyEnrolledException extends RuntimeException {
    public AlreadyEnrolledException(){
        super("Already enrolled");
    }

    public AlreadyEnrolledException(String message) {
        super(message);
        
    }
}
