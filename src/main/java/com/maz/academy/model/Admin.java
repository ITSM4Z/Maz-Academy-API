package com.maz.academy.model;

import com.maz.academy.enums.UserRole;
import com.maz.academy.service.Platform;
import com.maz.academy.util.SystemHelper;
import com.maz.academy.util.Catalogue;
import com.maz.academy.exception.UserNotFoundException;

import java.io.Serializable;
import java.util.*;

/**
 * Represents an Administrator in the E-Learning Platform.
 * Admins have the highest level of access in the system. Their responsibilities include:
 * Managing the global list of users (adding/removing Students and Instructors).
 * Viewing sorted reports (Courses by difficulty, Students by GPA).
 * Accessing the full course catalogue.
 */

public class Admin extends User implements Cloneable {
    /** A reference to the central platform instance to perform system operations. */
    private final Platform platform;

    /**
     * Constructs a new Admin user.
     *
     * @param userId   The unique identifier.
     * @param name     The admin's name.
     * @param email    The admin's email address.
     * @param userRole The role.
     * @param platform The reference to the main Platform instance.
     */
    public Admin(int userId, String name, String email, UserRole userRole, Platform platform) {
        super(userId, name, email, userRole);
        this.platform = platform;
    }

    /**
     * Returns a string representation of the admin.
     * @return Formatted string starting with "Admin:".
     */
    @Override
    public String toString() {
        return String.format("Admin: %s", super.toString());
    }

