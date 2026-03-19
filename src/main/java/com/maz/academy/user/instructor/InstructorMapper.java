package com.maz.academy.user.instructor;

import org.springframework.stereotype.Service;

@Service
public class InstructorMapper {
    public Instructor toInstructor(InstructorDTO dto){
        Instructor instructor = new Instructor();
        instructor.setName(dto.name());
        instructor.setEmail(dto.email());
        return instructor;
    }

    public InstructorResponseDTO toInstructorResponseDto(Instructor instructor){
        return new InstructorResponseDTO(
                instructor.getName(),
                instructor.getEmail()
        );
    }
}
