package com.maz.academy.exception;

/**
 * A custom checked Exception to use when something is not found.
 * extending Exception to use it with try-catch.
 * usage is when searching for a user that doesn't exist.
 */

public class UserNotFoundException extends Exception {
    // default message
    public UserNotFoundException(){
        super("The user was not found.");
    }
    /**
     * custom message using super to pass the message to the parent class which is Exception,
     * storing it and using it later.
     * to implement: throw new (name of class) pass message example ("hello") for default message ()
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}