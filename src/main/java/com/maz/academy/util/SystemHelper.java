package com.maz.academy.util;

import com.maz.academy.model.User;
import com.maz.academy.enums.UserRole;
import com.maz.academy.exception.UserNotFoundException;
import com.maz.academy.service.Platform;

import java.util.*;

/**
 * A utility class providing helpful tools for console interaction.
 * This class encapsulates logic for user input validation, menu selection,
 * and search operations. It uses static inner classes to group related functionalities.
 */


public class SystemHelper {
    public static class Choice{
        String prompt;
        String emptyError;
        String negativeError;
        String boundsError;

        /**
         * Constructs a Choice helper with custom error messages.
         *
         * @param prompt        The message displayed to the user.
         * @param emptyError    Message displayed if input is empty.
         * @param negativeError Message displayed if input is negative.
         * @param boundsError   Message displayed if input is out of range.
         */
        public Choice(String prompt, String emptyError, String negativeError, String boundsError){
            this.prompt = prompt;
            this.emptyError = emptyError;
            this.negativeError = negativeError;
            this.boundsError = boundsError;
        }
        //I used overloading here so the tool can be as much customizable as possible.
        public Choice(String prompt, String emptyError, String negativeError){
            this.prompt = prompt;
            this.emptyError = emptyError;
            this.negativeError = negativeError;
            this.boundsError = "Error: You must enter a valid choice number.";
        }
        public Choice(String prompt, String emptyError){
            this.prompt = prompt;
            this.emptyError = emptyError;
            this.negativeError = "Error: You must enter a positive number.";
            this.boundsError = "Error: You must enter a valid choice number.";
        }
        public Choice(String prompt){
            this.prompt = prompt;
            this.emptyError = "Error: You must enter a choice number.";
            this.negativeError = "Error: You must enter a positive number.";
            this.boundsError = "Error: You must enter a valid choice number.";
        }

        /**
         * Prompts the user for an integer input within a specified range (0 to bounds).
         * Loops until valid input is received.
         *
         * @param bounds The maximum valid integer choice.
         * @return The validated integer choice.
         */
        public int ChoiceByInt(int bounds){
            Scanner sc = new Scanner(System.in);
            int choice;

            while (true){
                System.out.print(prompt);
                String userInput;
                try {
                    userInput = sc.nextLine();
                    if(userInput.isEmpty()){
                        System.out.println(emptyError);
                        continue;
                    }
                    choice = Integer.parseInt(userInput);
                    if(choice == 0){
                        break;
                    }
                    else if(choice < 0){
                        System.out.println(negativeError);
                        continue;
                    }
                    else if(choice > bounds){
                        System.out.println(boundsError);
                        continue;
                    }
                    break;
                } catch (NumberFormatException e){
                    System.out.println("Error: You must enter a number.");
                }
            }
            return choice;
        }
    }

    /**
     * An inner class designed to handle searching for users within a list.
     * Supports searching by exact ID or by name matching.
     */
    public static class Search{
        String prompt;
        String emptyError;
        String negativeError;

        public Search(String prompt, String emptyError, String negativeError){
            this.prompt = prompt;
            this.emptyError = emptyError;
            this.negativeError = negativeError;
        }
        public Search(String prompt, String emptyError){
            this.prompt = prompt;
            this.emptyError = emptyError;
            this.negativeError = "Error: You must enter a positive number.";
        }
        public Search(String prompt){
            this.prompt = prompt;
            this.emptyError = "Error: You must enter a user's Name or Id.";
            this.negativeError = "Error: You must enter a positive number.";
        }
        public Search(){
            this.prompt = "Enter a User's Name or Id (Enter 0 to go back): ";
            this.emptyError = "Error: You must enter a user's Name or Id.";
            this.negativeError = "Error: You must enter a positive number.";
        }

