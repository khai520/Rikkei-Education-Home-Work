package org.ra.qltt.service;

import org.ra.qltt.model.dto.response.InternshipAssignmentResponseDTO;

import java.util.List;

public interface InternshipAssignmentsService {
    List<InternshipAssignmentResponseDTO> getIA();
}
