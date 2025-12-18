package com.educore.model;

import com.educore.enums.UserRole;
import com.educore.service.Platform;
import com.educore.util.SystemHelper;
import com.educore.exception.AlreadyEnrolledException;
import com.educore.exception.CourseFullException;
import com.educore.exception.UserNotFoundException;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a Student user in the E-Learning Platform.
 * This class manages the student's academic progress, including:
 * Enrolling in and dropping courses.
 * Tracking grades using a {@link HashMap}.
 * Rating courses.
 * Calculating GPA.
 * It implements {@link Comparable} to allow sorting students by their GPA.
 */

public class Student extends User implements Cloneable, Comparable<Student> {
    /** A list of courses the student is currently active in. */
    private ArrayList<Course> enrolledCourses;
    /**
     * Stores the ratings this student has given to courses.
     * Key: Course, Value: Rating (Double).
     */
    private HashMap<Course, Double> ratedCourses;
    /**
     * Stores the grades the student has received.
     * Key: Course, Value: Grade (Double).
     */
    private HashMap<Course, Double> grades;

    /**
     * Constructs a new Student with the specified details.
     *
     * @param userId   The unique id.
     * @param name     The student's name.
     * @param email    The student's email address.
     * @param userRole The role.
     */
    public Student(int userId, String name, String email, UserRole userRole) {
        super(userId, name, email, userRole);
        enrolledCourses = new ArrayList<>();
        grades = new HashMap<>();
        ratedCourses = new HashMap<>();
    }

    /**
     * Returns a string representation of the student.
     * @return Formatted string starting with "Student:".
     */
    @Override
    public String toString() {
        return String.format("Student: %s", super.toString());
    }

    /**
     * Creates a deep copy of the Student object.
     * Ensures that the list of enrolled courses and the map of grades
     * is duplicated so changes to the clone do not affect the original.
     *
     * @return A new independent Student object.
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Student clone() throws CloneNotSupportedException {
        Student cloned = (Student) super.clone();
        cloned.enrolledCourses = new ArrayList<>(this.enrolledCourses);
        cloned.grades = new HashMap<>(this.grades);
        return cloned;
    }

    /**
     * Checks if this Student object is equal to another object.
     * Equality is determined by the unique {@code userId}.
     * Two Student objects are considered the same if they share the same ID,
     * regardless of their other attributes as they might be the same.
     *
     * @param obj The object to compare with.
     * @return true if the Student objects are the same or have the same {@param userId} false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Student)) return false;
        if(this == obj) return true;

        Student student = (Student) obj;
        return this.userId == student.userId;
    }

    /**
     * Returns a hash code value for the student.
     * This method is overridden so that students that are equal based on {@link #equals} have the same hash code
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
     * Compares this student to another student based on their GPA.
     * Used by {@link Platform#getStudentsSortedByGPA()} to organize students.
     *
     * @param student The other student to compare against.
     * @return A negative integer(less than), zero(equal to), or a positive integer(greater than)
     * after comparing the students GPA.
     */
    @Override
    public int compareTo(Student student) {
        return Double.compare(this.calculateGPA(), student.calculateGPA());
    }

    /**
     * Displays the Student Dashboard menu.
     * Allows the student to view enrolled courses, enroll in new ones, drop courses, and check their grades/GPA.
     *
     * @param platform The central platform instance (used to fetch available courses).
     */
    @Override
    public void displayDashboard(Platform platform) {
        while (true){
            System.out.println("------ Student Dashboard ------");
            System.out.println("1. View Enrolled Courses \n2. Enroll in New Course" +
                    " \n3. Drop Course \n4. View Grades & GPA");
            Scanner sc = new Scanner(System.in);

            SystemHelper.Choice choice = new SystemHelper.Choice("Choose an option (Press 0 to go back): ");

            int option = choice.ChoiceByInt(4);

            switch (option){
                case 0: return;
                case 1:
                    viewEnrolledCourses(sc);
                    break;
                case 2:
                    enrollInNewCourse(platform);
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    viewGrades();
                    break;
                default:
                    System.out.println("Error: You must enter a valid choice number.");
                    break;
            }
        }
    }

    /**
     * Bulk updates the enrolled courses list.
     *
     * @param courses The list of courses to set.
     */
    public void setEnrolledCourses(ArrayList<Course> courses){
        enrolledCourses.addAll(courses);
    }

    /**
     * Adds a specific course to the student's enrollment list and initializes their grade to 0.0.
     *
     * @param course The course to enroll in.
     */
    public void addCourseEnrollment(Course course){
        enrolledCourses.add(course);
        grades.put(course, 0.0);
    }

    /**
     * Removes a course from the student's enrollment list and deletes their grade record.
     *
     * @param course The course to remove.
     * @return true if the course or grade was found and removed.
     */
    public boolean removeCourseEnrollment(Course course) {
        boolean coursesRemoved = enrolledCourses.remove(course);
        boolean gradesRemoved = grades.remove(course) != null;
        return coursesRemoved || gradesRemoved;
    }

    /** @return An unmodifiable view of the enrolled courses list containing all enrolled courses. */
    public List<Course> getEnrolledCourses(){ return Collections.unmodifiableList(enrolledCourses); }

    /**
     * Returns the grade for a specific course.
     *
     * @param course The course to check.
     * @return The grade as a double, or null if no grade exists.
     */
    public double getGrade(Course course){ return grades.get(course); }

