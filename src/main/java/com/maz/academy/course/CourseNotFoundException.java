package com.maz.academy.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(){
        super("Course not found!");
    }
    // Shoutout to Sixth
    public CourseNotFoundException(String message) {
        super(message);
    }
}
