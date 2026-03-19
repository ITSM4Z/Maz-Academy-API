package com.maz.academy.course;

import com.maz.academy.core.enums.CourseLevel;
import com.maz.academy.course.module.Module;

import java.math.BigDecimal;
import java.util.List;

public record CourseResponseDTO(
        String title,
        int capacity,
        BigDecimal price,
        double rating,
        CourseLevel level,
        List<String> instructors,
        List<String> modules
) {
    public static CourseResponseDTO fromEntity(Course course){
        List<String> instructors = course.getTeachings()
                .stream()
                .map(teaching -> teaching.getInstructor().getName())
                .toList();

        List<String> modules = course.getModules()
                .stream()
                .map(Module::getTitle)
                .toList();
        return new CourseResponseDTO(
                course.getTitle(),
                course.getCapacity(),
                course.getPrice(),
                course.getAverageRating(),
                course.getCourseLevel(),
                instructors,
                modules
        );
    }
}
