package com.maz.academy.course.module;

import com.maz.academy.course.Course;
import com.maz.academy.course.lesson.Lesson;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * This class represents a structural unit (chapter/section) of a Course.
 * A Module acts as a container for multiple Lesson objects.
 * It allows for organizing course content into logical sections.
 */

@Data
@Table(name = "modules")
@Entity
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int moduleId;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
    private String title;

    @OneToMany(
            mappedBy = "module",
            cascade = CascadeType.ALL
    )
    private List<Lesson> lessons;

//    public void addLesson(Lesson lesson){
//        this.lessons.add(lesson);
//    }
//    public void removeLesson(Lesson lesson){
//        for(Lesson obj : lessons){
//            if(obj.getTitle().equalsIgnoreCase(lesson.getTitle())){
//                lessons.remove(obj);
//            }
//        }
//    }
//    public List<Lesson> getLessonsList(){ //new
//       return Collections.unmodifiableList(lessons);
//    }
//    public void setLessonsList(ArrayList<Lesson> lessons){ //new
//        this.lessons = lessons;
//    }
}