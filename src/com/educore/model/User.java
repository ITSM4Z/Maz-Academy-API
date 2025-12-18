package com.educore.model;

import com.educore.enums.UserRole;
import com.educore.service.Platform;
import com.educore.exception.UserNotFoundException;

import java.io.Serializable;

/**
 * An abstract class representing a generic user of the system.
 * Different user types such as Student, Instructor, or Admin will extend this class.
 */

public abstract class User {
    /** A unique identifier for each user in the system. */
    protected int userId;
    /** The user's full name. */
    protected String name;
    /** The user's email address. */
    protected String email;
    /** The user's role. */
    protected UserRole userRole;

    /**
     * Constructor used to initialize the basic information of a user.
     *
     * @param userId The user's unique ID.
     * @param name   The user's name.
     * @param email  The user's email address.
     * @param userRole The user's role.
     */
    public User(int userId, String name, String email, UserRole userRole) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }

    /** @return The user's ID. */
    public int getUserId() {
        return userId;
    }
    /** Sets a new ID for the user. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** @return The user's name. */
    public String getName() {
        return name;
    }
    /** Updates the user's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return The user's email. */
    public String getEmail() {
        return email;
    }
    /** Updates the user's email address. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return The user's role. */
    public UserRole getUserRole(){
        return userRole;
    }
    /** Updates the user's role. */
    public void setUserRole(UserRole userRole){
        this.userRole = userRole;
    }

    /**
     * An abstract method that must be implemented by any subclass.
     * Its purpose is to display the appropriate dashboard for each user type.
     * For example:
     * - A student will see enrolled courses, schedule, etc.
     * - An instructor will see their courses and enrolled students.
     */
    public abstract void displayDashboard(Platform platform) throws UserNotFoundException;

    /** @return The user's info */
    public String toString() {
        return String.format("%s (%d). Email: %s Role: %s", name, userId, email, userRole);
    }
}