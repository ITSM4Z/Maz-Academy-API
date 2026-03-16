package com.maz.academy.model;

import java.util.*;

import com.maz.academy.service.Platform;
import com.maz.academy.util.SystemHelper;

/**
 * This class represents a structural unit (chapter/section) of a Course.
 * A Module acts as a container for multiple Lesson objects.
 * It allows for organizing course content into logical sections.
 */

public class Module {
    private int moduleID;
    private int courseID;
    private String title;
    private ArrayList<Lesson> lessons;

    public Module(){}
    public Module(String title, int courseID, Platform platform) {
        SystemHelper.Generate generator = new SystemHelper.Generate();

        this.title = title;
        this.lessons = new ArrayList<>();
        this.moduleID = generator.generateID(7, false, platform);
        this.courseID = courseID;
    }

    public void setTitle(String title){ //new
        this.title = title;
    }
    public String getTitle(){ //new
        return title;
    }

    public void addLesson(Lesson lesson){
        this.lessons.add(lesson);
    }
    public void removeLesson(Lesson lesson){
        for(Lesson obj : lessons){
            if(obj.getTitle().equalsIgnoreCase(lesson.getTitle())){
                lessons.remove(obj);
            }
        }
    }
    public List<Lesson> getLessonsList(){ //new
       return Collections.unmodifiableList(lessons);
    }
    public void setLessonsList(ArrayList<Lesson> lessons){ //new
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "module title: " + title + ". " + lessons.size() + " lessons.";
    }
}