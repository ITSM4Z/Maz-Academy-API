package com.educore.service;

import com.educore.enums.CourseLevel;
import com.educore.model.*;
import com.educore.enums.UserRole;
import java.io.*;
import java.util.*;

public class DatabaseService {
    private static final String DATABASE_DIR = "./database/";
    private static final String USERS_FILE = DATABASE_DIR + "users.csv";
    private static final String COURSES_FILE = DATABASE_DIR + "courses.csv";
    private static final String ENROLLMENT_FILE = DATABASE_DIR + "enrollment.csv";

    private static final String USERS_HEADER = "ID,Name,Email,Role";
    private static final String COURSES_HEADER = "ID,Title,Capacity,Price,Level";
    private static final String ENROLLMENTS_HEADER = "StudentID,CourseID,Grade";

    private static final int USERS_HEADER_LENGTH = 4;
    private static final int COURSES_HEADER_LENGTH = 5;
    private static final int ENROLLMENTS_HEADER_LENGTH = 3;

    public static void initializeDatabase(){
        File dir = new File(DATABASE_DIR);
        if(!dir.exists()){
            boolean created = dir.mkdirs();
            if(created)
                System.out.println("Database folder was created successfully.");
        }

        checkFile(USERS_FILE, USERS_HEADER);
        checkFile(COURSES_FILE, COURSES_HEADER);
        checkFile(ENROLLMENT_FILE, ENROLLMENTS_HEADER);
    }

    public static void savePlatform(Platform platform){
        saveUsers(platform.getUsers());
        saveCourses(platform.getCourses());
        saveEnrollments(platform.getUsers());
        System.out.println("Platform data saved to CSV successfully.");
    }

    public static Platform loadPlatform(){
        Platform platform = new Platform();
        loadUsers(platform);
        loadCourses(platform);
        loadEnrollments(platform);

        return platform;
    }

    private static void checkFile(String fileName, String header){
        File file = new File(fileName);
        try{
            if(!file.exists()){
                if(file.createNewFile()){
                    try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
                        writer.println(header);
                    } catch (IOException e){
                        System.out.println("Error: there was a problem creating file: " + e.getMessage());
                    }
                }
            }
            else if(file.length() == 0){
                try(PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))){
                    writer.println(header);
                } catch (IOException e){
                    System.out.println("Error: there was a problem writing file header: " + e.getMessage());
                }
            }
        } catch (IOException e){
            System.out.println("Error: There was a problem with initializing file " + fileName + ": " + e.getMessage());
        }
    }

    private static void saveUsers(List<User> users){
        try(PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))){
            writer.println(USERS_HEADER);
            for(User u : users){
                writer.println(u.getUserId() + "," + u.getName() + "," + u.getEmail() + "," + u.getUserRole());
            }
        } catch (IOException e){
            System.out.println("Error: there was a problem saving users: " + e.getMessage());
        }
    }

    private static void saveCourses(List<Course> courses){
        try(PrintWriter writer = new PrintWriter(new FileWriter(COURSES_FILE))){
            writer.println(COURSES_HEADER);
            for (Course c : courses) {
                writer.println(c.getCourseID() + "," + c.getTitle() + "," + c.getCapacity() + "," + c.getPrice() + "," + c.getCourseLevel());
            }
        } catch (IOException e){
            System.out.println("Error: there was a problem saving courses: " + e.getMessage());
        }
    }

    private static void saveEnrollments(List<User> users){
        try(PrintWriter writer = new PrintWriter(new FileWriter(ENROLLMENT_FILE))){
            writer.println(ENROLLMENTS_HEADER);
            for(User user : users){
                if(user instanceof Student s){
                    for(Course c : s.getEnrolledCourses()){
                        double grade = s.getGrade(c);
                        writer.println(s.getUserId() + "," + c.getCourseID() + "," + grade);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Error: there was a problem saving enrollments: " + e.getMessage());
        }
    }

    private static void loadUsers(Platform platform){
        try(BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))){
            String line = br.readLine();

            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length < USERS_HEADER_LENGTH) continue;

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];
                UserRole role = UserRole.valueOf(parts[3]);
                User user = switch (role) {
                    case STUDENT -> new Student(id, name, email, role);
                    case INSTRUCTOR -> new Instructor(id, name, email, role);
                    case ADMIN -> new Admin(id, name, email, role, platform);
                };
                platform.addUser(user);
            }
        } catch (Exception e){
            System.out.println("Error: There was a problem loading users: " + e.getMessage());
        }
    }

    private static void loadCourses(Platform platform){
        try (BufferedReader br = new BufferedReader(new FileReader(COURSES_FILE))){
            String line = br.readLine();

            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length < COURSES_HEADER_LENGTH) continue;

                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                int capacity = Integer.parseInt(parts[2]);
                double price = Double.parseDouble(parts[3]);
                CourseLevel level = CourseLevel.valueOf(parts[4]);

                Course course = new Course(id, capacity, title, price, level);
                platform.addCourse(course);
            }
        } catch (Exception e){
            System.out.println("Error: There was a problem loading courses: " + e.getMessage());
        }
    }

    private  static void loadEnrollments(Platform platform){
        try (BufferedReader br = new BufferedReader(new FileReader(COURSES_FILE))){
            String line = br.readLine();

            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length < ENROLLMENTS_HEADER_LENGTH) continue;

                int studentId = Integer.parseInt(parts[0]);
                int courseId = Integer.parseInt(parts[1]);
                double grade = Double.parseDouble(parts[2]);

                User user = platform.findUserById(studentId);
                Course course = platform.findCourseById(courseId);

                if(user instanceof Student s){
                    s.addCourseEnrollment(course);
                    s.modifyGrade(course, grade);
                }
            }
        } catch (Exception e){
            System.out.println("Error: There was a problem loading enrollments: " + e.getMessage());
        }
    }
}