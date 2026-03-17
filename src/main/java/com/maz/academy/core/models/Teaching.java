package com.maz.academy.core.models;

import com.maz.academy.course.Course;
import com.maz.academy.user.Instructor;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "teachings")
@Entity
public class Teaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teachingId;

    @ManyToOne
    @JoinColumn(name = "instructorId")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    private Double rating;
}
