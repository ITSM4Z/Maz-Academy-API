package com.maz.academy.course;

import com.maz.academy.core.models.Teaching;
import com.maz.academy.user.instructor.InstructorController;
import com.maz.academy.user.instructor.InstructorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final InstructorService instructorService;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, InstructorService instructorService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.instructorService = instructorService;
    }

    public CourseResponseDTO saveCourse(CourseDTO dto){
        return courseMapper.toCourseResponseDTO(courseRepository.save(courseMapper.toCourse(dto)));
    }

    public List<CourseResponseDTO> findAllCourse(){
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toCourseResponseDTO)
                .toList();
    }

    public CourseResponseDTO findCourseById(int id){
        return courseRepository.findById(id)
                .map(courseMapper::toCourseResponseDTO)
                .orElseThrow(() -> new CourseNotFoundException("Course not found!"));
    }

    private boolean checkForInstructors(List<Integer> ids){
        return ids.stream().allMatch(id -> instructorService.findInstructorById(id) != null);
    }
}
