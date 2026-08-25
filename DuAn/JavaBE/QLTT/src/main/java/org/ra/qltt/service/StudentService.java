package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.StudentRequestDTO;
import org.ra.qltt.model.dto.response.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    List<StudentResponseDTO> getStudents();
    StudentResponseDTO getStudentByID(Long id);
    StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO);
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO);
}
