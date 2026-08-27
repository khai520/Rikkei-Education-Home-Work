package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.AssessmentResultRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentResultResponseDTO;
import org.ra.qltt.model.entity.AssessmentResults;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssessmentResultMapper {
    @Mapping(source = "id", target = "resultId")
    @Mapping(source = "assignment.id", target = "assignmentId")

    @Mapping(source = "assignment.student.id", target = "studentId")
    @Mapping(source = "assignment.student.studentCode", target = "studentCode")
    @Mapping(source = "assignment.student.user.fullName", target = "studentName")

    @Mapping(source = "assignment.mentor.id", target = "mentorId")
    @Mapping(source = "assignment.mentor.user.fullName", target = "mentorName")

    @Mapping(source = "round.id", target = "roundId")
    @Mapping(source = "round.roundName", target = "roundName")

    @Mapping(source = "criterion.id", target = "criterionId")
    @Mapping(source = "criterion.criteriaName", target = "criterionName")

    @Mapping(source = "criterion.maxScore", target = "maxScore")
    @Mapping(source = "comment", target = "comments")

    @Mapping(source = "evaluatedBy.fullName", target = "evaluatedByName")
    AssessmentResultResponseDTO resultToResponseDTO(
            AssessmentResults result
    );

    List<AssessmentResultResponseDTO> resultsToResponseDTOs(
            List<AssessmentResults> results
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    @Mapping(target = "round", ignore = true)
    @Mapping(target = "criterion", ignore = true)
    @Mapping(target = "evaluatedBy", ignore = true)
    @Mapping(target = "evaluationDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AssessmentResults requestToResult(
            AssessmentResultRequestDTO request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    @Mapping(target = "round", ignore = true)
    @Mapping(target = "criterion", ignore = true)
    @Mapping(target = "evaluatedBy", ignore = true)
    @Mapping(target = "evaluationDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateResult(
            AssessmentResultRequestDTO request,
            @MappingTarget AssessmentResults result
    );
}