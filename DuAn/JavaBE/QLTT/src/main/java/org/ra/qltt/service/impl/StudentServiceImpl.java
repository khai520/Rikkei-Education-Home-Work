package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.enums.UserRole;
import org.ra.qltt.model.dto.request.StudentRequestDTO;
import org.ra.qltt.model.dto.response.StudentResponseDTO;
import org.ra.qltt.model.entity.Students;
import org.ra.qltt.model.entity.Users;
import org.ra.qltt.model.mapper.StudentMapper;
import org.ra.qltt.repository.StudentRepository;
import org.ra.qltt.repository.UserRepository;
import org.ra.qltt.security.UserPrinciple;
import org.ra.qltt.service.StudentService;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserRepository userRepository;

    @Override
    public List<StudentResponseDTO> getStudents() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        Users user = principal.getUser();

        List<Students> students;

        assert user != null;
        if ("ADMIN".equals(user.getRole())) {

            students = studentRepository.findAll();

        } else {

            students = studentRepository
                    .findStudentsByMentorId(user.getId());
        }

        return studentMapper.studentsToStudentResponseDTOs(students);
    }

    @Override
    public StudentResponseDTO getStudentByID(Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        Users user = principal.getUser();
        Students students;

        assert user != null;
        students = studentRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.student.not_found"))
        );
        if ("STUDENT".equals(user.getRole()) && user.getId() != id ) {
            throw new AccessDeniedException(
                    "Chỉ có thể xem thông tin bản thân"
            );
        }
        return studentMapper.studentToStudentResponseDTO(students);
    }

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        Users user = userRepository.findById(studentRequestDTO.getUserId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(ResponseWrapper.getMessage("error.student.not_found"))
                );

        if (!user.getRole().equals(UserRole.STUDENT.name())) {
            throw new IllegalArgumentException(
                    "User phải có role STUDENT"
            );
        }

        if (studentRepository.existsById(user.getId())) {
            throw new IllegalArgumentException(
                    "User này đã có thông tin sinh viên"
            );
        }

        Students student =
                studentMapper.studentRequestToStudents(studentRequestDTO);

        student.setUser(user);

        Students savedStudent =
                studentRepository.save(student);

        return studentMapper.studentToStudentResponseDTO(savedStudent);
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(
            Long studentId,
            StudentRequestDTO request
    ) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        String username = principal.getUser().getUsername();

        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy sinh viên với ID: " + studentId
                ));

        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng: " + username
                ));


        if (currentUser.getRole().equals(UserRole.STUDENT.name())
                && !student.getId().equals(currentUser.getId())) {

            throw new AccessDeniedException(
                    "Sinh viên chỉ được cập nhật thông tin của chính mình"
            );
        }

        if (request.getUserId() != null
                && !request.getUserId().equals(student.getId())) {

            throw new IllegalArgumentException(
                    "Không được thay đổi người dùng liên kết với sinh viên"
            );
        }

        studentMapper.updateStudentFromDTO(request, student);

        Students updatedStudent = studentRepository.save(student);

        return studentMapper.studentToStudentResponseDTO(updatedStudent);
    }

}
