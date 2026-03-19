package com.maz.academy.course;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public CourseResponseDTO saveCourse(@Valid @RequestBody CourseDTO courseDTO){
        return courseService.saveCourse(courseDTO);
    }

    @GetMapping("/courses")
    public List<CourseResponseDTO> findAllCourse(){
        return courseService.findAllCourse();
    }

    @GetMapping("/courses/{course_id}")
    public CourseResponseDTO findCourseById(@PathVariable int course_id){
        return courseService.findCourseById(course_id);
    }
}
