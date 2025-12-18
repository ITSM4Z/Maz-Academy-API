package com.educore.main;

import com.educore.model.*;
import com.educore.service.*;
import com.educore.util.SystemHelper;
import com.educore.exception.UserNotFoundException;
import java.util.*;

/**
 * The entry point for the E-Learning Platform application.
 * This class initializes the system and handles the login workflow.
 * It demonstrates:
 * Main application loop.
 * Polymorphism (treating different user roles).
 * Use of {@code try-catch-finally} blocks for session management.
 */

public class Main {
    /**
     * The main method that launches the application.
     * It presents a login menu for Admins, Instructors, and Students.
     * Based on the selection, it launches the appropriate dashboard.
     */
    public static void main(String[] args) {
        DatabaseService.initializeDatabase();
        Platform platform = DatabaseService.loadPlatform();
        User currentUser;

        if(platform.getUsers().isEmpty()){
            platform = new Platform();
            DatabaseService.savePlatform(platform);
            System.out.println("There is no users in the system. default admin with the name Admin has been created.");
        }

        List<User> userList = platform.getUsers();

        while (true){
            System.out.println("\n------ Welcome to the E-Learning Platform Program! ------");
            System.out.println("1. Login as Admin \n2. Login as Instructor \n3. Login as Student");
            SystemHelper.Choice choice = new SystemHelper.Choice("Choose an option (Enter 0 to exit the program): ");
            int option = choice.ChoiceByInt(3);
            System.out.println();
            int userCounter = 0;

            choice = new SystemHelper.Choice("Choose an option (Enter 0 to go back): ");
            switch (option){
                case 0:
                    System.out.println("Thank you for using the E-Learning Platform Program!");
                    DatabaseService.savePlatform(platform);
                    return;
                case 1:
                    List<Admin> admins = new ArrayList<>();
                    for (User user : userList) {
                        if (user instanceof Admin) {
                            userCounter++;
                            System.out.println((userCounter) + ". " + user);
                            admins.add((Admin) user);
                        }
                    }

                    if(admins.isEmpty()){
                        System.out.println("No Admins found.");
                        continue;
                    }

                    option = choice.ChoiceByInt(admins.size());
                    if(option == 0) continue;

                    currentUser = admins.get(option-1);
                    try{
                        System.out.println("\n------------ Welcome " + currentUser.getName() + " ------------");
                        currentUser.displayDashboard(platform);
                    } catch (UserNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    finally {
                        System.out.println("\n------------ See you later " + currentUser.getName() + " ------------");
                    }
                    break;
                case 2:
                    List<Instructor> instructors = new ArrayList<>();
                    for (User user : userList) {
                        if (user instanceof Instructor) {
                            userCounter++;
                            System.out.println((userCounter) + ". " + user);
                            instructors.add((Instructor) user);
                        }
                    }

                    if(instructors.isEmpty()){
                        System.out.println("No Instructors found.");
                        continue;
                    }

                    option = choice.ChoiceByInt(instructors.size());
                    if(option == 0) continue;

                    currentUser = instructors.get(option-1);
                    try{
                        System.out.println("\n------------ Welcome " + currentUser.getName() + " ------------");
                        currentUser.displayDashboard(platform);
                    } catch (UserNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    finally {
                        System.out.println("\n------------ See you later " + currentUser.getName() + " ------------");
                    }
                    break;
                case 3:
                    List<Student> students = new ArrayList<>();
                    for (User user : userList) {
                        if (user instanceof Student) {
                            userCounter++;
                            System.out.println((userCounter) + ". " + user);
                            students.add((Student) user);
                        }
                    }

                    if(students.isEmpty()){
                        System.out.println("No Students found.");
                        continue;
                    }

                    option = choice.ChoiceByInt(students.size());
                    if(option == 0) continue;

                    currentUser = students.get(option-1);
                    try{
                        System.out.println("\n------------ Welcome " + currentUser.getName() + " ------------");
                        currentUser.displayDashboard(platform);
                    } catch (UserNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    finally {
                        System.out.println("\n------------ See you later " + currentUser.getName() + " ------------");
                    }
                    break;
                default:
                    System.out.println("Error: An unexpected error occurred while choosing a role in the Main");
                    break;
            }
        }
    }
}