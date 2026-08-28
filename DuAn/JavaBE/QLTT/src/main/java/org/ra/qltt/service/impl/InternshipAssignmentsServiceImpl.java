package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceAlreadyExistsException;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.enums.AssignmentStatus;
import org.ra.qltt.model.dto.enums.UserRole;
import org.ra.qltt.model.dto.request.InternshipAssignmentRequestDTO;
import org.ra.qltt.model.dto.response.InternshipAssignmentResponseDTO;
import org.ra.qltt.model.entity.*;
import org.ra.qltt.model.mapper.InternshipAssignmentMapper;
import org.ra.qltt.repository.InternshipAssignmentsRepository;
import org.ra.qltt.repository.InternshipPhasesRepository;
import org.ra.qltt.repository.MentorRepository;
import org.ra.qltt.repository.StudentRepository;
import org.ra.qltt.security.UserPrinciple;
import org.ra.qltt.service.InternshipAssignmentsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipAssignmentsServiceImpl implements InternshipAssignmentsService {
    private final InternshipAssignmentsRepository internshipAssignmentsRepository;
    private final InternshipAssignmentMapper internshipAssignmentMapper;
    private final StudentRepository  studentRepository;
    private final MentorRepository   mentorRepository;
    private final InternshipPhasesRepository  internshipPhasesRepository;

    @Override
    public List<InternshipAssignmentResponseDTO> getIA() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        Users user = principal.getUser();

        List<InternshipAssignments> assignments;

        if (user.getRole().equals(UserRole.ADMIN.name())) {

            assignments = internshipAssignmentsRepository.findAll();

        } else if (user.getRole().equals(UserRole.MENTOR.name())) {

            assignments = internshipAssignmentsRepository
                    .findByMentor_User_Id((user.getId()));

        } else{

            assignments = internshipAssignmentsRepository
                    .findByStudentUser_Id((user.getId()));
        }

        return internshipAssignmentMapper
                .assignmentsToResponseDTOs(assignments);
    }

    @Override
    public InternshipAssignmentResponseDTO getIAById(Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        Users user = principal.getUser();

        InternshipAssignments assignments;

        if ("ADMIN".equals(user.getRole())) {
            assignments = internshipAssignmentsRepository.findById(id).orElseThrow(() ->
                 new ResourceNotFoundException(ResponseWrapper.getMessage("error.internship_assignment.not_found"))
            );

        } else if ("MENTOR".equals(user.getRole())) {

            assignments = internshipAssignmentsRepository.findByIdAndMentor_Id(id ,user.getId());

        } else{

            assignments = internshipAssignmentsRepository.findByIdAndStudent_Id(id ,user.getId());

        }

        return internshipAssignmentMapper
                .assignmentToResponseDTO(assignments);
    }

    @Override
    public InternshipAssignmentResponseDTO createIA(
            InternshipAssignmentRequestDTO request
    ) {

        InternshipAssignments assignment =
                internshipAssignmentMapper.requestToAssignment(request);


        Students student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy sinh viên với ID: "
                                        + request.getStudentId()
                        )
                );


        Mentors mentor = mentorRepository.findById(request.getMentorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy mentor với ID: "
                                        + request.getMentorId()
                        )
                );

        InternshipPhases phase =
                internshipPhasesRepository.findById(request.getPhaseId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy giai đoạn thực tập với ID: "
                                                + request.getPhaseId()
                                )
                        );
        if (internshipAssignmentsRepository
                .existsByStudentIdAndPhaseId(
                        request.getStudentId(),
                        request.getPhaseId()
                )) {

            throw new ResourceAlreadyExistsException(
                    "Tiêu chí này đã được đánh giá cho sinh viên trong đợt này"
            );
        }
        assignment.setStudent(student);
        assignment.setMentor(mentor);
        assignment.setPhase(phase);


        InternshipAssignments saved =
                internshipAssignmentsRepository.save(assignment);


        return internshipAssignmentMapper.assignmentToResponseDTO(saved);
    }

    @Override
    public InternshipAssignmentResponseDTO updateStatusIA(Long id, AssignmentStatus assignmentStatus) {
        InternshipAssignments internshipAssignments = internshipAssignmentsRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.internship_assignment.not_found"))
        );
        internshipAssignments.setStatus(assignmentStatus.name());
        internshipAssignmentsRepository.save(internshipAssignments);
        return internshipAssignmentMapper.assignmentToResponseDTO(internshipAssignments);
    }
}