    /**
     * Updates the student's grade for a specific course.
     * Validates that the final grade is between 0 and 100.
     *
     * @param course The course for which the grade is being modified.
     * @param grade  The amount to add (or subtract) from the current grade.
     * @return true if the grade was successfully updated, false if validation failed.
     */
    public boolean modifyGrade(Course course, Double grade){
        double finalGrade;
        Double gradeObj = grades.get(course);
        if(gradeObj == null){
            grades.put(course, Double.valueOf(0.0));
            finalGrade = 0;
        }
        else{
            finalGrade = gradeObj;
        }

        finalGrade += grade;
        if(finalGrade < 0){
            System.out.println("Error: The student's grade must not be negative.");
            return false;
        }
        else if(finalGrade > 100){
            System.out.println("Error: The student's grade must not exceed 100.");
            return false;
        }
        else{
            grades.put(course, finalGrade);
            return true;
        }
    }

    /**
     * Calculates the student's GPA.
     *
     * @return The mean of all course grades. Returns 0.0 if the student has no grades.
     */
    public double calculateGPA(){
        if(grades.isEmpty()) return 0.0;
        double totalGrades = 0.0;
        for(Double grade : grades.values()){
            totalGrades += grade;
        }
        return totalGrades / grades.size();
    }

    //Prints all courses with the corresponding grade assigned to it and the total GPA
    private void viewGrades(){
        for(Course course : grades.keySet()){
            Double gradeObj = grades.get(course);
            if(gradeObj == null){
                System.out.printf("%s: No grades available for this course.\n", course.getTitle());
            }
            else{
                double grade = gradeObj;
                System.out.printf("%s: %.1f\n", course.getTitle(), grade);
            }
        }
        System.out.println("Your current GPA: " + calculateGPA());
    }

    //Prints all enrolled courses if found then asks the user to choose a course to drop
    private void dropCourse() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses to drop.");
            return;
        }

        SystemHelper.Choice choice = new SystemHelper.Choice("Choose course to drop (Press 0 to go back): ");

        while (true) {
            for (int i = 0; i < enrolledCourses.size(); i++) {
                System.out.println((i + 1) + ". " + enrolledCourses.get(i));
            }

            int option = choice.ChoiceByInt(enrolledCourses.size());
            if (option == 0) {
                break;
            }

            Course selected = enrolledCourses.get(option - 1);

            try {
                boolean dropped = selected.drop(this);
                if (dropped) {
                    removeCourseEnrollment(selected);
                    System.out.println("Successfully dropped " + selected.getTitle());
                }
                break;
            } catch (UserNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    //Prints all available courses from platform if found then asks the user to choose a course to enroll in
    private void enrollInNewCourse(Platform platform){
        List<Course> availableCourses = platform.getCourses();

        if(availableCourses.isEmpty()){
            System.out.println("No Courses available for enrollment at the moment.");
            return;
        }

        SystemHelper.Choice choice = new SystemHelper.Choice("Choose a course to enroll in (Press 0 to go back): ");

        while (true){
            for(int i = 0; i < availableCourses.size(); i++){
                System.out.println((i+1) + ". " + availableCourses.get(i).courseInfo());
                System.out.println("------------------------------");
            }
            int option = choice.ChoiceByInt(availableCourses.size());

            if(option == 0){
                break;
            }

            Course selectedCourse = availableCourses.get(option-1);

            try {
                boolean enrolled = selectedCourse.enroll(this);
                if(enrolled){
                    addCourseEnrollment(selectedCourse);
                    System.out.println("Successfully enrolled in: " + selectedCourse);
                }
                break;
            } catch (AlreadyEnrolledException | CourseFullException e){
                System.out.println(e.getMessage());
            }
        }
    }

    //prints all enrolled courses if found then asks the user to choose an operation on the course
    private void viewEnrolledCourses(Scanner sc){
        while (true){
            SystemHelper.Choice choice = new SystemHelper.Choice("Choose a course (Press 0 to go back): ",
                    "Error: You must choose a course.",
                    "Error: You must enter a positive number.",
                    "Error: You must enter a valid course choice.");

            System.out.println();

            if(enrolledCourses.isEmpty()){
                System.out.println("You are not enrolled in any course.");
                return;
            }

            System.out.println("Enrolled courses:");
            for (int i = 0; i < enrolledCourses.size(); i++) {
                System.out.println((i+1) + ". " + enrolledCourses.get(i));
            }

            int option = choice.ChoiceByInt(enrolledCourses.size());
            if(option == 0) break;

            Course course = enrolledCourses.get(option - 1);

            while (true){
                System.out.println();
                System.out.println("Viewing: " + course);
                System.out.println("1. View Grades \n2. Rate the course");
                choice = new SystemHelper.Choice("Choose an option (Enter 0 to go back): ");
                option = choice.ChoiceByInt(2);
                if(option == 0){
                    break;
                }
                else if(option == 1){
                    Double gradeObj = grades.get(course);
                    if(gradeObj == null){
                        System.out.println("No grades available for this course.");
                    }
                    else{
                        double grade = gradeObj;
                        System.out.println("Grade for " + course.getTitle() + ": " + grade);
                    }
                    System.out.printf("Your current GPA: %.2f\n", calculateGPA());
                }
                else if(option == 2){
                    if(ratedCourses.get(course) != null){
                        double rating = ratedCourses.get(course);
                        System.out.println("You already gave this course a rating(" + rating + ").");
                        continue;
                    }
                    System.out.print("Enter rating (1-5): ");
                    double ratingInput = sc.nextDouble();

                    if(ratingInput < 1 || ratingInput > 5){
                        System.out.println("Error: Rating must be 1-5.");
                        continue;
                    }
                    course.addRating(ratingInput);
                    ratedCourses.put(course, ratingInput);
                    System.out.printf("Rated %.1f | New course average rating: %.1f \n", ratingInput, course.getAverageRating());
                }
            }
        }
    }
}