        /**
         * Searches for a user within a list, filtering by a specific UserRole.
         *
         * @param passedUserList The list of users to search through.
         * @param userRole       The specific role to filter by (like searching only for Instructors).
         * @return The found User object, or null if cancelled.
         * @throws UserNotFoundException if no user matches the criteria.
         */
        public User searchForUser(List<User> passedUserList, UserRole userRole) throws UserNotFoundException{
            ArrayList<User> users = new ArrayList<>(passedUserList);

            if(users.isEmpty()) return null;

            String role;
            switch (userRole){
                case STUDENT:
                    role = "student";
                    break;
                case INSTRUCTOR:
                    role = "instructor";
                    break;
                case ADMIN:
                    role = "admin";
                    break;
                case null:
                    role = "user";
                    break;
            }

            Scanner sc = new Scanner(System.in);
            String userInput = "";

            while (true){
                System.out.println(users.size() + " " + role + "s.");
                System.out.print(prompt);

                try {
                    userInput = sc.nextLine().toLowerCase().trim();

                    if(userInput.isEmpty()){
                        System.out.println(emptyError);
                        continue;
                    }
                    int id = Integer.parseInt(userInput);
                    if(id == 0){
                        break;
                    }
                    else if(id < 0){
                        System.out.println(negativeError);
                        continue;
                    }

                    for(User user : users){
                        if(user.getUserId() == id){
                            return user;
                        }
                    }
                    throw new UserNotFoundException("The " + role + " with the id of: " + userInput + " was not found.");
                } catch (NumberFormatException numberE){
                    while (true){
                        ArrayList<User> nameResults = new ArrayList<>();
                        for(User user : users){
                            if(user.getName().toLowerCase().trim().startsWith(userInput)){
                                nameResults.add(user);
                            }
                        }

                        System.out.println("Search results for 10 " + role + "s: ");
                        int resultsCount = 0;
                        for (int i = 0; i < 10; i++) {
                            try{
                                System.out.println((i+1) + ". " + nameResults.get(i));
                                resultsCount++;
                                if(nameResults.get(i) == nameResults.getLast())
                                    break;
                            } catch (IndexOutOfBoundsException indexE){
                                System.out.println("CRITICAL: An unexpected error occurred while looping through the " +
                                        "name results.");
                                break;
                            }
                        }
                        if(nameResults.isEmpty()){
                            System.out.println("No " + role + "s was found.");
                        }

                        int choice;
                        while (true){
                            System.out.print("Choose a user or enter another user's name to search again" +
                                    " (Enter 0 to go back): ");
                            try {
                                userInput = sc.nextLine().toLowerCase().trim();
                                if(userInput.isEmpty()){
                                    System.out.println("Error: You must choose a user.");
                                    continue;
                                }
                                choice = Integer.parseInt(userInput);
                                if(choice == 0){
                                    return null;
                                }
                                else if(choice < 0){
                                    System.out.println("Error: You must enter a positive number.");
                                    continue;
                                }
                                else if(choice > resultsCount){
                                    System.out.println("Error: You must enter a valid user choice.");
                                    continue;
                                }
                                return nameResults.get(choice - 1);
                            } catch (NumberFormatException e){
                                break;
                            }
                        }
                    }
                }
            }
            return null;
        }

        /**
         * Searches for a user within a list without role filtering.
         *
         * @param passedUserList The list of users to search through.
         * @return The found User object.
         * @throws UserNotFoundException if no user is found.
         */
        public User searchForUser(List<User> passedUserList) throws UserNotFoundException{
            ArrayList<User> users = new ArrayList<>(passedUserList);
            if(users.isEmpty()) return null;

            String role = "user";
            Scanner sc = new Scanner(System.in);

            String userInput = "";
            while (true){
                System.out.println(users.size() + " " + role + "s.");
                System.out.print(prompt);

                try {
                    userInput = sc.nextLine().toLowerCase().trim();

                    if(userInput.isEmpty()){
                        System.out.println(emptyError);
                        continue;
                    }
                    int id = Integer.parseInt(userInput);
                    if(id == 0){
                        break;
                    }
                    else if(id < 0){
                        System.out.println(negativeError);
                    }

                    for(User user : users){
                        if(user.getUserId() == id){
                            return user;
                        }
                    }
                    throw new UserNotFoundException("The " + role + " with the id of: " + userInput + " was not found.");
                } catch (NumberFormatException numberE){
                    while (true){
                        ArrayList<User> nameResults = new ArrayList<>();
                        for(User user : users){
                            if(user.getName().toLowerCase().trim().startsWith(userInput)){
                                nameResults.add(user);
                            }
                        }

                        System.out.println("Search results: ");
                        int resultsCount = 0;
                        for (int i = 0; i < 10; i++) {
                            try{
                                System.out.println((i+1) + ". " + nameResults.get(i));
                                resultsCount++;
                                if(nameResults.get(i) == nameResults.getLast())
                                    break;
                            } catch (IndexOutOfBoundsException indexE){
                                break;
                            }
                        }

                        if(nameResults.isEmpty()){
                            System.out.println("No " + role + "s was found.");
                        }

                        int choice;
                        while (true){
                            System.out.print("Choose a user or enter another user's name to search again" +
                                    " (Enter 0 to go back): ");
                            try {
                                userInput = sc.nextLine().toLowerCase().trim();
                                if(userInput.isEmpty()){
                                    System.out.println("Error: You must choose a user.");
                                    continue;
                                }
                                choice = Integer.parseInt(userInput);
                                if(choice == 0){
                                    return null;
                                }
                                else if(choice < 0){
                                    System.out.println("Error: You must enter a positive number.");
                                    continue;
                                }
                                else if(choice > resultsCount){
                                    System.out.println("Error: You must enter a valid user choice.");
                                    continue;
                                }
                                return nameResults.get(choice - 1);
                            } catch (NumberFormatException e){
                                break;
                            }
                        }
                    }
                }
            }
            return null;
        }
    }

    public static class Generate{
        public int generateID(int idLength, boolean sequential, Platform platform){
            Random rand = new Random();
            int generatedID;
            double bound = Math.pow(10, idLength);

            do{
                int randomNum = rand.nextInt((int)bound);
                generatedID = Integer.parseInt(String.format("%04d", randomNum));
            } while(platform.findCourseById(generatedID) != null);

            return generatedID;
        }
    }
}