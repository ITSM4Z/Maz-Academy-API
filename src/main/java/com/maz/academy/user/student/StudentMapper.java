package com.maz.academy.user.student;

import org.springframework.stereotype.Service;

@Service
public class StudentMapper {
    public Student toStudent(StudentDTO dto){
        Student student = new Student();
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setMajor(dto.major());
        return student;
    }

    public StudentResponseDTO toStudentResponseDto(Student student){
        return new StudentResponseDTO(
                student.getName(),
                student.getEmail(),
                student.getMajor()
        );
    }
}
