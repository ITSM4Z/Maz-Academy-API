package com.educore.model;

import com.educore.enums.UserRole;
import com.educore.enums.CourseLevel;
import com.educore.service.Platform;
import com.educore.util.SystemHelper;
import com.educore.exception.UserNotFoundException;

import java.io.Serializable;
import java.util.*;

/**
 * Represents an Instructor user in the E-Learning Platform.
 * Instructors are responsible for creating and managing courses.
 * Their capabilities include:
 * Creating new courses and assigning them difficulty levels.
 * Removing courses they teach.
 * Viewing and searching for students enrolled in their courses.
 * Grading students.
 */

public class Instructor extends User implements Cloneable {
    /** A list of courses this instructor is currently teaching. */
    private ArrayList<Course> teachingCourses;

    /**
     * Constructs a new Instructor with the specified details.
     *
     * @param userId   The unique identifier.
     * @param name     The instructor's full name.
     * @param email    The instructor's email address.
     * @param userRole The role.
     */
    public Instructor(int userId, String name, String email, UserRole userRole) {
        super(userId, name, email, userRole);
        teachingCourses = new ArrayList<>();
    }

    /**
     * Returns a string representation of the instructor.
     * @return Formatted string starting with "Instructor:".
     */
    @Override
    public String toString() {
        return String.format("Instructor: %s", super.toString());
    }

    /**
     * Creates a deep copy of the Instructor object.
     * Ensures that the list of teaching courses is duplicated so changes to the clone do not affect the original.
     *
     * @return A new independent Instructor object.
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Instructor clone() throws CloneNotSupportedException {
        Instructor cloned = (Instructor) super.clone();
        cloned.teachingCourses = new ArrayList<>(this.teachingCourses);
        return cloned;
    }

    /**
     * Checks if this Instructor object is equal to another object.
     * Equality is determined by the unique {@code userId}.
     * Two Instructor objects are considered the same if they share the same ID,
     * regardless of their other attributes as they might be the same.
     *
     * @param obj The object to compare with.
     * @return true if the Instructor objects are the same or have the same {@param userId} false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Instructor)) return false;
        if(this == obj) return true;

        Instructor instructor = (Instructor) obj;
        return this.userId == instructor.userId;
    }

    /**
     * Returns a hash code value for the instructor.
     * This method is overridden so that instructors that are equal based on {@link #equals} have the same hash code
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
     * Displays the Instructor Dashboard menu.
     * Allows the instructor to view their courses or create new ones or remove existing ones.
     *
     * @param platform The central platform instance (used for course management).
     */
    @Override
    public void displayDashboard(Platform platform) {
        SystemHelper.Choice choice;

        int option;

        while (true){
            System.out.println();
            System.out.println("------ Instructor Dashboard ------");
            System.out.println("1. View Teaching Courses \n2. Create New Course \n3. Remove a Course");

            choice = new SystemHelper.Choice("Choose an option (Enter 0 to go back): ");
            option = choice.ChoiceByInt(3);

            switch (option){
                case 0: return;
                case 1:
                    showTeachingCourses();
                    break;
                case 2:
                    createCourse(platform);
                    break;
                case 3:
                    removeCourse(platform);
                    break;
                default:
                    System.out.println("Error: You must enter a valid choice number.");
                    break;
            }
        }
    }

    /**
     * Assigns a course to this instructor.
     *
     * @param course The course object to be added to the teaching list.
     */
    public void addTeachingCourse(Course course){
        teachingCourses.add(course);
    }

    /**
     * Displays a list of students enrolled in a specific course and allows selection.
     *
     * @param course The course to inspect.
     * @return The selected Student object, or null if no selection is made.
     */
    public Student showEnrolledStudents(Course course){
        List<Student> studentList = course.getEnrolledStudents();
        if(studentList.isEmpty()){
            System.out.println("There are no students enrolled in this course.");
            return null;
        }
        else{
            for (int i = 0; i < studentList.size(); i++) {
                System.out.println((i+1) + ". " + studentList.get(i));
            }
            SystemHelper.Choice choice = new SystemHelper.Choice("Choose a student to manage (Press 0 to exit): ",
                    "Error: You must choose a student.",
                    "Error: You must enter a positive number.",
                    "Error: You must enter a valid student choice.");

            int option = choice.ChoiceByInt(studentList.size());

            if(option == 0) return null;

            return studentList.get(option - 1);
        }
    }

