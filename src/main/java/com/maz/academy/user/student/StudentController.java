package com.maz.academy.user.student;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) { this.studentService = studentService; }

    @PostMapping("/users/students")
    public StudentResponseDTO saveStudent(@Valid @RequestBody StudentDTO studentDTO){
        return studentService.saveStudent(studentDTO);
    }

    @GetMapping("/users/students")
    public List<StudentResponseDTO> findAllStudent(){
        return studentService.findAllStudent();
    }

    @GetMapping("/users/students/{student_id}")
    public StudentResponseDTO findStudentById(@PathVariable int student_id){
        return studentService.findStudentById(student_id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
        HashMap<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}