    /**
     * Creates a shallow copy of the Admin object.
     * Note: The {@code platform} reference is shared between the original and the clone
     * because the Platform is supposed to be single entity.
     *
     * @return A new Admin object.
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Admin clone() throws CloneNotSupportedException {
        return (Admin) super.clone();
    }

    /**
     * Checks if this Admin object is equal to another object.
     * Equality is determined by the unique {@code userId}.
     * Two Admin objects are considered the same if they share the same ID,
     * regardless of their other attributes as they might be the same.
     *
     * @param obj The object to compare with.
     * @return true if the Admin objects are the same or have the same {@param userId} false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Admin)) return false;
        if(this == obj) return true;

        Admin admin = (Admin) obj;
        return this.userId == admin.userId;
    }

    /**
     * Returns a hash code value for the admin.
     * This method is overridden so that admins that are equal based on {@link #equals} have the same hash code
     * which is required for correct behavior in hash based collections (like {@link HashMap}).
     * The hash code is generated based on the unique {@code userId}.
     *
     * @return An integer hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    /**
     * Displays the Admin Dashboard menu.
     * Provides options to:
     * View all users and courses.
     * Add or remove users.
     * View students sorted by GPA (demonstrating {@code TreeSet}).
     * View courses sorted by difficulty.
     * Demo the generic {@code Catalogue} class.
     *
     * @param platform The central platform instance.
     */
    @Override
    public void displayDashboard(Platform platform) {
        SystemHelper.Choice choice;

        int option;

        while (true){
            System.out.println();
            System.out.println("------ Admin Dashboard ------");
            System.out.println("1. View All Users \n2. View All Courses \n3. Add New User \n4. Edit User" +
                    "\n5. Remove User \n6. View Students Sorted by GPA \n7. View Courses Sorted by Difficulty" +
                    "\n8. Demo Generic Catalogue");

            choice = new SystemHelper.Choice("Choose an option (Enter 0 to go back): ");
            option = choice.ChoiceByInt(7);

            switch (option){
                case 0: return;
                case 1:
                    List<User> users = platform.getUsers();
                    if(users.isEmpty()){
                        System.out.println("Error: No users found.");
                        break;
                    }
                    System.out.println("Currently there's " + platform.getUsers().size() + " users in the system: ");
                    for(User user : users){
                        System.out.println(user);
                    }
                    break;
                case 2:
                    List<Course> courses = platform.getCourses();
                    if(courses.isEmpty()){
                        System.out.println("No courses found.");
                        break;
                    }
                    System.out.println("Currently there's " + platform.getCourses().size() + " courses in the system: ");
                    for(Course course : courses){
                        System.out.println(course);
                    }
                    break;
                case 3:
                    createUser();
                    break;
                case 4:
                    editUser();
                    break;
                case 5:
                    while (true){
                        try {
                            User user = platform.searchForUser();
                            if(user == null) break;
                            if(user == this){
                                System.out.println("Error: You cannot remove yourself from the system.");
                                continue;
                            }

                            if(platform.removeUser(user)){
                                System.out.println(user + " is removed successfully.");
                                break;
                            }
                            else{
                                System.out.println("CRITICAL: An Unexpected error happened while removing: " + user.name);
                            }

                        } catch (UserNotFoundException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 6:
                    System.out.println("Students sorted by GPA:");
                    TreeSet<Student> sortedUsers = platform.getStudentsSortedByGPA();
                    for(Student student : sortedUsers){
                        System.out.println(student + " GPA: " + student.calculateGPA());
                    }
                    break;
                case 7:
                    System.out.println("Courses sorted by difficulty:");
                    TreeSet<Course> sortedCourses = platform.getCoursesSortedByDifficulty();
                    for(Course course : sortedCourses){
                        System.out.println(course + " Difficulty: " + course.getCourseLevel());
                    }
                    break;
                case 8:
                    Catalogue<User> catalogue = new Catalogue<>();
                    catalogue.addItem(platform.getUsers().get(0));
                    catalogue.addItem(platform.getUsers().get(1));
                    catalogue.printAll();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * An internal helper method to handle the creation of a new user.
     * Collects name, ID, and email from the admin, validates inputs, and
     * adds the new user to the Platform.
     */
    private void createUser(){
        int userId = 9999;
        String userName = "";
        String email = "";
        UserRole userRole = UserRole.STUDENT;

        Random rand = new Random();

        System.out.println("------ User Creator ------ \nUser Roles: \n1. Student \n2. Instructor \n3. Admin");
        SystemHelper.Choice choice = new SystemHelper.Choice("Choose a role for the user (Enter 0 to go back): ");

        int option = choice.ChoiceByInt(3);
        switch (option){
            case 0:
                break;
            case 1:
                userRole = UserRole.STUDENT;
                break;
            case 2:
                userRole = UserRole.INSTRUCTOR;
                break;
            case 3:
                userRole = UserRole.ADMIN;
                break;
            default:
                System.out.println("CRITICAL: An unexpected error happened while assigning user role in create user.");
                break;
        }

        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.print("Enter the name of the user (Press 0 to exit or enter to skip): ");

            try {
                userName = sc.nextLine().trim();

                if(userName.isEmpty()){
                    userName = "NewUser" + (platform.getUsers().size() + 1);
                    System.out.println("Skipped name input: Generated default name (" + userName + ").");
                    break;
                }
                int id = Integer.parseInt(userName);
                if(id == 0){
                    break;
                }
                else{
                    System.out.println("Error: The name of the user must not be numbers.");
                }
            } catch (NumberFormatException numberE){
                break;
            }
        }
        while (true){
            System.out.print("Enter the id of the user (Press 0 to exit or enter to skip): ");

            try {
                String userInput = sc.nextLine().trim();

                if(userInput.isEmpty()){
                    do{
                        int randomNum = rand.nextInt(10000);
                        userInput = String.format("%04d", randomNum);
                        userId = Integer.parseInt(userInput);
                    } while(platform.findUserById(Integer.parseInt(userInput)) != null);
                    System.out.println("Skipped id input: Generated random id (" + userId + ").");
                    break;
                }
                int id = Integer.parseInt(userInput);
                if(id == 0){
                    break;
                }
                else if(platform.findUserById(id) != null){
                    System.out.println("Error: The id you entered is already used.");
                    continue;
                }
                else if(id < 0){
                    System.out.println("Error: The id number must be positive.");
                    continue;
                }
                userId = id;
                break;
            } catch (NumberFormatException numberE){
                System.out.println("Error: The id must be in numbers.");
            }
        }
        while (true){
            System.out.print("Enter the email of the user (Press 0 to exit or enter to skip): ");

            try {
                email = sc.nextLine().trim();

                if(email.isEmpty()){
                    email = "none";
                    System.out.println("Skipped email input: No email provided.");
                    break;
                }
                int id = Integer.parseInt(email);
                if(id == 0){
                    break;
                }
                else{
                    System.out.println("Error: The email of the user must not be numbers.");
                }
            } catch (NumberFormatException numberE){
                if(platform.findUserByEmail(email) != null){
                    System.out.println("Error: The email you entered is already used.");
                    continue;
                }
                break;
            }
        }

        User user = null;
        switch (userRole){
            case UserRole.STUDENT:
                user = new Student(userId, userName, email, userRole);
                break;
            case UserRole.INSTRUCTOR:
                user = new Instructor(userId, userName, email, userRole);
                break;
            case UserRole.ADMIN:
                user = new Admin(userId, userName, email, userRole, this.platform);
                break;
            case null, default:
                System.out.println("CRITICAL: An unexpected error happened while creating a new user.");
                break;
        }

        platform.addUser(user);
    }

    /**
     * An internal helper method to handle the edit of an existing user.
     * Shows name, ID, and email from the existing user, validates inputs, and
     * updates the new user in the Platform.
     */
    private void editUser(){
        //Searches for the user and shows the results

        //When the admin Chooses a user the program prompts the admin to choose a field to edit
        //Then the method validates the admin's input
    }
}