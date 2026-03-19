package com.maz.academy.course;

import com.maz.academy.core.enums.CourseLevel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record CourseDTO(
        @NotEmpty
        String title,
        @Min(value = 0, message = "Capacity cannot be negative!")
        @Max(value = 100, message = "Maximum capacity is 100 students!")
        @PositiveOrZero
        @NotEmpty
        int capacity,
        @DecimalMin(value = "0.0", message = "Price cannot be negative!")
        @DecimalMax(value = "100.0", message = "Price cannot exceed 100.0!")
        @PositiveOrZero
        @NotEmpty
        BigDecimal price,
        @NotEmpty
        CourseLevel level,
        @NotEmpty
        List<Integer> instructorsId
) {
}