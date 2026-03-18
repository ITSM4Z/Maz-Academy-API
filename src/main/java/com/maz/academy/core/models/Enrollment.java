package com.maz.academy.core.models;

import com.maz.academy.course.Course;
import com.maz.academy.user.student.Student;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "enrollments")
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollmentId;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    private Double rating;
    private Double gradePoint;
    private String letterGrade;
}
