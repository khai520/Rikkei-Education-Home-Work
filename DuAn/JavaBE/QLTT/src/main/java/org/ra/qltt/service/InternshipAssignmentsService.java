package org.ra.qltt.service;

import org.ra.qltt.model.dto.enums.AssignmentStatus;
import org.ra.qltt.model.dto.request.InternshipAssignmentRequestDTO;
import org.ra.qltt.model.dto.response.InternshipAssignmentResponseDTO;

import java.util.List;

public interface InternshipAssignmentsService {
    List<InternshipAssignmentResponseDTO> getIA();
    InternshipAssignmentResponseDTO getIAById(Long id);
    InternshipAssignmentResponseDTO createIA(InternshipAssignmentRequestDTO internshipAssignmentRequestDTO);
    InternshipAssignmentResponseDTO updateStatusIA(Long id , AssignmentStatus assignmentStatus);
}
