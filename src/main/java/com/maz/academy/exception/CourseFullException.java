package com.maz.academy.exception;

/**
 * a custom checked Exception to use when the course is full.
 * Extending Exception to use the try-catch.
 * usage is when someone tries to enroll but the course is full.
 */

public class CourseFullException extends Exception {
    // default message
    public CourseFullException() {
        super("Course is full");
    }
    /**
     * custom message using super to pass the message to the parent class which is Exception,
     * storing it and using it later.
     * to implement: throw new (name of class) pass message example ("hello") for default message ()
     */
    public CourseFullException(String message) {
        super(message);
    }
}
