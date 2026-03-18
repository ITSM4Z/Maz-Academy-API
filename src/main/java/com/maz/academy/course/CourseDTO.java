package com.maz.academy.course;

import com.maz.academy.core.enums.CourseLevel;

public record CourseDTO(
        String title,
        int capacity,
        double price,
        CourseLevel level
) {
}