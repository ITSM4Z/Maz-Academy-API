package com.maz.academy.user.student;

import com.maz.academy.core.models.Enrollment;
import com.maz.academy.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private List<Enrollment> enrollments;

    private String major;
    private double gpa;
}