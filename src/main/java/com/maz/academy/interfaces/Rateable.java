package com.maz.academy.interfaces;

/**
 * An interface representing any entity that can receive ratings from users.
 * Examples: Course, Instructor, System Feature, etc.
 */

public interface Rateable {
    /**
     * Adds a new rating to this entity.
     *
     * @param rating The rating value (e.g., from 1.0 to 5.0).
     */
    void addRating(Double rating);


    /**
     * Calculates and returns the average of all ratings submitted.
     *
     * @return The average rating value as a double.
     */
    double getAverageRating();
}