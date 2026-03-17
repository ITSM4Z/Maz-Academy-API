package com.maz.academy.course;

import jakarta.persistence.*;
import lombok.Data;

/**
 * This class serves as a data model for course content.
 * Represents a single lesson entity containing a title and duration.
 */

@Data
@Table(name = "lessons")
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lessonId;

    @ManyToOne
    @JoinColumn(name = "moduleId")
    private Module module;
    private String title;
    private int durationMinutes;
}