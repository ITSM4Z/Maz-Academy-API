package com.maz.academy.user.student;

import com.maz.academy.core.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper){
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDTO saveStudent(StudentDTO dto){
        Student savedStudent = studentRepository.save(studentMapper.toStudent(dto));
        return studentMapper.toStudentResponseDto(savedStudent);
    }

    public List<StudentResponseDTO> findAllStudent(){
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .toList();
    }

    public StudentResponseDTO findStudentById(int id){
        return studentRepository.findById(id)
                .map(studentMapper::toStudentResponseDto)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }
}