package com.maz.academy.user;

import com.maz.academy.core.models.Teaching;
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
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User {

    @OneToMany(
            mappedBy = "instructor",
            cascade = CascadeType.ALL
    )
    private List<Teaching> teachings;
}