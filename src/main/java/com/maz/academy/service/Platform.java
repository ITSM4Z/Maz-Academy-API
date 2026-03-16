package com.maz.academy.service;

import com.maz.academy.model.*;
import com.maz.academy.enums.UserRole;
import com.maz.academy.util.SystemHelper;
import com.maz.academy.exception.UserNotFoundException;

import java.util.*;

/**
 * The central hub of the E-Learning System.
 * This class acts as the main database and controller for the application.
 * It manages the global lists of {@link User}s and {@link Course}s.
 * Key Features:
 * Uses {@link Vector} for thread safe user management.
 * Uses {@link TreeSet} to provide sorted view of students by GPA and courses by difficulty.
 * Provides search functionality and data retrieval for the dashboard.
 */

public class Platform {
    /** A thread-safe collection storing all registered users (Students, Instructors, Admins). */
    private static Vector<User> users = new Vector<>();

    /** A list storing all available courses in the platform. */
    private static ArrayList<Course> courses = new ArrayList<>();

    public void initializeDefaultAdmin(){
        users.add(new Admin(1, "Admin", "Admin@edu.com", UserRole.ADMIN, this));
    }

    /**
     * Registers a new user into the platform.
     * @param user The User object to be added (Student, Instructor, or Admin).
     */
    public void addUser(User user){ users.add(user); }

    /**
     * Removes a user from the platform.
     * @param user The User object to be removed.
     * @return true if the user was found and removed successfully.
     */
    public boolean removeUser(User user){ return users.remove(user); }

    /**
     * Initiates a search for a user using the SystemHelper prompt.
     * @return The User object found by the search.
     * @throws UserNotFoundException if the search returns no results or the user cancels.
     */
    public User searchForUser() throws UserNotFoundException{
        try {
            SystemHelper.Search searcher = new SystemHelper.Search();

            List<User> users = new ArrayList<>(getUsers());
            if(users.isEmpty()){
                System.out.println("There are no users in the system.");
                return null;
            }

            return searcher.searchForUser(users);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
    }

    /**
     * helper method to find a user directly by their unique ID.
     * @param id The unique integer ID of the user.
     * @return The User object if found, otherwise null.
     */
    public User findUserById(int id){
        for(User user : users){
            if(user.getUserId() == id){
                return user;
            }
        }
        return null;
    }

    /**
     * Helper method to find a user directly by their email address.
     * @param email The email string of the user.
     * @return The User object if found, otherwise null.
     */
    public User findUserByEmail(String email){
        for(User user : users){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a new course to the courses ArrayList.
     * @param course The Course object to be added.
     */
    public void addCourse(Course course){ courses.add(course); }

    /**
     * Removes a course from the courses ArrayList.
     * @param course The Course object to be removed.
     * @return true if the course was found and removed.
     */
    public boolean removeCourse(Course course){ return courses.remove(course); }

    /**
     * Helper method to find a course directly by its unique ID.
     * @param id The unique integer ID of the course.
     * @return The Course object if found, otherwise null.
     */
    public Course findCourseById(int id){
        for(Course course : courses){
            if(course.getCourseID() == id){
                return course;
            }
        }
        return null;
    }

    /** @return An unmodifiable view of the user list containing all users. */
    public List<User> getUsers() { return Collections.unmodifiableList(users);}

    /** @return An unmodifiable view of the course list containing all courses. */
    public List<Course> getCourses() { return Collections.unmodifiableList(courses);}

    /**
     * Returns a sorted set of students ordered by their GPA.
     * This method filters the global user list for Students, sorts them
     * using the {@link Comparable} interface (by GPA), and returns them in a {@link TreeSet}.
     *
     * @return A TreeSet containing students sorted by GPA.
     */
    public TreeSet<Student> getStudentsSortedByGPA(){
        ArrayList<Student> tempStudentList = new ArrayList<>();
        for(User user : users){
            if(user instanceof Student){
                tempStudentList.add((Student) user);
            }
        }
        Collections.sort(tempStudentList);
        return new TreeSet<>(tempStudentList);
    }

    /**
     * Returns a sorted set of courses ordered by their difficulty level.
     * This method sorts the courses using the {@link Comparable} interface (by CourseLevel)
     * and returns them in a {@link TreeSet}.
     *
     * @return A TreeSet containing courses sorted from BEGINNER to ADVANCED.
     */
    public TreeSet<Course> getCoursesSortedByDifficulty(){
        ArrayList<Course> tempCourseList = new ArrayList<>(courses);
        Collections.sort(tempCourseList);
        return new TreeSet<>(tempCourseList);
    }
}