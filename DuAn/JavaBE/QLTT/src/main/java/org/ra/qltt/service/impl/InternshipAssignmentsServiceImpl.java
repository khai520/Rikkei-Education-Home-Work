package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.response.InternshipAssignmentResponseDTO;
import org.ra.qltt.model.entity.InternshipAssignments;
import org.ra.qltt.model.mapper.InternshipAssignmentMapper;
import org.ra.qltt.repository.InternshipAssignmentsRepository;
import org.ra.qltt.service.InternshipAssignmentsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipAssignmentsServiceImpl implements InternshipAssignmentsService {
    private final InternshipAssignmentsRepository internshipAssignmentsRepository;
    private final InternshipAssignmentMapper internshipAssignmentMapper;

    @Override
    public List<InternshipAssignmentResponseDTO> getIA() {
        List<InternshipAssignments> internshipAssignments = internshipAssignmentsRepository.findAll();
        return internshipAssignmentMapper.assignmentsToResponseDTOs(internshipAssignments);
    }
}
