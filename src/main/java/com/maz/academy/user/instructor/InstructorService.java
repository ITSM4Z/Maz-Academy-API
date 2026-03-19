package com.maz.academy.user.instructor;

import com.maz.academy.core.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final  InstructorMapper instructorMapper;

    public InstructorService(InstructorRepository instructorRepository, InstructorMapper instructorMapper){
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
    }

    public InstructorResponseDTO saveInstructor(InstructorDTO dto){
        Instructor instructor = instructorRepository.save(instructorMapper.toInstructor(dto));
        return instructorMapper.toInstructorResponseDto(instructor);
    }

    public List<InstructorResponseDTO> findAllInstructor(){
        return instructorRepository.findAll()
                .stream()
                .map(instructorMapper::toInstructorResponseDto)
                .toList();
    }

    public InstructorResponseDTO findInstructorById(int id){
        return instructorRepository.findById(id)
                .map(instructorMapper::toInstructorResponseDto)
                .orElseThrow(() -> new UserNotFoundException("Instructor not found!"));
    }
}
