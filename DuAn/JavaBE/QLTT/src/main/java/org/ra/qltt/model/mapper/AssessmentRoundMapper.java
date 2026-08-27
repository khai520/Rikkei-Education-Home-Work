package org.ra.qltt.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.ra.qltt.model.dto.request.AssessmentRoundRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentRoundResponseDTO;
import org.ra.qltt.model.entity.AssessmentRounds;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = AssessmentRoundCriterionMapper.class
)
public interface AssessmentRoundMapper {

    // Phases
    @Mapping(source = "phases.id" , target = "phaseId")
    @Mapping(source = "phases.phaseName" , target = "phaseName")

    @Mapping(source = "roundCriteria" , target = "criteria")
    @Mapping(source = "id" , target = "roundId")
    AssessmentRoundResponseDTO roundToResponseDTO(
            AssessmentRounds round
    );

    List<AssessmentRoundResponseDTO> roundsToResponseDTOs(
            List<AssessmentRounds> rounds
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phases" , ignore = true)
    @Mapping(target = "assessmentResults" , ignore = true)
    @Mapping(target = "roundCriteria", ignore = true)
    @Mapping(source = "active" , target = "isActive")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AssessmentRounds requestToRound(
            AssessmentRoundRequestDTO request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phases" , ignore = true)
    @Mapping(target = "assessmentResults" , ignore = true)
    @Mapping(target = "roundCriteria", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateRound(
            AssessmentRoundRequestDTO request,
            @MappingTarget AssessmentRounds round
    );


}
