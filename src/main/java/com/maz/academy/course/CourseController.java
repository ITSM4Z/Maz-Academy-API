package com.maz.academy.course;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @PostMapping("/courses")
    public CourseResponseDTO create(@RequestBody CourseDTO courseDTO){
        return toCourseResponseDTO(courseRepository.save(toCourse(courseDTO)));
    }

    @GetMapping("/courses")
    public List<CourseResponseDTO> findAll(){
        return courseRepository.findAll()
                .stream()
                .map(this::toCourseResponseDTO)
                .toList();
    }

    @GetMapping("/courses/{course_id}")
    public CourseResponseDTO findById(@PathVariable int course_id){
        return toCourseResponseDTO(
                courseRepository.findById(course_id)
                        .orElseThrow(() -> new CourseNotFoundException("Course not found!"))
        );
    }

    private Course toCourse(CourseDTO dto){
        Course course = new Course();
        course.setTitle(dto.title());
        course.setCapacity(dto.capacity());
        course.setPrice(dto.price());
        course.setCourseLevel(dto.level());
        return course;
    }

    private CourseResponseDTO toCourseResponseDTO(Course course){
        return CourseResponseDTO.fromEntity(course);
    }
}
