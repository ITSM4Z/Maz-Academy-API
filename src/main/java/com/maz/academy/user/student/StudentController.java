package com.maz.academy.user.student;

import com.maz.academy.core.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) { this.studentRepository = studentRepository; }

    @PostMapping("/users/students")
    public StudentResponseDTO create(@RequestBody StudentDTO studentDTO){
        Student savedStudent = studentRepository.save(toStudent(studentDTO));
        return toStudentResponseDto(savedStudent);
    }

    @GetMapping("/users/students")
    public List<StudentResponseDTO> findAll(){
        return studentRepository.findAll()
                .stream()
                .map(this::toStudentResponseDto)
                .toList();
    }

    @GetMapping("/users/students/{student_id}")
    public StudentResponseDTO findById(@PathVariable int student_id){
        return toStudentResponseDto(
                studentRepository.findById(student_id)
                        .orElseThrow(() -> new UserNotFoundException("Student not found!"))
        );
    }

    private Student toStudent(StudentDTO dto){
        Student student = new Student();
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setMajor(dto.major());
        return student;
    }

    private StudentResponseDTO toStudentResponseDto(Student student){
        return new StudentResponseDTO(
                student.getName(),
                student.getEmail(),
                student.getMajor()
        );
    }
}