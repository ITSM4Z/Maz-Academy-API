package com.maz.academy.course;

import com.maz.academy.core.enums.CourseLevel;
import com.maz.academy.core.models.Enrollment;
import com.maz.academy.core.models.Teaching;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private int capacity;
    private String title;
    private double price;
    private double averageRating;

    private List<Double> ratings;

    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<Module> modules;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<Enrollment> enrollments;

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<Teaching> teachings;
}