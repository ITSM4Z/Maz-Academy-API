package com.maz.academy.interfaces;

import com.maz.academy.model.Student;
import com.maz.academy.exception.AlreadyEnrolledException;
import com.maz.academy.exception.CourseFullException;
import com.maz.academy.exception.UserNotFoundException;

/**
 * An interface that represents any entity that a student can enroll in or drop from.
 * Examples: Course, Activity, Program, etc.
 */

public interface Enrollable {
    /**
     * Attempts to enroll a student in this entity.
     *
     * @param s The student to be enrolled.
     * @return true if enrollment was successful, false otherwise
     *         (e.g., course is full or student already enrolled).
     */
    boolean enroll(Student s) throws AlreadyEnrolledException, CourseFullException;

    /**
     * Attempts to remove (drop) a student from this entity.
     *
     * @param s The student to be dropped.
     * @return true if the drop operation was successful, false otherwise
     *         (e.g., student is not enrolled).
     */
    boolean drop(Student s) throws UserNotFoundException;
}