    /**
     * Searches for a specific student within a course's enrollment list.
     *
     * @param course The course to search within.
     * @return The found Student object.
     * @throws UserNotFoundException if the student is not found.
     */
    public Student findEnrolledStudent(Course course) throws UserNotFoundException {
        try {
            SystemHelper.Search searcher = new SystemHelper.Search();

            List<User> users = new ArrayList<>(course.getEnrolledStudents());
            Student student = (Student) searcher.searchForUser(users, UserRole.STUDENT);
            if(student == null){
                System.out.println("There are no students enrolled in the course.");
                return null;
            }
            return student;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
    }

    /**
     * Handles the logic for removing a course taught by this instructor.
     * Ensures that if a course is removed, all enrolled students are unenrolled from it as well.
     *
     * @param platform The platform instance where the course is saved.
     */
    public void removeCourse(Platform platform){
        if(teachingCourses.isEmpty()){
            System.out.println("There are no courses for you to remove.");
            return;
        }

        for (int i = 0; i < teachingCourses.size(); i++) {
            System.out.println((i+1) + ". " + teachingCourses.get(i));
        }

        SystemHelper.Choice choice = new SystemHelper.Choice("Choose a course to remove (Press 0 to exit): ",
                "Error: You must choose a course.",
                "Error: You must enter a positive number.",
                "Error: You must enter a valid course choice.");

        int option = choice.ChoiceByInt(teachingCourses.size());

        if(option == 0) return;

        Course course = teachingCourses.get(option - 1);
        if(platform.removeCourse(course)){
            for(Student student : course.getEnrolledStudents()){
                if(!student.removeCourseEnrollment(course)){
                    System.out.println("CRITICAL: An unexpected error occurred while removing enrolled students from: " +
                            course);
                    break;
                }
            }
            teachingCourses.remove(course);
            System.out.println("Removed (" + course + ") Successfully.");
        }
        else{
            System.out.println("Error: An unexpected error occurred while trying to remove a course.");
        }
    }

    /**
     * Displays all courses taught by the instructor and provides management options.
     * Options include viewing enrolled students or searching for a specific student.
     */
    public void showTeachingCourses(){
        if(teachingCourses.isEmpty()){
            System.out.println("You are not teaching any course.");
            return;
        }

        for (int i = 0; i < teachingCourses.size(); i++) {
            System.out.println((i+1) + ". " + teachingCourses.get(i));
        }

        SystemHelper.Choice choice = new SystemHelper.Choice("Choose a course to manage (Press 0 to exit): ",
                "Error: You must choose a course.",
                "Error: You must enter a positive number.",
                "Error: You must enter a valid course choice.");

        int option = choice.ChoiceByInt(teachingCourses.size());

        if(option == 0) return;

        Course course = teachingCourses.get(option - 1);

        System.out.println("Managing (" + course+ ")");
        System.out.println("1. View Enrolled Students \n2. Search For Enrolled Student");
        option = choice.ChoiceByInt(2);

        Student student = null;

        if(option == 0) return;
        else if(option == 1){
            student = showEnrolledStudents(course);
        }
        else if(option == 2){
            try {
                student = findEnrolledStudent(course);
            } catch (UserNotFoundException e){
                System.out.println(e.getMessage());
            }
        }

        if(student == null) return;

        System.out.println("Managing (" + student.getName() + ")");
        System.out.println("1. Assign grade");

        choice = new SystemHelper.Choice("Choose an option (Press 0 to exit): ");

        option = choice.ChoiceByInt(1);

        if(option == 0) return;
        else if(option == 1){
            gradeStudent(course, student);
        }
    }

    /**
     * Assigns or modifies a grade for a student in a specific course.
     * This method takes user input to determine the grade value and updates
     * the student's record using {@link Student#modifyGrade(Course, Double)}.
     *
     * @param course  The course context.
     * @param student The student to be graded.
     */
    public void gradeStudent(Course course, Student student){
        Scanner sc = new Scanner(System.in);
        String userInput = "";
        while (true){
            System.out.print("Enter a positive grade to increase or a negative to decrease (Enter 0 to go back): ");
            userInput = sc.nextLine().trim();

            try {
                if(userInput.isEmpty()){
                    System.out.println("Error: You must enter a grade.");
                    continue;
                }

                double grade = Double.parseDouble(userInput);

                if(grade == 0){
                    break;
                }

                boolean success = student.modifyGrade(course, Double.valueOf(grade));

                if(success){
                    break;
                }
            } catch (NumberFormatException e){
                System.out.println("Error: You must enter a number.");
            }
        }
    }

    private void createCourse(Platform platform){
        int courseId = 9999;
        int courseCapacity = 0;
        double coursePrice = 0.0;
        String courseTitle = "";
        CourseLevel courseLevel = CourseLevel.BEGINNER;

        Random rand = new Random();

        System.out.println("------ Course Creator ------ \nCourse Levels: \n1. Beginner \n2. Intermediate \n3. Advanced");
        SystemHelper.Choice choice = new SystemHelper.Choice("Choose a level for the course (Enter 0 to go back): ");

        int option = choice.ChoiceByInt(3);
        switch (option){
            case 0:
                break;
            case 1:
                courseLevel = CourseLevel.BEGINNER;
                break;
            case 2:
                courseLevel = CourseLevel.INTERMEDIATE;
                break;
            case 3:
                courseLevel = CourseLevel.ADVANCED;
                break;
            default:
                System.out.println("CRITICAL: An unexpected error happened while assigning course level in create course.");
                break;
        }

        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.print("Enter the title of the course (Press 0 to exit or enter to skip): ");

            try {
                courseTitle = sc.nextLine().trim();

                if(courseTitle.isEmpty()){
                    courseTitle = "NewCourse" + (platform.getCourses().size() + 1);
                    System.out.println("Skipped title input: Generated default title (" + courseTitle + ").");
                    break;
                }
                int id = Integer.parseInt(courseTitle);
                if(id == 0){
                    break;
                }
                else{
                    System.out.println("Error: The title of the course must not be numbers.");
                }
            } catch (NumberFormatException numberE){
                break;
            }
        }
        while (true){
            System.out.print("Enter the id of the course (Press 0 to exit or enter to skip): ");

            try {
                String userInput = sc.nextLine().trim();

                if(userInput.isEmpty()){
                    do{
                        int randomNum = rand.nextInt(10000);
                        userInput = String.format("%04d", randomNum);
                        courseId = Integer.parseInt(userInput);
                    } while(platform.findCourseById(Integer.parseInt(userInput)) != null);
                    System.out.println("Skipped id input: Generated random id (" + courseId + ").");
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
                courseId = id;
                break;
            } catch (NumberFormatException numberE){
                System.out.println("Error: The id must be in numbers.");
            }
        }
        while (true){
            System.out.print("Enter the capacity of the course (Press 0 to exit or enter to skip): ");

            try {
                String userInput = sc.nextLine().trim();

                if(userInput.isEmpty()){
                    courseCapacity = 15;
                    System.out.println("Skipped capacity input: Default capacity is set (" + 15 + ").");
                    break;
                }
                int capacity = Integer.parseInt(userInput);
                if(capacity == 0){
                    break;
                }
                else if(capacity < 0){
                    System.out.println("Error: The capacity must be positive.");
                    continue;
                }
                courseCapacity = capacity;
                break;
            } catch (NumberFormatException numberE){
                System.out.println("Error: The capacity must be in numbers.");
            }
        }
        while (true){
            System.out.print("Enter the price of the course (Press 0 to exit or enter to skip): ");

            try {
                String userInput = sc.nextLine().trim();

                if(userInput.isEmpty()){
                    System.out.println("Skipped price input: Priced the course as free (" + 0.0 + ").");
                    break;
                }
                double price = Double.parseDouble(userInput);
                if(price == 0){
                    break;
                }
                else if(price < 0){
                    System.out.println("Error: The price must be positive.");
                    continue;
                }
                coursePrice = price;
                break;
            } catch (NumberFormatException numberE){
                System.out.println("Error: The price must be in numbers.");
            }
        }

        Course createdCourse = new Course(courseId, courseCapacity, courseTitle, coursePrice, courseLevel);
        platform.addCourse(createdCourse);
        addTeachingCourse(createdCourse);
    }
}