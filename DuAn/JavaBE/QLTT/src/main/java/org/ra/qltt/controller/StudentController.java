package org.ra.qltt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.StudentRequestDTO;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.response.StudentResponseDTO;
import org.ra.qltt.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getStudents() {
        List<StudentResponseDTO> students = studentService.getStudents();
        return ResponseEntity.ok(ResponseWrapper.success(students,"success.resource.all" , HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id){
        StudentResponseDTO student = studentService.getStudentByID(id);
        return ResponseEntity.ok(ResponseWrapper.success(student,"success.resource.find" , HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<?> saveStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO){
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(studentResponseDTO,"success.resource.create",HttpStatus.CREATED.value()));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentRequestDTO request
    ) {

        StudentResponseDTO student =
                studentService.updateStudent(studentId, request);

        return ResponseEntity.ok(
                ResponseWrapper.success(
                        student,
                        "success.resource.update",
                        HttpStatus.OK.value()
                )
        );
    }
}
