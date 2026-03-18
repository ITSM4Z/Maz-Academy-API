package com.maz.academy.user.instructor;

import com.maz.academy.core.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstructorController {
    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorRepository instructorRepository) { this.instructorRepository = instructorRepository; }

    @PostMapping("/users/instructors")
    public InstructorResponseDTO create(@RequestBody InstructorDTO user){
        Instructor instructor = instructorRepository.save(toInstructor(user));
        return toInstructorResponseDto(instructor);
    }

    @GetMapping("/users/instructors")
    public List<InstructorResponseDTO> findAll(){
        return instructorRepository.findAll()
                .stream()
                .map(this::toInstructorResponseDto)
                .toList();
    }

    @GetMapping("/users/instructors/{instructor_id}")
    public InstructorResponseDTO findById(@PathVariable int instructor_id){
        return toInstructorResponseDto(
                instructorRepository.findById(instructor_id)
                        .orElseThrow(() -> new UserNotFoundException("Instructor not found!"))
        );
    }

    private Instructor toInstructor(InstructorDTO dto){
        Instructor instructor = new Instructor();
        instructor.setName(dto.name());
        instructor.setEmail(dto.email());
        return instructor;
    }

    private InstructorResponseDTO toInstructorResponseDto(Instructor instructor){
        return new InstructorResponseDTO(
                instructor.getName(),
                instructor.getEmail()
        );
    }
}