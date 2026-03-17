package com.maz.academy.user;

import com.maz.academy.core.enums.UserRole;
import jakarta.persistence.*;

import lombok.Data;

/**
 * An abstract class representing a generic user of the system.
 * Different user types such as Student, Instructor, or Admin will extend this class.
 */

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int userId;

    protected String name;

    @Column(unique = true)
    protected String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", insertable = false, updatable = false)
    protected UserRole userRole;
}