package com.maz.academy.course;

/**
 * a custom checked Exception to use when the course is full.
 * Extending Exception to use the try-catch.
 * usage is when someone tries to enroll but the course is full.
 */

public class AlreadyEnrolledException extends Exception {
    //default message
    public AlreadyEnrolledException(){
        super("Already enrolled");
    }
    /**
     * custom message Using super to pass the message to the parent class which is Exception,
     * storing it and using it later.
     * to implement: throw new (name of class) pass message example ("hello") for default message ()
     */
    public AlreadyEnrolledException(String message) {
        super(message);
        
    }
}
