package com.maz.academy.user.instructor;

import com.maz.academy.core.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping("/users/instructors")
    public InstructorResponseDTO saveInstructor(@RequestBody InstructorDTO user){
        return instructorService.saveInstructor(user);
    }

    @GetMapping("/users/instructors")
    public List<InstructorResponseDTO> findAllInstructor(){
        return instructorService.findAllInstructor();
    }

    @GetMapping("/users/instructors/{instructor_id}")
    public InstructorResponseDTO findInstructorById(@PathVariable int instructor_id){
        return instructorService.findInstructorById(instructor_id);
    }
}