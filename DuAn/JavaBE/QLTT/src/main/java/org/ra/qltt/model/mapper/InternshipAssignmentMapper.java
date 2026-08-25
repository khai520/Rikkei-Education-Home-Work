package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.InternshipAssignmentRequestDTO;
import org.ra.qltt.model.dto.response.InternshipAssignmentResponseDTO;
import org.ra.qltt.model.entity.InternshipAssignments;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternshipAssignmentMapper {

    InternshipAssignmentResponseDTO assignmentToResponseDTO(
            InternshipAssignments assignment
    );

    List<InternshipAssignmentResponseDTO> assignmentsToResponseDTOs(
            List<InternshipAssignments> assignments
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    InternshipAssignments requestToAssignment(
            InternshipAssignmentRequestDTO request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateAssignment(
            InternshipAssignmentRequestDTO request,
            @MappingTarget InternshipAssignments assignment
    );
}