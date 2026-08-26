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

    @Mapping(source = "id" ,target = "assignmentId")


    // Student
    @Mapping(source = "student.id" ,target = "studentId")
    @Mapping(source = "student.studentCode" ,target = "studentCode")
    @Mapping(source = "student.user.fullName" , target = "studentName")

    // Mentor
    @Mapping(source = "mentor.id" ,target = "mentorId")
    @Mapping(source = "mentor.user.fullName" , target = "mentorName")

    // Phase
    @Mapping(source = "phase.id" , target = "phaseId")
    @Mapping(source = "phase.phaseName" , target = "phaseName")
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