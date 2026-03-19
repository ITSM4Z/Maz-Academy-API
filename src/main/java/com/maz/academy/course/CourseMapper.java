package com.maz.academy.course;

import org.springframework.stereotype.Service;

@Service
public class CourseMapper {
    public Course toCourse(CourseDTO dto){
        Course course = new Course();
        course.setTitle(dto.title());
        course.setCapacity(dto.capacity());
        course.setPrice(dto.price());
        course.setCourseLevel(dto.level());
        return course;
    }

    public CourseResponseDTO toCourseResponseDTO(Course course){
        return CourseResponseDTO.fromEntity(course);
    }
}
